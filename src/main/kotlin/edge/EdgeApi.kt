package cloud.karpov.edge

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import getUsers
import io.ktor.server.routing.get
import createUser

fun Route.edgeRoutes() {
  val edge = Edge()
  route("/edge/create_user") {
    post {
      val request = call.receive<AuthUser>()
      val user = edge.createUser(request)
      call.respond(AuthUser(user.email, user.passwordHash))
    }
  }
  route("/edge/get_users") {
    get {
      val users = getUsers()
      call.respond(users)
    }
  }
}
