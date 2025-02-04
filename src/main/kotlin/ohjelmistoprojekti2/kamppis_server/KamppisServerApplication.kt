package ohjelmistoprojekti2.kamppis_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["ohjelmistoprojekti2.kamppis_server"])
class KamppisServerApplication

fun main(args: Array<String>) {
	runApplication<KamppisServerApplication>(*args)
}
