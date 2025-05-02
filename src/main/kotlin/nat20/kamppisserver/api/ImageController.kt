package nat20.kamppisserver.api

import nat20.kamppisserver.repository.ProfilePhotoRepository
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.service.ImageService
import nat20.kamppisserver.storage.FileSystemStorageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Files

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val profileRepository: ProfileRepository,
    private val profilePhotoRepository: ProfilePhotoRepository,
    private val storageService: FileSystemStorageService,
    private val imageService: ImageService
) {

    @GetMapping("/get/{userId}/{filename:.+}")
    fun getImage(@PathVariable userId: Long, @PathVariable filename: String): ResponseEntity<Resource> {
        return try {
            val resource = storageService.loadAsResource("$userId/$filename")

            if (resource.exists() && resource.isReadable) {
                val contentType = Files.probeContentType(resource.file.toPath()) ?: "application/octet-stream"
                val headers = HttpHeaders()
                headers.contentType = MediaType.parseMediaType(contentType)
                headers.contentLength = resource.contentLength()
                ResponseEntity.ok().headers(headers).body(resource)
            } else {
                ResponseEntity.notFound().build() // Return 404 if file does not exist
            }
        } catch (e: Exception) {
            ResponseEntity.notFound().build() // Return 404 on any file loading error
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

        val profile = profileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        // Store files and get public URLs
        val imageUrls = storageService.store(image, userId)

        // Save image metadata in database
        imageService.saveImageMetaData(profile, imageUrls, isProfilePhoto)

        return ResponseEntity.ok(mapOf(
            "message" to "Upload successful",
            "original" to imageUrls["original"].orEmpty(),
            "resized" to imageUrls["resized"].orEmpty(),
            "thumbnail" to imageUrls["thumbnail"].orEmpty()
        ))
    }

    @DeleteMapping("/{userId}/{photoId}")
    @Transactional
    fun deleteImage(@PathVariable userId: Long, @PathVariable photoId: Long): ResponseEntity<Map<String, String>> {
        val profile = profileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        val photo = profilePhotoRepository.findById(photoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User photo not found"))

        if (photo.profile.id != userId) {
            return ResponseEntity.badRequest().body(mapOf("message" to "User photo does not belong to user"))
        }

        try {
            imageService.deleteUserPhoto(profile, photo, userId)
            return ResponseEntity.ok(mapOf("message" to "Image deleted"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to "Failed to delete image", "error" to e.message.orEmpty()))
        }
    }

    @PutMapping("/{userId}/{photoId}")
    @Transactional
    fun updateImage(
        @PathVariable userId: Long,
        @PathVariable photoId: Long,
        @RequestParam("isProfilePhoto", defaultValue = "false") isProfilePhoto: Boolean
    ): ResponseEntity<Map<String, String>> {
        // TODO: Move this duplicate logic elsewhere
        val profile = profileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        val photo = profilePhotoRepository.findById(photoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User photo not found"))

        if (photo.profile.id != userId) {
            return ResponseEntity.badRequest().body(mapOf("message" to "User photo does not belong to user"))
        }

        return try {
            imageService.updateImage(photo, userId, isProfilePhoto)
            ResponseEntity.ok(mapOf("message" to "Image updated", "isProfilePhoto" to isProfilePhoto.toString()))
        } catch (e: Exception){
            ResponseEntity.badRequest().body(mapOf("message" to "There must always be one profile photo"))
        }
    }
}