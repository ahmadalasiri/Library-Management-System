import java.sql.Timestamp

case class User(
    id: Long,
    name: String,
    email: String,
    password: String,
    createdAt: Timestamp
)

case class Book(
    id: Long,
    title: String,
    author: String,
    price: Double,
    isbn: String,
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

  class BookTable(tag: Tag) extends Table[Book](tag, Some("books"), "Book") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def author = column[String]("author")
    def price = column[Double]("price")
    def isbn = column[String]("isbn")
    def createdAt = column[Timestamp](
      "created_at",
      O.Default(new Timestamp(System.currentTimeMillis()))
    )

    // mapping function to the case class  Book

    override def * =
      (id, title, author, price, isbn, createdAt) <> (Book.tupled, Book.unapply)

  }
  lazy val userTable = TableQuery[UserTable] // TableQuery object for the User table
  lazy val bookTable = TableQuery[BookTable] // TableQuery object for the Book table
}
