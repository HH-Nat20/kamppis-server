package nat20.kamppisserver.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.test.Test

@WebMvcTest(ImageController::class)
@Import(SecurityConfig::class)
class ImageControllerTest @Autowired constructor(
    val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var profileRepository: ProfileRepository

    @MockkBean
    private lateinit var profilePhotoRepository: ProfilePhotoRepository

    @MockkBean
    private lateinit var storageService: FileSystemStorageService

    lateinit var jwt: String
    lateinit var user: User
    lateinit var userProfile: UserProfile

    @BeforeEach
    fun setup() {
        jwt = JwtUtils.generateJwtToken("test@example.com")

        user = User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            dateOfBirth = LocalDate.of(1980, 1, 1),
            gender = Gender.MALE,
            id = 1L
        )

        userProfile = UserProfile(
            user = user,
            bio = "Test bio",
            photos = mutableListOf()
        )
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

}