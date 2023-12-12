import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future, Await}
import scala.util.{Failure, Success, Try}
import java.sql.Timestamp

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext =
    ExecutionContext.fromExecutorService(executor)
}
object userService {

  import slick.jdbc.PostgresProfile.api._
  import PrivateExecutionContext._

  def addUser(
      name: String,
      email: String,
      password: String
  ): Unit = {
    val user =
      User(0, name, email, password, new Timestamp(System.currentTimeMillis()))
    // create a query description to insert a user and return the id
    val queryDescription =
      SlickTables.userTable returning SlickTables.userTable.map { _.id } into ((user, id) => user.copy(id = id)) += user

    // create a future object to store the result of the query
    val futuredResult = Connection.db.run(queryDescription)

    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf // blocking call to get the result of future object
    )
    if (result.id == 0) println("Failed to add user")
    else println("User added successfully, user => " + result)

  }

  def removeUser(id: Long): Unit = {
    val queryDescription = SlickTables.userTable.filter(_.id === id).delete
    val futuredResult = Connection.db.run(queryDescription)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf
    )
    if (result == 0) println("No user found")
    else println("User removed successfully")
  }

  def updateUser(id: Long, name: String, email: String, password: String): Unit = {

    // create a query description to update a user and return the  user
    val queryDescription = SlickTables.userTable
      .filter(_.id === id)
      .map(user => (user.name, user.email, user.password))
      .update((name, email, password))

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

  def getUserById(id: Long): Unit = {
    val queryDescription = SlickTables.userTable.filter(_.id === id).result
    val futuredUser: Future[Seq[User]] = Connection.db.run(queryDescription)
    val user = Await.result(
      futuredUser,
      scala.concurrent.duration.Duration.Inf
    ) //  blocking call to get the result of future object
    if (user.isEmpty) println("No user found")
    else
      user.foreach(println(_))
  }
}
