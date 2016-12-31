package hello

import scala.concurrent.Future
import scala.util.Random
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.{FromRequestUnmarshaller, Unmarshaller}
import akka.stream.ActorMaterializer
import com.github.levkhomich.akka.tracing._
import com.github.levkhomich.akka.tracing.http.BaseTracingDirectives
import payloads.ItemPayloads._

final case class BasicRequest(value: String) extends TracingSupport

/**
  * Created by tonypizzicato on 31/12/2016.
  */
trait RestService extends BaseTracingDirectives with JsonMarshallers {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val serviceName = "tracedTest"

  override protected def trace: TracingExtensionImpl = TracingExtension(system)

  val orderGetOrPutMethod = path("order" / IntNumber) & (get | put) & extract(_.request.method)

  def sleep(duration: Long) {
    Thread.sleep(duration)
  }

  def r(v: Integer) = Random.nextInt(v)

  val f = Future {
    sleep(r(999))
    Some(UpdateItemPayload("SKUZZ", r(20)))
  }

  def fetchItem(itemId: Long): Future[Option[UpdateItemPayload]] = f

  val routes = tracedHandleWith(serviceName) { tr: BasicRequest =>
    HttpResponse(StatusCodes.OK)
  }

  implicit def um: FromRequestUnmarshaller[BasicRequest] =
    Unmarshaller { implicit ctx =>
      request: HttpRequest =>
        import scala.concurrent.duration._
        request.entity.toStrict(FiniteDuration(100, MILLISECONDS)).map { v => BasicRequest("BB REQUEST") }
    }
}

class Service(implicit val system: ActorSystem, implicit val materializer: ActorMaterializer) extends RestService {
  def startServer(address: String, port: Int) = {
    Http().bindAndHandle(routes, address, port)
  }
}
