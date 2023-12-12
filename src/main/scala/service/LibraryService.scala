import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
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
      User(0, name, email, password, new Timestamp(System.currentTimeMillis()))

    val queryDescription = SlickTables.userTable += user

    val futuredId: Future[Int] = Connection.db.run(queryDescription)
    futuredId.onComplete {
      case Success(id) => println(s"Inserted user with id: $id")
      case Failure(e)  => println(s"Failed to insert user: $e")
    }
    Thread.sleep(10000)

  }

  // Other methods for managing library resources, handling book checkout, etc.
}
