package cloud.karpov

import Users
import io.ktor.server.application.Application
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabase() {
  val config = environment.config.config("database")
  val hikariConfig = HikariConfig().apply {
    driverClassName = config.property("driver").getString()
    jdbcUrl = config.property("url").getString()
    username = config.property("user").getString()
    password = config.property("password").getString()
    maximumPoolSize = 10
    isAutoCommit = false
    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
  }

  val dataSource = HikariDataSource(hikariConfig)
  Database.connect(dataSource)
  transaction {
    SchemaUtils.create(Users)
  }
}
