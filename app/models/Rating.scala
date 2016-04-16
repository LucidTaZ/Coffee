package models

import slick.driver.H2Driver.api._

case class Rating(
  id: Option[Long] = None,
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
  def score = column[Float]("score")
  def comment = column[String]("comment")
  def * = (id.?, score, comment.?) <> (Rating.tupled, Rating.unapply) 
}

object Ratings {
  def ratings = TableQuery[Ratings]
}
