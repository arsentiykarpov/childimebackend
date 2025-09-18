import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import cloud.karpov.edge.AuthUser
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.core.Table.Dual.uniqueIndex
import org.jetbrains.exposed.v1.core.Table.Dual.varchar
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.jdbc.selectAll

object Users : IntIdTable() {
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
}

@Serializable
data class UserDbDto(val id: Int, val email: String, val passwordHash: String)

fun createUser(email: String, hash: String): UserDbDto {
    return transaction {
        val insertedId = Users.insertAndGetId {
            it[Users.email] = email
            it[Users.passwordHash] = hash
        }.value

        UserDbDto(
              id = insertedId,
              email = email,
              passwordHash = hash
          )
    }
}

fun getUsers(): List<UserDbDto> {
  return transaction {
    Users.selectAll().map {
      UserDbDto(id = it[Users.id].value, email = it[Users.email], passwordHash = it[Users.passwordHash])
    }.toList()
  }
}

fun getUserById(userId: Int): UserDbDto? = transaction {
    Users.select((Users.id eq userId))
        .map {
            UserDbDto(
                id = it[Users.id].value,
                email = it[Users.email],
                passwordHash = it[Users.passwordHash]
            )
        }
        .singleOrNull()
}


fun ResultRow.toUserDto() = UserDbDto(
    id = this[Users.id].value,
    email = this[Users.email],
    passwordHash = this[Users.passwordHash]
)

