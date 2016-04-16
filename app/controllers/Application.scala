package controllers

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random
import javax.inject.Inject
import models.Flavor
import models.Flavors
import models.Rating
import models.Roasting
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.Action
import play.api.mvc.Controller
import slick.driver.H2Driver.api.Database
import slick.driver.H2Driver.api.TableQuery
import slick.driver.H2Driver.api.streamableQueryActionExtensionMethods
import slick.driver.JdbcProfile
import slick.backend.DatabaseConfig
import models.Roastings

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  def index = Action {
    Ok(views.html.Application.index())
  }

  def debug = Action {
    val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
    val database: Database = dbConfig.db

    val roastingsQuery = Roastings.roastings
    val roastingsAction = roastingsQuery.result
    val roastingsFutureResults/*: Future[Seq[Flavors.TableElementType]]*/ = database.run(roastingsAction)
    val roastings: Seq[Roasting] = Await.result(roastingsFutureResults, Duration.Inf)

    val flavorQuery = Roastings.roastings.flatMap(_.flavor)
    val flavorAction = flavorQuery.result
    val flavorFutureResult = database.run(flavorAction)
    val flavors: Seq[Flavor] = Await.result(flavorFutureResult, Duration.Inf)

    Ok(views.html.Application.debug(flavors, roastings))
  }

  def allFlavors = TODO

  def sampleRoasting = Action {
    val roastingWithRatings = generateSampleRoasting
    val flavor = roastingWithRatings._1
    val roasting = roastingWithRatings._2
    val ratings = roastingWithRatings._3
    Ok(views.html.Application.roasting(flavor, roasting, ratings))
  }

  def sampleFlavor = Action {
    val roastingWithRatings = generateSampleRoasting
    val flavor = roastingWithRatings._1
    val roasting = roastingWithRatings._2
    Ok(views.html.Application.flavor(flavor, List(roasting)))
  }

  private def generateSampleRoastings: List[Tuple3[Flavor, Roasting, Rating]] = {
    return List(generateSampleRoasting)
  }

  private def generateSampleRoasting: Tuple3[Flavor, Roasting, Rating] = {
    val flavor = Flavor(None, "Yellow Bourbon")
    val roasting = Roasting(None, 0, java.time.Duration.ofSeconds(Random.nextInt(200) + 300), Some("Blop"))
    val rating = Rating(None, Random.nextFloat(), Some("Blap"))
    return Tuple3(flavor, roasting, rating);
  }
}
