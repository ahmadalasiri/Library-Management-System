import java.sql.Timestamp

case class User(
    nationalId: String,
    name: String,
    createdAt: Timestamp
)

case class Book(
    id: Long,
    title: String,
    author: String,
    isbn: String,
    availability: Boolean,
    location: String,
    createdAt: Timestamp
)

object SlickTables {
  import slick.jdbc.PostgresProfile.api._

  class UserTable(tag: Tag) extends Table[User](tag, Some("users"), "User") {

    def nationalId = column[String]("national_id", O.PrimaryKey)
    def name = column[String]("name")
    def createdAt = column[Timestamp](
      "created_at",
      O.Default(new Timestamp(System.currentTimeMillis()))
    )

    // mapping function to the case class User
    override def * = (nationalId, name, createdAt) <> (User.tupled, User.unapply)
  }

  class BookTable(tag: Tag) extends Table[Book](tag, Some("books"), "Book") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def author = column[String]("author")
    def isbn = column[String]("isbn")
    def availability = column[Boolean]("availability")
    def location = column[String]("location")
    def createdAt = column[Timestamp](
      "created_at",
      O.Default(new Timestamp(System.currentTimeMillis()))
    )

    // mapping function to the case class  Book

    override def * = (id, title, author, isbn, availability, location, createdAt) <> (Book.tupled, Book.unapply)

  }

  lazy val userTable = TableQuery[UserTable] // TableQuery object for the User table
  lazy val bookTable = TableQuery[BookTable] // TableQuery object for the Book table
}
