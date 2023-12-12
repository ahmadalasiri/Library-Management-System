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
case class Transaction(
    id: Long,
    userNationalId: String,
    bookId: Long,
    checkoutDate: Timestamp,
    dueDate: Timestamp,
    returnDate: Timestamp,
    fineAmount: Double
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

  class TransactionTable(tag: Tag) extends Table[Transaction](tag, Some("transactoins"), "Transaction") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userNationalId = column[String]("user_national_id")
    def bookId = column[Long]("book_id")
    def checkoutDate = column[Timestamp]("checkout_date")
    def dueDate = column[Timestamp]("due_date")
    def returnDate = column[Timestamp]("return_date")
    def fineAmount = column[Double]("fine_amount")

    def userNationalIdFK = foreignKey("user_national_id_fk", userNationalId, userTable)(
      _.nationalId,
      onUpdate = ForeignKeyAction.Restrict, // if the user national id is updated, restrict the update
      onDelete = ForeignKeyAction.Cascade // if the user is deleted, delete the transaction
    )

    def bookIdFK = foreignKey("book_id_fk", bookId, bookTable)(
      _.id,
      onUpdate = ForeignKeyAction.Restrict,
      onDelete = ForeignKeyAction.Cascade
    )

    override def * = (id, userNationalId, bookId, checkoutDate, dueDate, returnDate, fineAmount) <> (Transaction.tupled, Transaction.unapply)

  }

  lazy val userTable = TableQuery[UserTable] // TableQuery object for the User table
  lazy val bookTable = TableQuery[BookTable] // TableQuery object for the Book table
  lazy val transactionTable = TableQuery[TransactionTable] // TableQuery object for the Transaction table
}
