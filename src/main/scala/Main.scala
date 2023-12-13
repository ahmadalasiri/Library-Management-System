import scala.io.StdIn.readLine
import scala.util.control.Breaks
import org.slf4j.LoggerFactory
import java.sql.Timestamp
import ch.qos.logback.classic.{Level, LoggerContext}

object Main {

  val loggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
  // loggerContext.getLogger("org.slf4j.impl.StaticLoggerBinder").setLevel(Level.INFO)
  loggerContext.stop()

  def main(args: Array[String]): Unit = {
    var continue = true
    println("Welcome to Library Management System!")

    while (continue) {
      println(
        """
Please select an option from the following menu:
  -----------------------------------------------------------------
  | 1.  Manage Users              |   2.  Manage Books             |
  | 3.  Manage Transactions       |   4.  Report Generation        |
  | 5. Exit                      |                                |
  -----------------------------------------------------------------
"""
      )

      try {
        var choice = readLine(
          "Enter number corresponding to your choice: "
        ).toInt

        choice match {
          // 1. Register New User
          case 1 =>
            var manageUsers = true
            while (manageUsers) {
              println(
                """
Please select an option from the following menu:
  -----------------------------------------------------------------
  | 1.  Add New User           |   2.  Remove User                |
  | 3.  Update User            |   4.  View All Users             |
  | 5.  View User by Id        |   6. Back to Main Menu           |
  -----------------------------------------------------------------
"""
              )
              try {
                var choice = readLine(
                  "Enter number corresponding to your choice: "
                ).toInt

                choice match {
                  // 1. Add New User
                  case 1 =>
                    var nationalId = readLine("Enter national id of user: ")
                    var name = readLine("Enter name of user: ")
                    userService.addUser(nationalId, name)

                  // // 2. Remove User
                  case 2 =>
                    var nationalId = readLine(
                      "Enter national id of user to be removed: "
                    )
                    userService.removeUser(nationalId)

                  // // 3. Update User
                  case 3 =>
                    var nationalId = readLine(
                      "Enter national id of user to be updated: "
                    )
                    var name = readLine("Enter name of user: ")
                    userService.updateUser(nationalId, name)

                  // // 4. View All Users
                  case 4 =>
                    userService.getAllUsers()

                  // // 5. View User by Id
                  case 5 =>
                    var nationalId = readLine(
                      "Enter national id of user to be viewed: "
                    )
                    userService.getUserByNationalId(nationalId)

                  // // 6. Back to Main Menu
                  case 6 =>
                    manageUsers = false

                  case _ =>
                    println("Invalid choice. Please try again.")
                }
              } catch {
                case a: NumberFormatException =>
                  println(s"NumberFormatException occurred. Try again!")
              }
            }

          // 2. Manage Books
          case 2 =>
            var manageBooks = true
            while (manageBooks) {
              println("""
Please select an option from the following menu:
  -----------------------------------------------------------------
  | 1.  Add New Book           |   2.  Remove Book                | 
  | 3.  Update Book            |   4.  View All Books             |
  | 5.  View Book by Id        |   6. Back to Main Menu           |
  -----------------------------------------------------------------
                """)
              try {
                var choice = readLine(
                  "Enter number corresponding to your choice: "
                ).toInt

                choice match {
                  // 1. Add New Book
                  case 1 =>
                    var title = readLine("Enter title of book: ")
                    var author = readLine("Enter author of book: ")
                    var isbn = readLine("Enter isbn of book: ")
                    var availability = readLine("Enter availability of book: ").toBoolean
                    var location = readLine("Enter location of book: ")
                    bookService.addBook(title, author, isbn, availability, location)

                  // 2. Remove Book
                  case 2 =>
                    var bookId = readLine(
                      "Enter Id of book to be removed: "
                    ).toInt
                    bookService.removeBook(id = bookId)

                  // 3. Update Book
                  case 3 =>
                    var bookId = readLine(
                      "Enter Id of book to be updated: "
                    ).toInt
                    var title = readLine("Enter title of book: ")
                    var author = readLine("Enter author of book: ")
                    var isbn = readLine("Enter isbn of book: ")
                    var availability = readLine("Enter availability of book: ").toBoolean
                    var location = readLine("Enter location of book: ")
                    bookService.updateBook(bookId, title, author, isbn, availability, location)

                  // 4. View All Books
                  case 4 =>
                    bookService.getAllBooks()

                  // 5. View Book by Id
                  case 5 =>
                    var bookId = readLine(
                      "Enter Id of book to be viewed: "
                    ).toInt
                    bookService.getBookById(id = bookId)

                  // 6. Back to Main Menu
                  case 6 =>
                    manageBooks = false

                  case _ =>
                    println("Invalid choice. Please try again.")
                }
              } catch {
                case a: NumberFormatException =>
                  println(s"NumberFormatException occurred. Try again!")
              }
            }

          // 3. Manage Transactions
          case 3 =>
            var manageTransactions = true
            while (manageTransactions) {
              println("""
Please select an option from the following menu:
  -------------------------------------------------------------------------------
  | 1.  Add New Transaction          |   2.  Remove Transaction                 |
  | 3.  Update Transaction           |   4.  View All Transactions              |
  | 5.  View Transaction by Id       |   6.  View Transactions by national id   |
  | 7.  View Transactions by Book Id |   8.  Back to Main Menu                  |
  -------------------------------------------------------------------------------
                """)
              try {
                var choice = readLine(
                  "Enter number corresponding to your choice: "
                ).toInt

                choice match {
                  // 1. Add New Transaction
                  case 1 =>
                    var userNationalId = readLine("Enter national id of user: ")
                    var bookId = readLine("Enter Id of book: ").toInt
                    var checkoutDate = readLine("Enter checkout date of book: ")
                    var dueDate = readLine("Enter due date of book: ")
                    var returnDate = readLine("Enter return date of book: ")
                    var fineAmount = readLine("Enter fine amount of book: ").toDouble

                    val checkoutTimestamp = Timestamp.valueOf(checkoutDate)
                    val dueTimestamp = Timestamp.valueOf(dueDate)
                    val returnTimestamp = Timestamp.valueOf(returnDate)

                    transactionService.addTransaction(userNationalId, bookId, checkoutTimestamp, dueTimestamp)

                  // 2. Remove Transaction
                  case 2 =>
                    var transactionId = readLine(
                      "Enter Id of transaction to be removed: "
                    ).toInt
                    transactionService.removeTransaction(id = transactionId)

                  // 3. Update Transaction
                  case 3 =>
                    var transactionId = readLine(
                      "Enter Id of transaction to be updated: "
                    ).toInt
                    var userNationalId = readLine("Enter national id of user: ")
                    var bookId = readLine("Enter Id of book: ").toInt
                    var checkoutDate = readLine("Enter checkout date of book: ")
                    var dueDate = readLine("Enter due date of book: ")
                    var returnDate = readLine("Enter return date of book: ")
                    var fineAmount = readLine("Enter fine amount of book: ").toDouble

                    val checkoutTimestamp = Timestamp.valueOf(checkoutDate)
                    val dueTimestamp = Timestamp.valueOf(dueDate)
                    val returnTimestamp = Timestamp.valueOf(returnDate)

                    transactionService.updateTransaction(transactionId, userNationalId, bookId, checkoutTimestamp, dueTimestamp, returnTimestamp, fineAmount)

                  // 4. View All Transactions
                  case 4 =>
                    transactionService.getAllTransactions()

                  // 5. View Transaction by Id
                  case 5 =>
                    var transactionId = readLine(
                      "Enter Id of transaction to be viewed: "
                    ).toInt
                    transactionService.getTransactionById(id = transactionId)

                  // 6. View Transactions by national id
                  case 6 =>
                    var userNationalId = readLine(
                      "Enter national id of user to be viewed: "
                    )
                    transactionService.getTransactionByUserNationalId(userNationalId)

                  // 7. View Transactions by Book Id
                  case 7 =>
                    var bookId = readLine(
                      "Enter Id of book to be viewed: "
                    ).toInt
                    transactionService.getTransactionByBookId(bookId)

                  // 8. Back to Main Menu
                  case 8 =>
                    manageTransactions = false

                  case _ =>
                    println("Invalid choice. Please try again.")
                }
              } catch {
                case a: NumberFormatException =>
                  println(s"NumberFormatException occurred. Try again!")
              }
            }

          // 4. Report Generation
          case 4 =>
            var reportGeneration = true
            while (reportGeneration) {
              println("""
Please select an option from the following menu:
  -------------------------------------------------------------
  | 1.  Generate User Report          |   2.  Generate Book Report   |
  | 3.  Generate Transaction Report   |   4.  Back to Main Menu      |
  -------------------------------------------------------------
                """)
              try {
                var choice = readLine(
                  "Enter number corresponding to your choice: "
                ).toInt

                choice match {
                  // 1. Generate User Report
                  case 1 =>
                    reportService.generateUserReport()

                  // 2. Generate Book Report
                  case 2 =>
                    reportService.generateBookReport()

                  // 3. Generate Transaction Report
                  case 3 =>
                    reportService.generateTransactionReport()

                  // 4. Back to Main Menu
                  case 4 =>
                    reportGeneration = false

                  case _ =>
                    println("Invalid choice. Please try again.")
                }
              } catch {
                case a: NumberFormatException =>
                  println(s"NumberFormatException occurred. Try again!")
              }
            }

          // 5. Exit
          case 5 =>
            println("Exiting...")
            continue = false

          case _ =>
            println("Invalid choice. Please try again.")
        }
      } catch {
        case a: NumberFormatException =>
          println(s"NumberFormatException occurred. Try again!")
      }
    }
  }
}

// Please select an option from the following menu:
//  -----------------------------------------------------------------
// | 1.  Managing users              |   2.  Managing books          |
// | 3.  Managing transactions       |   4.  Managing fines          |
// | 3.  Report Generation           |   4.  Manage Library Resources|
// | 5.  Handle Book Checkout        |   6.  Manage Users Accounts    |
// | 7.  Create Reports              |   8.  Monitor Library Usage   |
// | 9.  View Available Books        |   10. View Borrowed Books     |
// | 11. Search for Books            |   5. Exit                    |
//  -----------------------------------------------------------------
// """
// """
