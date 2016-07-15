package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.data.Form
import play.api.data.Forms._
import slick.driver.JdbcProfile
import slick.driver.H2Driver.api.streamableQueryActionExtensionMethods
import models.Flavors
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import models.Flavor
import play.api.i18n.MessagesApi
import play.api.i18n.I18nSupport

object FlavorsController {
  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText
    )
    (Flavor.apply)(Flavor.unapply)
  )
}

class FlavorsController @Inject()(dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi) extends Controller with I18nSupport {
  def list = Action {
    val database = dbConfigProvider.get[JdbcProfile].db
    val flavors = Await.result(database.run(Flavors.flavors.result), Duration.Inf)
    Ok(views.html.FlavorsController.list(flavors, FlavorsController.form))
  }

  def show(id: Long) = Action {
    val database = dbConfigProvider.get[JdbcProfile].db
    val flavorQuery = Flavors.queryById(id)
    val flavorOption = Await.result(database.run(flavorQuery.result.headOption), Duration.Inf)

    flavorOption match {
      case None => NotFound("Not found")
      case Some(flavor) => {
        val roastingsQuery = flavor.roastingsQuery
        val roastings = Await.result(database.run(roastingsQuery.result), Duration.Inf)
        Ok(views.html.FlavorsController.show(flavor, roastings))
      }
    }
  }

  def create = TODO
}
