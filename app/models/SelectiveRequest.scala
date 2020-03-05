package models

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class SelectiveRequest(pageUrl: String, inboundLinks: Int)

object SelectiveRequest {
  implicit val fontCase = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[SelectiveRequest]
}
