package controllers

import models._
import play.api._
import play.api.mvc._
import java.time.Duration
import scala.util.Random
import scala.collection.immutable.Map

class Application extends Controller {

  def index = Action {
    Ok(views.html.Application.index())
  }

  def debug = Action {
    val flavors = Flavor.list
    Ok(views.html.Application.debug(flavors))
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
    val roasting = new Roasting(flavor, Duration.ofSeconds(Random.nextInt(200) + 300), Some("Blop"))
    val rating = new Rating(Random.nextFloat(), Some("Blap"))
    return Tuple2(roasting, rating);
  }
}
