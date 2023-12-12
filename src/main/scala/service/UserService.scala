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
      SlickTables.userTable returning SlickTables.userTable.map { _.id } into (
        (user, id) => user.copy(id = id)
      ) += user
    // create a future object to store the result of the query
    val futuredResult = Connection.db.run(queryDescription)
    val result = Await.result(
      futuredResult,
      scala.concurrent.duration.Duration.Inf // blocking call to get the result of future object
    )
    println(s"User added with result: $result")

  }

  def getAllUsers(): Unit = {
    val queryDescription = SlickTables.userTable.result

    val futuredUsers: Future[Seq[User]] = Connection.db.run(queryDescription)
    // futuredUsers.onComplete {
    //   case Success(users) => users.foreach(println(_))
    //   case Failure(e)     => println(s"Failed to get users: $e")
    // }
    // Thread.sleep(10000)
    val users = Await.result(
      futuredUsers,
      scala.concurrent.duration.Duration.Inf
    ) //  blocking call to get the result of future object
    if (users.isEmpty) println("No users found")
    else
      users.foreach(println(_))

  }

  // Other methods for managing user resources, handling book checkout, etc.
}
