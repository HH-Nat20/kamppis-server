package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import nat20.kamppisserver.domain.ProfilePhoto
import nat20.kamppisserver.domain.User
import nat20.kamppisserver.domain.UserProfile
import nat20.kamppisserver.domain.enums.Gender
import nat20.kamppisserver.repository.ProfileRepository
import nat20.kamppisserver.repository.ProfilePhotoRepository
import nat20.kamppisserver.security.JwtUtils
import nat20.kamppisserver.storage.FileSystemStorageService
import nat20.kamppisserver.security.SecurityConfig
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.test.Test

@WebMvcTest(ImageController::class)
@Import(SecurityConfig::class, JwtUtils::class)
class ImageControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val jwtUtils: JwtUtils
) {

    @MockkBean
    private lateinit var profileRepository: ProfileRepository

    @MockkBean
    private lateinit var profilePhotoRepository: ProfilePhotoRepository

    @MockkBean
    private lateinit var storageService: FileSystemStorageService

    val jwt = jwtUtils.generateJwtToken("test@example.com")

    lateinit var user: User
    lateinit var userProfile: UserProfile

    @BeforeEach
    fun init() {
        StandaloneSetup.setup()
        user = StandaloneSetup.user1
        userProfile = StandaloneSetup.userProfile
    }

    @Test
    fun `getImage returns resource if found`() {
        val filename = "test.jpg"

        val testFile = File("build/tmp/test.jpg")
        testFile.parentFile.mkdirs()
        testFile.writeText("fake image data")

        val resourceMock = mockk<Resource> {
            every { exists() } returns true
            every { isReadable } returns true
            every { file } returns testFile
            every { contentLength() } returns testFile.length()
            every { inputStream } returns ByteArrayInputStream("fake image data".toByteArray())
        }

        every { storageService.loadAsResource("${user.id}/$filename") } returns resourceMock

        mockMvc.get("/api/images/get/${user.id}/$filename") {
            header("Authorization", "Bearer $jwt")
        }
            .andExpect {
                status { isOk() }
                header { string("Content-Type", "image/jpeg") }
            }

        testFile.delete()
    }

    @Test
    fun `uploadImage uploads file and returns URLs`() {
        val file = MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".toByteArray())
        val imageUrls = mapOf(
            "original" to "url1",
            "resized" to "url2",
            "thumbnail" to "url3"
        )

        every { user.id?.let { profileRepository.findById(it) } } returns Optional.of(userProfile)
        every { user.id?.let { storageService.store(file, it) } } returns imageUrls
        every { profilePhotoRepository.save(any()) } answers { firstArg() }
        every { profileRepository.save(any()) } returns userProfile

        mockMvc.perform(
            multipart("/api/images/${user.id}")
                .file(file)
                .param("isProfilePhoto", "true")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Upload successful"))
            .andExpect(jsonPath("$.original").value("url1"))
    }

    @Test
    fun `deleteImage deletes image if valid`() {
        val photoId = 2L
        val photo = ProfilePhoto(id = photoId, profile = userProfile, url = "url1", isProfilePhoto = true)

        userProfile.photos = mutableListOf(photo)

        every { user.id?.let { profileRepository.findById(it) } } returns Optional.of(userProfile)
        every { profilePhotoRepository.findById(photoId) } returns Optional.of(photo)
        every { storageService.delete(user.id!!, photo.url) } just Runs
        every { profileRepository.save(any()) } returns userProfile
        every { profilePhotoRepository.delete(photo) } just Runs

        mockMvc.perform(
            delete("/api/images/${user.id}/${photo.id}")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Image deleted"))
    }

    @Test
    fun `updateImage sets new profile photo if valid`() {
        val photoId = 2L
        val currentPhoto = ProfilePhoto(id = photoId, profile = userProfile, url = "url1", isProfilePhoto = false)

        every { user.id?.let { profileRepository.findById(it) } } returns Optional.of(userProfile)
        every { profilePhotoRepository.findById(photoId) } returns Optional.of(currentPhoto)
        every { user.id?.let { profilePhotoRepository.findByProfileId(it) } } returns listOf(currentPhoto)
        every { profilePhotoRepository.save(any()) } answers { firstArg() }

        mockMvc.perform(
            put("/api/images/${user.id}/$photoId")
                .param("isProfilePhoto", "true")
                .header("Authorization", "Bearer $jwt")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message").value("Image updated"))
            .andExpect(jsonPath("$.isProfilePhoto").value("true"))
    }
}