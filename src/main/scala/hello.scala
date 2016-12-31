package hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object Hello extends App {
  implicit val system = ActorSystem("rest-server", ConfigFactory.load())
  implicit val materializer = ActorMaterializer()

  val server: Service = new Service()

  server.startServer("localhost", 8080)
}
