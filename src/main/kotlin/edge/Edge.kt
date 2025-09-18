package cloud.karpov.edge

import createUser
import kotlinx.serialization.Serializable
import UserDbDto
import at.favre.lib.crypto.bcrypt.BCrypt
import getUsers

class Edge {
  fun createUser(user: AuthUser): UserDbDto {
    val passHash = BCrypt.withDefaults().hashToString(12, user.password.toCharArray())
    val createdUser = createUser(user.email, passHash)
    return getUser(createdUser.id)
  }

  fun getUser(id: Int): UserDbDto {
    return getUsers().first()
  }
}

@Serializable
data class AuthUser(val email: String, val password: String)
