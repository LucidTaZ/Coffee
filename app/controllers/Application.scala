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

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  def index = Action {
    Ok(views.html.Application.index())
  }

  def debug = Action {
    val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
    val database: Database = dbConfig.db
    val query: TableQuery[Flavors] = Flavors.flavors
    val action = query.result
    val futureResults/*: Future[Seq[Flavors.TableElementType]]*/ = database.run(action)
    val awaitedResults: Seq[Flavor] = Await.result(futureResults, Duration.Inf)
    
    Ok(views.html.Application.debug(awaitedResults))
  }

  def allFlavors = TODO

  def sampleRoasting = Action {
    val roastingWithRatings = generateSampleRoasting
    val roasting = roastingWithRatings._1
    val ratings = roastingWithRatings._2
    Ok(views.html.Application.roasting(roasting, ratings))
  }

  def sampleFlavor = Action {
    val roastingWithRatings = generateSampleRoasting
    val roasting = roastingWithRatings._1
    val flavor = roasting.flavor
    Ok(views.html.Application.flavor(flavor, List(roasting)))
  }

  private def generateSampleRoastings: List[Tuple2[Roasting, Rating]] = {
    return List(generateSampleRoasting)
  }

  private def generateSampleRoasting: Tuple2[Roasting, Rating] = {
    val flavor = Flavor(None, "Yellow Bourbon")
    val roasting = new Roasting(flavor, java.time.Duration.ofSeconds(Random.nextInt(200) + 300), Some("Blop"))
    val rating = new Rating(Random.nextFloat(), Some("Blap"))
    return Tuple2(roasting, rating);
  }
}
