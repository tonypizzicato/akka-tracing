package test

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model._
import hello.RestService


class RestSpec extends WordSpec with Matchers with ScalatestRouteTest with RestService {
  "Items API" should {
    "post to /items" in {

      val jsonRequest = ByteString(
        s"""
           |{
           |    "sku":"test",
           |    "qty":12
           |}
        """.stripMargin)

      Post("/items", HttpEntity(MediaTypes.`application/json`, jsonRequest)) ~> routes ~> check {
        status.isSuccess() shouldEqual true
      }
    }
  }
}