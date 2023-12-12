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
object libraryService {

  import slick.jdbc.PostgresProfile.api._
  import PrivateExecutionContext._

  def addUser(
      name: String,
      email: String,
      password: String
  ): Unit = {
    val user =
      User(12, name, email, password, new Timestamp(System.currentTimeMillis()))

    val queryDescription = SlickTables.userTable += user

    val futuredId: Future[Int] = Connection.db.run(queryDescription)
    futuredId.onComplete {
      case Success(id) => println(s"Inserted user with id: $id")
      case Failure(e)  => println(s"Failed to insert user: $e")
    }
    Thread.sleep(10000)

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

  // Other methods for managing library resources, handling book checkout, etc.
}
