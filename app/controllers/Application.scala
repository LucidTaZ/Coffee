package controllers

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration

import javax.inject.Inject
import models.Flavor
import models.Flavors
import models.Ratings
import models.Roasting
import models.Roastings
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.Action
import play.api.mvc.Controller
import slick.backend.DatabaseConfig
import slick.driver.H2Driver.api.Database
import slick.driver.H2Driver.api.streamableQueryActionExtensionMethods
import slick.driver.JdbcProfile

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  def index = Action {
    Ok(views.html.Application.index())
  }

  def debug = Action {
    val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
    val database: Database = dbConfig.db

    val roastingsQuery = Roastings.roastings
    val roastingsAction = roastingsQuery.result
    val roastingsFutureResults: Future[Seq[Roastings#TableElementType]] = database.run(roastingsAction)
    val roastings: Seq[Roasting] = Await.result(roastingsFutureResults, Duration.Inf)

    val flavorQuery = Roastings.roastings.flatMap(_.flavor)
    val flavorAction = flavorQuery.result
    val flavorFutureResult = database.run(flavorAction)
    val flavors: Seq[Flavor] = Await.result(flavorFutureResult, Duration.Inf)

    Ok(views.html.Application.debug(flavors, roastings))
  }
}
