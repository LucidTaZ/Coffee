package controllers

import models._
import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def sampleRating = Action {
    val flavor = Flavor("Yellow Bourbon")
    val rating = Rating(flavor, 0.52f/*, Some("Blap")*/)
    Ok(views.html.rating(rating))
  }

  def listRatings = TODO
}
