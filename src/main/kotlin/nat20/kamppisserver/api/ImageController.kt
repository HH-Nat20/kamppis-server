package nat20.kamppisserver.api
import nat20.kamppisserver.domain.UserPhoto
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserPhotoRepository
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.storage.StorageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


import java.util.*

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val userProfileRepository: UserProfileRepository,
    private val userPhotoRepository: UserPhotoRepository,
    private val storageService: StorageService
) {

    private val uploadRootDir: Path = Paths.get("/var/www/uploads")

    @GetMapping("/get/{userId}/{filename}", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE])
    fun getImage(@PathVariable userId: Long, @PathVariable filename: String): ResponseEntity<ByteArray> {
        val userDir = uploadRootDir.resolve(userId.toString())
        val filePath = userDir.resolve(filename).normalize()

        return try {
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }

            val fileBytes = Files.readAllBytes(filePath)
            val mimeType = Files.probeContentType(filePath) ?: "application/octet-stream"

            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"$filename\"")
                .body(fileBytes)

        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
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

        // Store file and get public URL
        val imageUrl = storageService.store(image, userId)

        // Save image metadata in database
        val userPhoto = UserPhoto(
            userProfile = userProfile,
            name = imageUrl,
            isProfilePhoto = isProfilePhoto
        )
        userPhotoRepository.save(userPhoto)

        userProfile.userPhotos = (userProfile.userPhotos ?: mutableListOf()).apply {
            add(userPhoto)
        }
        userProfileRepository.save(userProfile)

        return ResponseEntity.ok(mapOf("message" to "Upload successful", "imageUrl" to imageUrl))
    }
}
