package nat20.kamppisserver.repository

import nat20.kamppisserver.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>