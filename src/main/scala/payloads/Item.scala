package payloads

import spray.json.DefaultJsonProtocol

object ItemPayloads {

  case class UpdateItemPayload(sku: String, qty: Long)

  trait JsonMarshallers extends DefaultJsonProtocol {
    implicit val itemFormats = jsonFormat2(UpdateItemPayload.apply)
  }

}