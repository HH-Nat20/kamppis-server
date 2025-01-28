package ohjelmistoprojekti2.kamppis_server.repository

import ohjelmistoprojekti2.kamppis_server.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>