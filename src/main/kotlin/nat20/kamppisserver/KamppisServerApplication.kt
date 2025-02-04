package nat20.kamppisserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KamppisServerApplication

fun main(args: Array<String>) {
    runApplication<KamppisServerApplication>(*args)
}
