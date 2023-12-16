//==============================================================
import scala.concurrent.{Future, Await}
// import java.sql.Timestamp

// class TransactionService {
//   import slick.jdbc.PostgresProfile.api._

//   def addTransaction(userNationalId: String, bookId: Long, checkoutDate: Timestamp, dueDate: Timestamp): Unit = {
//     val transaction = Transaction(0, userNationalId, bookId, checkoutDate, dueDate, null, 0)
//     val queryDescription = SlickTables.transactionTable returning SlickTables.transactionTable.map { _.id } into ((transaction, id) => transaction.copy(id = id)) += transaction
//     val futuredResult = Connection.db.run(queryDescription)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result.id == 0) println("Failed to add transaction")
//     else println("Transaction added successfully, transaction => " + result)

//   }

//   def removeTransaction(id: Long): Unit = {

//     val queryDescription = SlickTables.transactionTable.filter(_.id === id).delete
//     // delete all
//     // val queryDescription = SlickTables.transactionTable.delete
//     val futuredResult = Connection.db.run(queryDescription)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result == 0) println("No transaction found")
//     else println("Transaction removed successfully")

//   }

//   def updateTransaction(id: Long, userNationalId: String, bookId: Long, checkoutDate: Timestamp, dueDate: Timestamp, returnDate: Timestamp, fineAmount: Double): Unit = {
//     try {
//       val queryDescription = SlickTables.transactionTable
//         .filter(_.id === id)
//         .map(transaction => (transaction.userNationalId, transaction.bookId, transaction.checkoutDate, transaction.dueDate, transaction.returnDate, transaction.fineAmount))
//         .update((userNationalId, bookId, checkoutDate, dueDate, returnDate, fineAmount))
//       val futuredResult = Connection.db.run(queryDescription)
//       val result = Await.result(
//         futuredResult,
//         scala.concurrent.duration.Duration.Inf
//       )
//       if (result == 0) println("No transaction found")
//       else println("Transaction updated successfully")
//     } catch {
//       case e: IllegalArgumentException =>
//         println("Error: Invalid timestamp format. Please enter timestamps in the format yyyy-mm-dd hh:mm:ss[.fffffffff]")
//       case e: Exception =>
//         println(s"An unexpected error occurred: ${e.getMessage}")
//     }
//   }

//   def getAllTransactions(): Unit = {
//     val queryDescription = SlickTables.transactionTable
//     val futuredResult = Connection.db.run(queryDescription.result)
//     val result = Await.result(
//       futuredResult,
//       scala.concurrent.duration.Duration.Inf
//     )
//     if (result.isEmpty)
//       println("No transactions found")
//     else
//       result.foreach(transaction => println("Transaction => " + transaction))

//   }

//   def getTransactionById(id: Long): Unit = {
//     try {
//       val queryDescription = SlickTables.transactionTable.filter(_.id === id)
//       val futuredResult = Connection.db.run(queryDescription.result)
//       val result = Await.result(
//         futuredResult,
//         scala.concurrent.duration.Duration.Inf
//       )
//       if (result.isEmpty)
//         println("No transaction found")
//       else
//         result.foreach(transaction => println("Transaction => " + transaction))
//     } catch {
//       case e: Exception =>
//         println(s"An unexpected error occurred: ${e.getMessage}")
//     }
//   }

//   def getTransactionByUserNationalId(userNationalId: String): Unit = {
//     try {
//       val queryDescription = SlickTables.transactionTable.filter(_.userNationalId === userNationalId)
//       val futuredResult = Connection.db.run(queryDescription.result)
//       val result = Await.result(
//         futuredResult,
//         scala.concurrent.duration.Duration.Inf
//       )
//       if (result.isEmpty)
//         println("No transaction found")
//       else
//         result.foreach(transaction => println("Transaction => " + transaction))
//     } catch {
//       case e: Exception =>
//         println(s"An unexpected error occurred: ${e.getMessage}")
//     }
//   }

//   def getTransactionByBookId(bookId: Long): Unit = {
//     try {
//       val queryDescription = SlickTables.transactionTable.filter(_.bookId === bookId)
//       val futuredResult = Connection.db.run(queryDescription.result)
//       val result = Await.result(
//         futuredResult,
//         scala.concurrent.duration.Duration.Inf
//       )
//       if (result.isEmpty)
//         println("No transaction found")
//       else
//         result.foreach(transaction => println("Transaction => " + transaction))
//     } catch {
//       case e: Exception =>
//         println(s"An unexpected error occurred: ${e.getMessage}")
//     }
//   }

// }

//==============================================================
import akka.actor.Actor
import java.sql.Timestamp

class TransactionActor extends Actor {

  val transactionService = new TransactionService()

  def receive: Receive = {
    case AddTransaction(userNationalId, bookId, checkoutDate, dueDate) =>
      transactionService.addTransaction(userNationalId, bookId, checkoutDate, dueDate)

    case RemoveTransaction(id) =>
      transactionService.removeTransaction(id)

    case UpdateTransaction(id, userNationalId, bookId, checkoutDate, dueDate, returnDate, fineAmount) =>
      transactionService.updateTransaction(id, userNationalId, bookId, checkoutDate, dueDate, returnDate, fineAmount)

    case GetAllTransactions() =>
      transactionService.getAllTransactions()

    case GetTransactionById(id) =>
      transactionService.getTransactionById(id)

    case GetTransactionByUserNationalId(userNationalId) =>
      transactionService.getTransactionByUserNationalId(userNationalId)

    case GetTransactionByBookId(bookId) =>
      transactionService.getTransactionByBookId(bookId)
  }
}

case class AddTransaction(userNationalId: String, bookId: Long, checkoutDate: Timestamp, dueDate: Timestamp)
case class RemoveTransaction(id: Long)
case class UpdateTransaction(id: Long, userNationalId: String, bookId: Long, checkoutDate: Timestamp, dueDate: Timestamp, returnDate: Timestamp, fineAmount: Double)
case class GetAllTransactions()
case class GetTransactionById(id: Long)
case class GetTransactionByUserNationalId(userNationalId: String)
case class GetTransactionByBookId(bookId: Long)
