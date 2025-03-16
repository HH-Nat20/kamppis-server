package nat20.kamppisserver.storage

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.nio.file.*
import java.util.stream.Stream
import org.springframework.util.FileSystemUtils


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
        Files.createDirectories(userDir) // Ensure user directory exists

        val sanitizedFilename = "${System.currentTimeMillis()}-${file.originalFilename?.replace("\\s+".toRegex(), "_")}"
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

        return "/api/images/get/$userId/$sanitizedFilename" // Public URL for access
    }

    override fun loadAll(): Stream<Path> {
        return Files.walk(rootLocation, 1)
            .filter { path -> !path.equals(rootLocation) }
            .map(rootLocation::relativize)
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() && resource.isReadable) {
                return resource
            } else {
                throw RuntimeException("Could not read file: $filename")
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }
}