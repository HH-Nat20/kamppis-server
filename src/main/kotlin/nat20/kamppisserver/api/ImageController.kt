package nat20.kamppisserver.api
import nat20.kamppisserver.domain.UserPhoto
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.repository.UserPhotoRepository
import nat20.kamppisserver.repository.UserProfileRepository
import nat20.kamppisserver.storage.FileSystemStorageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ResponseBody;
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
    private val storageService: FileSystemStorageService
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

        val userProfile = userProfileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        // Store files and get public URLs
        val imageUrls = storageService.store(image, userId)

        // Save image metadata in database
        val userPhoto = UserPhoto(
            userProfile = userProfile,
            name = imageUrls["original"].orEmpty(), // TODO: Perhaps don't save original at all to save space
            isProfilePhoto = isProfilePhoto
        )
        userPhotoRepository.save(userPhoto)

        userProfile.userPhotos = (userProfile.userPhotos ?: mutableListOf()).apply {
            add(userPhoto)
        }
        userProfileRepository.save(userProfile)

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
        val userProfile = userProfileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        val userPhoto = userPhotoRepository.findById(photoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User photo not found"))

        if (userPhoto.userProfile.id != userId) {
            return ResponseEntity.badRequest().body(mapOf("message" to "User photo does not belong to user"))
        }

        try {
            // Delete all image files associated with this photo
            storageService.delete(userId, userPhoto.name)

            // Remove the photo from the user's list **without replacing the collection**
            userProfile.userPhotos?.remove(userPhoto)

            // Save the updated user profile
            userProfileRepository.save(userProfile)

            // Delete metadata from the database
            userPhotoRepository.delete(userPhoto)

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
        val userProfile = userProfileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        val userPhoto = userPhotoRepository.findById(photoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User photo not found"))

        if (userPhoto.userProfile.id != userId) {
            return ResponseEntity.badRequest().body(mapOf("message" to "User photo does not belong to user"))
        }

        val allUserPhotos = userPhotoRepository.findByUserProfileId(userId)

        if (!isProfilePhoto) {
            // Deny request if this is the only profile photo being turned off
            val currentProfilePhoto = allUserPhotos.find { it.isProfilePhoto }
            if (currentProfilePhoto?.id == userPhoto.id) {
                return ResponseEntity.badRequest().body(mapOf("message" to "There must always be one profile photo"))
            }
        } else {
            // If setting this as profile photo, remove profile status from all others
            allUserPhotos.forEach { photo ->
                if (photo.id != photoId && photo.isProfilePhoto) {
                    photo.isProfilePhoto = false
                    userPhotoRepository.save(photo)
                }
            }
        }

        // Set the selected photo as the profile photo
        userPhoto.isProfilePhoto = isProfilePhoto
        userPhotoRepository.save(userPhoto)

        return ResponseEntity.ok(mapOf("message" to "Image updated", "isProfilePhoto" to isProfilePhoto.toString()))
    }

}
