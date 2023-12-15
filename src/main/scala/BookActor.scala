import scala.concurrent.{Future, Await}
// import java.sql.Timestamp

// class BookService {
//   import slick.jdbc.PostgresProfile.api._

//   def addBook(title: String, author: String, isbn: String, availability: Boolean, location: String): Unit = {
//     val book = Book(0, title, author, isbn, availability, location, new Timestamp(System.currentTimeMillis()))
//     val queryDescription = SlickTables.bookTable returning SlickTables.bookTable.map { _.id } into ((book, id) => book.copy(id = id)) += book
//     val futuredResult = Connection.db.run(queryDescription)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result.id == 0) println("Failed to add book")
//     else println("Book added successfully, book => " + result)
//   }

//   def removeBook(id: Long): Unit = {
//     val queryDescription = SlickTables.bookTable.filter(_.id === id).delete
//     // delete all
//     // val queryDescription = SlickTables.bookTable.delete
//     val futuredResult = Connection.db.run(queryDescription)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result == 0) println("No book found")
//     else println("Book removed successfully")
//   }

//   def updateBook(id: Long, title: String, author: String, isbn: String, availability: Boolean, location: String): Unit = {
//     val queryDescription = SlickTables.bookTable
//       .filter(_.id === id)
//       .map(book => (book.title, book.author, book.isbn, book.availability, book.location))
//       .update((title, author, isbn, availability, location))
//     val futuredResult = Connection.db.run(queryDescription)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result == 0) println("No book found")
//     else println("Book updated successfully")
//   }

//   def getAllBooks(): Unit = {
//     val queryDescription = SlickTables.bookTable
//     val futuredResult = Connection.db.run(queryDescription.result)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result.isEmpty) println("No books found")
//     else
//       result.foreach(book => println("Book => " + book))
//   }

//   def getBookById(id: Long): Unit = {
//     val queryDescription = SlickTables.bookTable.filter(_.id === id)
//     val futuredResult = Connection.db.run(queryDescription.result)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result.isEmpty) println("No book found")
//     else
//       result.foreach(book => println("Book => " + book))
//   }

// }

//==============================================================
import akka.actor.Actor

class BookActor extends Actor {
  val bookService = new BookService()

  def receive = {
    case AddBook(title, author, isbn, availability, location) =>
      bookService.addBook(title, author, isbn, availability, location)

    case RemoveBook(id) =>
      bookService.removeBook(id)

    case UpdateBook(id, title, author, isbn, availability, location) =>
      bookService.updateBook(id, title, author, isbn, availability, location)

    case GetAllBooks() =>
      bookService.getAllBooks()

    case GetBookById(id) =>
      bookService.getBookById(id)

    case _ => println("Invalid message")

  }
}

case class AddBook(title: String, author: String, isbn: String, availability: Boolean, location: String)
case class RemoveBook(id: Long)
case class UpdateBook(id: Long, title: String, author: String, isbn: String, availability: Boolean, location: String)
case class GetAllBooks()
case class GetBookById(id: Long)
