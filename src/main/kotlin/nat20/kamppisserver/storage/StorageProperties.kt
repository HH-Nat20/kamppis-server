package nat20.kamppisserver.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class StorageProperties {
    
    //@Value("\${UPLOAD_DIR:uploads}")
    //lateinit var location: String

    fun fullPath(): String {
        //val homeDir = System.getenv("HOME") ?: "/var/www"
        //return "$homeDir/$location"
        return "/var/www/uploads" // Hardcoded for now
    }
}
