package nat20.kamppisserver.api


import nat20.kamppisserver.service.ImageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.ResponseEntity
import org.springframework.core.io.Resource
import org.springframework.transaction.annotation.Transactional

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val imageService: ImageService,
) {

    @GetMapping("/get/{userId}/{filename:.+}")
    fun getImage(@PathVariable userId: Long, @PathVariable filename: String): ResponseEntity<Resource> {
        return imageService.getImage(userId, filename)
    }

    @PostMapping("/{userId}")
    fun uploadImage(
        @PathVariable userId: Long,
        @RequestParam("image") image: MultipartFile,
        @RequestParam("isProfilePhoto", defaultValue = "false") isProfilePhoto: Boolean
    ): ResponseEntity<Map<String, String>> {
        return imageService.uploadImage(userId, image, isProfilePhoto)
    }

    @DeleteMapping("/{userId}/{photoId}")
    fun deleteImage(@PathVariable userId: Long, @PathVariable photoId: Long): ResponseEntity<Map<String, String>> {
        return imageService.deleteImage(userId, photoId)
    }

    @PutMapping("/{userId}/{photoId}")
    fun updateImage(
        @PathVariable userId: Long,
        @PathVariable photoId: Long,
        @RequestParam("isProfilePhoto", defaultValue = "false") isProfilePhoto: Boolean
    ): ResponseEntity<Map<String, String>> {
        return imageService.updateImage(userId, photoId, isProfilePhoto)
    }
}
