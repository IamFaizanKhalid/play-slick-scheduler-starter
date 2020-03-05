package controllers

import java.io.{File, FileInputStream}

import javax.inject._
import models.SelectiveRequest
import play.api.libs.json._
import play.api.mvc._

@Singleton
class DataController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  def recursiveListFiles(f: File): Seq[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles _)
  }

  def all(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val res = recursiveListFiles(new java.io.File("./output/")).flatMap { file =>
    file.getName match {
      case json if json.endsWith(".json") =>
        val stream = new FileInputStream(file)
        val json =
          try {
            Json.parse(stream)
          } finally {
            stream.close()
          }
        Some(json)
      case _ => None
      }
    }

    Ok(JsArray(res))
  }

  def selective(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    request.body.asJson
      .map { json =>
        json
          .asOpt[SelectiveRequest]
          .map { r =>
            val res = recursiveListFiles(new java.io.File("./output/")).flatMap { file =>
              file.getName match {
                case json if json.endsWith(".json") =>
                  val stream = new FileInputStream(file)
                  val json =
                    try {
                      Json.parse(stream)
                    } finally {
                      stream.close()
                    }

                  if ((json \ "page_url").as[String] == r.pageUrl && (json \ "inbound_links")
                        .as[Int] == r.inboundLinks)
                    Some(json)
                  else
                    None
                case _ => None
              }
            }

            Ok(JsArray(res))
          }
          .getOrElse(BadRequest("Some key in the JSON object was invalid."))
      }
      .getOrElse(BadRequest("The request was not valid."))
  }
}
