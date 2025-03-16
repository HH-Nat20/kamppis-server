package nat20.kamppisserver.api

import nat20.kamppisserver.domain.UserPhoto
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserPhotoRepository
import nat20.kamppisserver.repository.UserProfileRepository
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.io.InputStream
import java.nio.file.*
import java.util.*

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val userProfileRepository: UserProfileRepository,
    private val userPhotoRepository: UserPhotoRepository
) {

    private val uploadRootDir = Path.of("/var/www/uploads")

    init {
        Files.createDirectories(uploadRootDir)
    }

    @GetMapping("/get/{userId}/{filename}")
    fun getImage(@PathVariable userId: Long, @PathVariable filename: String): ResponseEntity<Resource> {
        val userDir = uploadRootDir.resolve(userId.toString())
        val filePath = userDir.resolve(filename).normalize()
        val resource = UrlResource(filePath.toUri())

        return if (resource.exists() && resource.isReadable) {
            val mimeType = Files.probeContentType(filePath) ?: "application/octet-stream"

            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"$filename\"")
                .body(resource)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @PostMapping("/{userId}")
    @Transactional
    fun uploadImage(
        @PathVariable userId: Long,
        @RequestParam("image") image: MultipartFile,
        @RequestParam("isProfilePhoto", defaultValue = "false") isProfilePhoto: Boolean
    ): ResponseEntity<Map<String, String>> {
        if (image.isEmpty) {
            return ResponseEntity.badRequest().body(mapOf("message" to "File is empty"))
        }

        val userProfile = userProfileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        // Create user-specific folder
        val userDir = uploadRootDir.resolve(userId.toString())
        Files.createDirectories(userDir)

        // Generate a unique, sanitized filename
        val originalExtension = image.originalFilename?.substringAfterLast(".", "jpg") ?: "jpg"
        val sanitizedFilename = "${UUID.randomUUID()}.$originalExtension"

        val filePath = userDir.resolve(sanitizedFilename)
        saveFile(filePath, image.inputStream)

        // Get base URL dynamically
        val baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        val imageUrl = "$baseUrl/api/images/get/$userId/$sanitizedFilename"

        // Save image metadata in database
        val userPhoto = UserPhoto(
            userProfile = userProfile,
            name = imageUrl,
            isProfilePhoto = isProfilePhoto
        )
        userPhotoRepository.save(userPhoto)

        // Ensure `userPhotos` list is initialized before adding
        userProfile.userPhotos = (userProfile.userPhotos ?: mutableListOf()).apply {
            add(userPhoto)
        }

        userProfileRepository.save(userProfile)

        return ResponseEntity.ok(
            mapOf(
                "message" to "Upload successful",
                "filePath" to filePath.toString(),
                "imageUrl" to imageUrl
            )
        )
    }

    @Throws(IOException::class)
    private fun saveFile(filePath: Path, inputStream: InputStream) {
        try {
            Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to save file: ${filePath.fileName}", e)
        }
    }
}
