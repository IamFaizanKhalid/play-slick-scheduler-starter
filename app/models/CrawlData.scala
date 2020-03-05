package models

import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class CrawlData (timeOfCrawl: String,
					  pageUrl: String,
					  inboundLinks: Int,
					  outboundLinks: Int,
					  content: String)

object CrawlData {
  implicit val codeFormat = JsonConfiguration(SnakeCase)
  implicit val jsonFormat = Json.format[CrawlData]
}
