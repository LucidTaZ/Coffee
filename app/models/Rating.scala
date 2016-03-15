package models

case class Rating(flavor: Flavor, score: Float, comment: Option[String] = None) {
  /**
   * Give the number of stars, rounded to half a star
   */
  def nStars: Float = Math.round(score * 10) / 2f;
}
