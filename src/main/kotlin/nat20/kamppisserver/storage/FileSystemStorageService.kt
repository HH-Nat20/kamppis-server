package nat20.kamppisserver.storage

import org.springframework.stereotype.Service
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.*
import java.util.UUID
import java.util.stream.Stream
import org.springframework.util.FileSystemUtils
import java.net.MalformedURLException

@Service
class FileSystemStorageService(private val properties: StorageProperties) : StorageService {

    private val rootLocation: Path = try {
        val path = Paths.get(properties.fullPath())
        Files.createDirectories(path) // Ensure directory exists
        path
    } catch (e: Exception) {
        Paths.get(System.getProperty("java.io.tmpdir"), "storage").also {
            Files.createDirectories(it) // Ensure tmp storage exists
        }
    }

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

        if (!destinationFile.startsWith(userDir.toAbsolutePath())) {
            throw RuntimeException("Cannot store file outside the user directory.")
        }

        try {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to store file", e)
        }

        return "https://kamppis.hellmanstudios.fi/images/$userId/$sanitizedFilename" // TODO: Change path
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        return try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() && resource.isReadable) {
                resource
            } else {
                throw RuntimeException("Could not read file: $filename")
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException("Could not read file: $filename", e)
        }
    }

    override fun loadAll(): Stream<Path> {
        return try {
            Files.walk(rootLocation, 1)
                .filter { path -> !path.equals(rootLocation) }
                .map(rootLocation::relativize)
        } catch (e: IOException) {
            throw RuntimeException("Failed to read stored files", e)
        }
    }

    override fun deleteAll() {
        try {
            FileSystemUtils.deleteRecursively(rootLocation)
        } catch (e: IOException) {
            throw RuntimeException("Failed to delete files", e)
        }
    }
}
