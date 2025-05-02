package nat20.kamppisserver.service

import nat20.kamppisserver.domain.ProfilePhoto
import nat20.kamppisserver.repository.ProfilePhotoRepository
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.storage.FileSystemStorageService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files

@Service
class ImageService(
    private val profileRepository: ProfileRepository,
    private val profilePhotoRepository: ProfilePhotoRepository,
    private val storageService: FileSystemStorageService
) {

    fun getImage(userId: Long, fileName: String): ResponseEntity<Resource> {
        return try {
            val resource = storageService.loadAsResource("$userId/$fileName")

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

    @Transactional
    fun uploadImage(userId: Long, image: MultipartFile, isProfilePhoto: Boolean): ResponseEntity<Map<String, String>> {
        if (image.isEmpty) {
            return ResponseEntity.badRequest().body(mapOf("message" to "File is empty"))
        }

        val profile = profileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        // Store files and get public URLs
        val imageUrls = storageService.store(image, userId)

        // Save image metadata in database
        val photo = ProfilePhoto(
            profile = profile,
            url = imageUrls["original"].orEmpty(),
            isProfilePhoto = isProfilePhoto
        )
        profilePhotoRepository.save(photo)

        profile.photos = (profile.photos ?: mutableListOf()).apply {
            add(photo)
        }
        profileRepository.save(profile)

        return ResponseEntity.ok(mapOf(
            "message" to "Upload successful",
            "original" to imageUrls["original"].orEmpty(),
            "resized" to imageUrls["resized"].orEmpty(),
            "thumbnail" to imageUrls["thumbnail"].orEmpty()
        ))
    }

    @Transactional
    fun deleteImage(userId: Long, photoId: Long): ResponseEntity<Map<String, String>> {
        val profile = profileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        val photo = profilePhotoRepository.findById(photoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User photo not found"))

        if (photo.profile.id != userId) {
            return ResponseEntity.badRequest().body(mapOf("message" to "User photo does not belong to user"))
        }

        try {
            // Delete all image files associated with this photo
            storageService.delete(userId, photo.url)

            // Remove the photo from the user's list **without replacing the collection**
            profile.photos.remove(photo)

            // Save the updated user profile
            profileRepository.save(profile)

            // Delete metadata from the database
            profilePhotoRepository.delete(photo)

            return ResponseEntity.ok(mapOf("message" to "Image deleted"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to "Failed to delete image", "error" to e.message.orEmpty()))
        }
    }

    @Transactional
    fun updateImage(userId: Long, photoId: Long, isProfilePhoto: Boolean): ResponseEntity<Map<String, String>> {
        val profile = profileRepository.findById(userId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User profile not found"))

        val photo = profilePhotoRepository.findById(photoId).orElse(null)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "User photo not found"))

        if (photo.profile.id != userId) {
            return ResponseEntity.badRequest().body(mapOf("message" to "User photo does not belong to user"))
        }

        val allProfilePhotos = profilePhotoRepository.findByProfileId(userId)

        if (!isProfilePhoto) {
            // Deny request if this is the only profile photo being turned off
            val currentProfilePhoto = allProfilePhotos.find { it.isProfilePhoto }
            if (currentProfilePhoto?.id == photo.id) {
                return ResponseEntity.badRequest().body(mapOf("message" to "There must always be one profile photo"))
            }
        } else {
            // If setting this as profile photo, remove profile status from all others
            allProfilePhotos.forEach { photo ->
                if (photo.id != photoId && photo.isProfilePhoto) {
                    photo.isProfilePhoto = false
                    profilePhotoRepository.save(photo)
                }
            }
        }

        // Set the selected photo as the profile photo
        photo.isProfilePhoto = isProfilePhoto
        profilePhotoRepository.save(photo)

        return ResponseEntity.ok(mapOf("message" to "Image updated", "isProfilePhoto" to isProfilePhoto.toString()))
    }
}