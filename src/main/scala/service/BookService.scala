import scala.concurrent.{Future, Await}
import java.sql.Timestamp

object bookService {
  import slick.jdbc.PostgresProfile.api._

  def addBook(title: String, author: String, price: Double, isbn: String): Unit = {
    val book = Book(0, title, author, price, isbn, new Timestamp(System.currentTimeMillis()))
    val queryDescription = SlickTables.bookTable returning SlickTables.bookTable.map { _.id } into ((book, id) => book.copy(id = id)) += book
    val futuredResult = Connection.db.run(queryDescription)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result.id == 0) println("Failed to add book")
    else println("Book added successfully, book => " + result)
  }

  def removeBook(id: Long): Unit = {
    val queryDescription = SlickTables.bookTable.filter(_.id === id).delete
    val futuredResult = Connection.db.run(queryDescription)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result == 0) println("No book found")
    else println("Book removed successfully")
  }

  def updateBook(id: Long, title: String, author: String, price: Double, isbn: String): Unit = {
    val queryDescription = SlickTables.bookTable
      .filter(_.id === id)
      .map(book => (book.title, book.author, book.price, book.isbn))
      .update((title, author, price, isbn))
    val futuredResult = Connection.db.run(queryDescription)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result == 0) println("No book found")
    else println("Book updated successfully")
  }

  def getAllBooks(): Unit = {
    val queryDescription = SlickTables.bookTable
    val futuredResult = Connection.db.run(queryDescription.result)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result.isEmpty) println("No books found")
    else
      result.foreach(book => println("Book => " + book))
  }

  def getBookById(id: Long): Unit = {
    val queryDescription = SlickTables.bookTable.filter(_.id === id)
    val futuredResult = Connection.db.run(queryDescription.result)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result.isEmpty) println("No book found")
    else
      result.foreach(book => println("Book => " + book))
  }

}
