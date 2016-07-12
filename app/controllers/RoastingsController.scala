package controllers

import play.api.mvc.Action
import slick.driver.JdbcProfile
import slick.driver.H2Driver.api.streamableQueryActionExtensionMethods
import models.Roastings
import models.Ratings
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.Controller
import javax.inject.Inject

class RoastingsController @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {
  def show(id: Long) = Action {
    val database = dbConfigProvider.get[JdbcProfile].db
    val roastingQuery = Roastings.queryById(id)
    val roastingOption = Await.result(database.run(roastingQuery.result.headOption), Duration.Inf)

    roastingOption match {
      case None => NotFound("Not found")
      case Some(roasting) => {
        val flavorQuery = roastingQuery.flatMap { _.flavor }
        val flavor = Await.result(database.run(flavorQuery.result.head), Duration.Inf)
        val ratingQuery = Ratings.queryByRoasting(roasting)
        val rating = Await.result(database.run(ratingQuery.result.headOption), Duration.Inf)
        Ok(views.html.RoastingsController.show(flavor, roasting, rating))
      }
    }
  }
}
