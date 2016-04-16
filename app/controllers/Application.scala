package controllers

import models._
import play.api._
import play.api.mvc._
import scala.util.Random
import scala.collection.immutable.Map
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {

  def index = Action {
    Ok(views.html.Application.index())
  }

  def debug = Action {
    val query: TableQuery[Flavors] = Flavors.flavors
    val action = query.result
    val futureResults/*: Future[Seq[Flavors.TableElementType]]*/ = db.run(action)
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

  private def db = Database.forConfig("mycoffee")
}
