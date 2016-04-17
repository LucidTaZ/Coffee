package models

import slick.driver.H2Driver.api._

case class Rating(
  id: Option[Long] = None,
  roasting_id: Long,
  score: Float,
  comment: Option[String] = None
) {
  /**
   * Give the number of stars, rounded to half a star
   */
  def nStars: Float = Math.round(score * 10) / 2f
}

class Ratings(tag: Tag) extends Table[Rating](tag, "RATING") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def roasting_id = column[Long]("ROASTING_ID")
  def score = column[Float]("SCORE")
  def comment = column[String]("COMMENT")
  def * = (id.?, roasting_id, score, comment.?) <> (Rating.tupled, Rating.unapply) 
}

object Ratings {
  val ratings = TableQuery[Ratings]
  def queryByRoasting(roasting: Roasting) = ratings.filter { _.roasting_id === roasting.id }
}
