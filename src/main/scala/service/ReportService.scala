import scala.concurrent.{Future, Await}

//  report generation service , this service is responsible for generating reports  and statistics about the library
//  the reports are generated in the form of csv files  and stored in the reports folder
//  the reports are generated using the slick library and the scala language
//  the reports are generated using the data stored in the database
//  kind of reports generated:
//  1- user report :
//    1.1- users that have borrowed books
//    1.2- users that have not returned books on time
//    1.3- users that have not returned books at all
//  2- book report :
//    2.1- books that are borrowed
//    2.2- books that are not borrowed at all
//    2.3- books that are borrowed and not returned on time
//  3- transaction report :
//    3.1- transactions that are not returned on time
//    3.2- transactions that are not returned at all
//    3.3- transactions that are returned on time
//    3.4- transactions that are returned late
//    3.5- transactions that are returned early
//    3.6- transactions that are returned with fine

object reportService {
  import slick.jdbc.PostgresProfile.api._

  val currentDir = new java.io.File(".").getCanonicalPath
//   println(currentDir)
  val reportsDir = new java.io.File(s"$currentDir/reports")
//   println(reportsDir)
  // Create the reports directory if it doesn't exist
  if (!reportsDir.exists()) {
    reportsDir.mkdir()
  }

  //  generate user report
  def generateUserReport(): Unit = {
    //  get all users
    val queryDescription = SlickTables.userTable
    val futuredResult = Connection.db.run(queryDescription.result)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    //  if no users found
    if (result.isEmpty) println("No users found")
    else {
      //  create a csv file
      val pw = new java.io.PrintWriter(new java.io.File("reports/user_report.csv"))
      //  write the header of the csv file
      pw.write("national_id,name,created_at\n")
      //  write the data of the csv file
      result.foreach(user => pw.write(user.nationalId + "," + user.name + "," + user.createdAt + "\n"))
      //  close the csv file
      pw.close()
      println("User report generated successfully")
    }
  }

  //  generate user report
  def generateBookReport(): Unit = {
    //  get all books
    val queryDescription = SlickTables.bookTable
    val futuredResult = Connection.db.run(queryDescription.result)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    //  if no books found
    if (result.isEmpty) println("No books found")
    else {
      //  create a csv file
      val pw = new java.io.PrintWriter(new java.io.File("reports/book_report.csv"))
      //  write the header of the csv file
      pw.write("id,title,author,isbn,availability,location,created_at\n")
      //  write the data of the csv file
      result.foreach(book =>
        pw.write(book.id + "," + book.title + "," + book.author + "," + book.isbn + "," + book.availability + "," + book.location + "," + book.createdAt + "\n")
      )
      //  close the csv file
      pw.close()
      println("Book report generated successfully")
    }
  }

  //  generate transaction report
  def generateTransactionReport(): Unit = {
    //  get all transactions
    val queryDescription = SlickTables.transactionTable
    val futuredResult = Connection.db.run(queryDescription.result)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    //  if no transactions found
    if (result.isEmpty) println("No transactions found")
    else {
      //  create a csv file
      val pw = new java.io.PrintWriter(new java.io.File("reports/transaction_report.csv"))
      //  write the header of the csv file
      pw.write("id,user_national_id,book_id,checkout_date,due_date,return_date,fine_amount\n")
      //  write the data of the csv file
      result.foreach(transaction =>
        pw.write(
          transaction.id + "," + transaction.userNationalId + "," + transaction.bookId + "," + transaction.checkoutDate + "," + transaction.dueDate + "," + transaction.returnDate + "," + transaction.fineAmount + "\n"
        )
      )
      //  close the csv file
      pw.close()
      println("Transaction report generated successfully")
    }

  }
}
