import scala.concurrent.{Future, Await}
import java.sql.Timestamp

object userService {

  import slick.jdbc.PostgresProfile.api._

  def addUser(
      nationalId: String,
      name: String
  ): Unit = {
    val user =
      User(nationalId, name, new Timestamp(System.currentTimeMillis()))
    // create a query description to insert a user and return the id
    val queryDescription =
      SlickTables.userTable returning SlickTables.userTable.map { _.nationalId } into ((user, national_id) => user.copy(nationalId = national_id)) += user
    // create a future object to store the result of the query
    val futuredResult = Connection.db.run(queryDescription)

    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf // blocking call to get the result of future object
    )
    if (result.nationalId == "") println("Failed to add user")
    else println("User added successfully, user => " + result)

  }

  def removeUser(nationalId: String): Unit = {
    val queryDescription = SlickTables.userTable.filter(_.nationalId === nationalId).delete
    val futuredResult = Connection.db.run(queryDescription)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result == 0) println("No user found")
    else println("User removed successfully")
  }

  def updateUser(nationalId: String, name: String): Unit = {

    // create a query description to update a user and return the  user
    val queryDescription = SlickTables.userTable
      .filter(_.nationalId === nationalId)
      .map(user => (user.nationalId, user.name))
      .update((nationalId, name))

    // create a future object to store the result of the query
    val futuredResult = Connection.db.run(queryDescription)

    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf // blocking call to get the result of future object
    )

    if (result == 0) println("No user found")
    else println("User updated successfully")
  }

  def getAllUsers(): Unit = {
    val queryDescription = SlickTables.userTable.result

    val futuredUsers: Future[Seq[User]] = Connection.db.run(queryDescription)
    val users = Await.result(
      futuredUsers,
      scala.concurrent.duration.Duration.Inf
    ) //  blocking call to get the result of future object
    if (users.isEmpty) println("No users found")
    else
      users.foreach(println(_))

  }

  def getUserByNationalId(nationalId: String): Unit = {
    val queryDescription = SlickTables.userTable.filter(_.nationalId === nationalId)
    val futuredUser: Future[Seq[User]] = Connection.db.run(queryDescription.result)
    val user = Await.result(
      futuredUser,
      scala.concurrent.duration.Duration.Inf
    ) //  blocking call to get the result of future object
    if (user.isEmpty) println("No user found")
    else
      user.foreach(println(_))
  }
}
