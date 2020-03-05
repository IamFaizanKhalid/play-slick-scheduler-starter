package controllers

import java.io.FileInputStream

import javax.inject._
import play.api.libs.json._

import play.api.mvc._

import scala.collection.JavaConverters._


@Singleton
class DataController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  import java.io.File
  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  def all() = Action { implicit request: Request[AnyContent] =>
    {
      var result: JsArray = Json.arr()
      for (file <- recursiveListFiles(new java.io.File("./output/"))
           if file.getName.endsWith(".json")) {
        val stream = new FileInputStream(file)
        val json =
          try {
            Json.parse(stream)
          } finally {
            stream.close()
          }
        result = result.append(json)
      }
      Ok(result)
    }
  }

  def selective() = Action { implicit request: Request[AnyContent] =>
    {
      val json = request.body.asJson
      val page_url = json.get("page_url")
      val inbound_links = json.get("inbound_links")
      var result: JsArray = Json.arr()
      for (file <- recursiveListFiles(new java.io.File("./output/"))
           if file.getName.endsWith(".json")) {
        val stream = new FileInputStream(file)
        val json =
          try {
            Json.parse(stream)
          } finally {
            stream.close()
          }
        if ((json \ "page_url").get == page_url && (json \ "inbound_links").get == inbound_links)
          result = result.append(json)
      }
      Ok(result)
    }
  }
}
