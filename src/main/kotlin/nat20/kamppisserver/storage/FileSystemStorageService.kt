package nat20.kamppisserver.storage

import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage
import java.nio.file.StandardCopyOption
import javax.imageio.ImageIO
import org.springframework.stereotype.Service
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.File
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

    override fun store(file: MultipartFile, userId: Long): Map<String, String> {
        if (file.isEmpty) throw RuntimeException("Cannot store empty file.")

        val userDir = rootLocation.resolve(userId.toString())
        Files.createDirectories(userDir)

        val originalExtension = file.originalFilename?.substringAfterLast(".", "jpg") ?: "jpg"
        val baseFilename = UUID.randomUUID().toString()
        
        val originalFile = userDir.resolve("$baseFilename-original.$originalExtension").toFile()
        val resizedFile = userDir.resolve("$baseFilename-resized.jpg").toFile()
        val thumbnailFile = userDir.resolve("$baseFilename-thumbnail.jpg").toFile()

        // Prevent directory traversal attack
        if (!originalFile.toPath().startsWith(userDir.toAbsolutePath())) {
            throw RuntimeException("Cannot store file outside the user directory.")
        }

        try {
            // Save original file
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }

            // Convert to BufferedImage for resizing
            val bufferedImage: BufferedImage = ImageIO.read(originalFile)

            // Resize the images
            resizeImage(bufferedImage, 1024, 1024, resizedFile, crop = false) // No crop
            resizeImage(bufferedImage, 300, 300, thumbnailFile, crop = true)  // Cropped thumbnail


        } catch (e: IOException) {
            throw RuntimeException("Failed to store file", e)
        }

        // Return URLs for all three versions
        return mapOf(
            "original" to "$userId/${originalFile.name}",
            "resized" to "$userId/${resizedFile.name}",
            "thumbnail" to "$userId/${thumbnailFile.name}"
        )
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
        
    private fun resizeImage(inputImage: BufferedImage, width: Int, height: Int, outputFile: File, crop: Boolean = false) {
        if (crop) {
            Thumbnails.of(inputImage)
                .size(width, height)
                .crop(net.coobird.thumbnailator.geometry.Positions.CENTER) // Crop to center
                .outputFormat("jpg")
                .toFile(outputFile)
        } else {
            Thumbnails.of(inputImage)
                .size(width, height) // Preserve aspect ratio
                .outputFormat("jpg")
                .toFile(outputFile)
        }
    }

}
