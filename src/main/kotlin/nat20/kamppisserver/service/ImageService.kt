package nat20.kamppisserver.service

import jakarta.transaction.Transactional
import nat20.kamppisserver.domain.Profile
import nat20.kamppisserver.domain.ProfilePhoto
import nat20.kamppisserver.repository.ProfilePhotoRepository
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.storage.FileSystemStorageService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.text.orEmpty

@Service
class ImageService(
    private val storageService: FileSystemStorageService,
    private val profilePhotoRepository: ProfilePhotoRepository,
    private val profileRepository: ProfileRepository
) {

    fun saveImageMetaData(profile: Profile, imageUrls: Map<String, String>, isProfilePhoto: Boolean) {
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
    }

    fun deleteUserPhoto(profile: Profile, photo: ProfilePhoto, userId: Long) {

        // Delete all image files associated with this photo
        storageService.delete(userId, photo.url)

        // Remove the photo from the user's list **without replacing the collection**
        profile.photos.remove(photo)

        // Save the updated user profile
        profileRepository.save(profile)

        // Delete metadata from the database
        profilePhotoRepository.delete(photo)
    }

    @Transactional
    fun updateImage(photo: ProfilePhoto, userId: Long, isProfilePhoto: Boolean) {
        val allProfilePhotos = profilePhotoRepository.findByProfileId(userId)

        if (!isProfilePhoto) {
            // Deny request if this is the only profile photo being turned off
            val currentProfilePhoto = allProfilePhotos.find { it.isProfilePhoto }
            if (currentProfilePhoto?.id == photo.id) {
                throw IllegalArgumentException()
            }
        } else {
            // If setting this as profile photo, remove profile status from all others
            allProfilePhotos.forEach { photo ->
                if (photo.id != photo.id && photo.isProfilePhoto) {
                    photo.isProfilePhoto = false
                    profilePhotoRepository.save(photo)
                }
            }
        }

        // Set the selected photo as the profile photo
        photo.isProfilePhoto = isProfilePhoto
        profilePhotoRepository.save(photo)
    }
}