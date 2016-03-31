package models

class Rating(inScore: Float, inComment: Option[String] = None) {
  var score: Float = inScore
  var comment: Option[String] = inComment

  /**
   * Give the number of stars, rounded to half a star
   */
  def nStars: Float = Math.round(score * 10) / 2f
}
