import java.nio.file.*
import org.springframework.util.FileSystemUtils
import java.util.UUID

@Service
class FileSystemStorageService(private val properties: StorageProperties) : StorageService {

    private val rootLocation: Path = Paths.get(properties.fullPath())

    init {
        init()
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw RuntimeException("Could not initialize storage", e)
        }
    }

    override fun store(file: MultipartFile, userId: Long): String {
        if (file.isEmpty) throw RuntimeException("Cannot store empty file.")

        val userDir = rootLocation.resolve(userId.toString())
        Files.createDirectories(userDir)

        val originalExtension = file.originalFilename?.substringAfterLast(".", "jpg") ?: "jpg"
        val sanitizedFilename = "${UUID.randomUUID()}.$originalExtension"

        val destinationFile = userDir.resolve(sanitizedFilename).normalize().toAbsolutePath()

        if (!destinationFile.parent.equals(userDir.toAbsolutePath())) {
            throw RuntimeException("Cannot store file outside the user directory.")
        }

        try {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to store file", e)
        }

        return "https://kamppis.hellmanstudios.fi/api/images/get/$userId/$sanitizedFilename"
    }

    override fun deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation)
        } catch (e: IOException) {
            throw RuntimeException("Failed to delete files", e)
        }
    }
}
