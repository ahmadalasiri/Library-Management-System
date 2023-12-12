import java.sql.Timestamp

case class User(
    id: Long,
    name: String,
    email: String,
    password: String,
    createdAt: Timestamp
)

object SlickTables {
  import slick.jdbc.PostgresProfile.api._

  class UserTable(tag: Tag) extends Table[User](tag, Some("users"), "User") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email")
    def password = column[String]("password")
    def createdAt = column[Timestamp](
      "created_at",
      O.Default(new Timestamp(System.currentTimeMillis()))
    )

    // mapping function to the case class  User

    override def * =
      (id, name, email, password, createdAt) <> (User.tupled, User.unapply)

  }
  lazy val userTable = TableQuery[UserTable]
}
