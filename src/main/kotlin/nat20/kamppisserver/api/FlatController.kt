package nat20.kamppisserver.api

import jakarta.validation.Valid
import nat20.kamppisserver.domain.FlatDTO
import nat20.kamppisserver.service.FlatService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/flats")
@Validated
class FlatController(private val service: FlatService) {

    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<FlatDTO>> {
        return ResponseEntity.ok(service.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<FlatDTO> {
        return ResponseEntity.ok(service.findById(id))
    }

    @PostMapping
    fun add(@Valid @RequestBody flat: FlatDTO): ResponseEntity<FlatDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(flat))
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody flat: FlatDTO, @PathVariable("id") id : Long): ResponseEntity<FlatDTO> {
        return ResponseEntity.ok(service.update(flat, id))
    }
}