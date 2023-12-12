import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {

    var continue = true
    println("Welcome to Library Management System!")
    while (continue) {
      println(
        """
Please select an option from the following menu:
 -----------------------------------------------------------------
| 1.  Register New User           |   2.  Check In                |
| 3.  Report Generation           |   4.  Manage Library Resources|
| 5.  Handle Book Checkout        |   6.  Manage User Accounts    |
| 7.  Create Reports              |   8.  Monitor Library Usage   |
| 9.  View Available Books        |   10. View Borrowed Books      |
| 11. Search for Books            |   12. Exit                     |
 -----------------------------------------------------------------
"""
      )
      try {
        var choice = readLine(
          "Enter number corresponding to your choice: "
        ).toInt

        choice match {
          // 1.  Register New User
          case 1 =>
            var name = readLine("Enter name of customer: ")
            var email = readLine("Enter email of customer: ")
            var password = readLine("Enter password of customer: ")
            libraryService.addUser(name, email, password)

          // // 2.  Check In
          // case 2 =>
          //   var customerId = readLine(
          //     "Enter Id of customer to be removed: "
          //   ).toInt
          //   libraryService.removeCustomer(id = customerId)
          // // 3.  Report Generation
          // case 3 =>
          //   var name = readLine("Enter name of student: ")
          //   var rollNo = readLine("Enter Roll.No of student: ")
          //   var batch = readLine("Enter batch of student: ")
          //   libraryService.addStudent(name, rollNo, batch)
          // // 4.  Manage Library Resources
          // case 4 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 5.  Handle Book Checkout
          // case 5 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 6.  Manage User Accounts
          // case 6 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 7.  Create Reports
          // case 7 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 8.  Monitor Library Usage
          // case 8 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 9.  View Available Books
          // case 9 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 10. View Borrowed Books
          // case 10 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 11. Search for Books
          // case 11 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // // 12. Exit
          // case 12 =>
          //   var studentId = readLine(
          //     "Enter Roll.No of student to be removed : "
          //   )
          // case _ =>
          //   println("Invalid choice. Please try again.")

        }
      } catch {
        case a: NumberFormatException =>
          println(s"NumberFormatException occured. Try again!")
      }
    }
  }
}
