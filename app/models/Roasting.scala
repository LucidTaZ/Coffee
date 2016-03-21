package models

import java.time.Duration

class Roasting(inFlavor: Flavor, inDuration: Duration, inComment: Option[String] = None) {
  var flavor: Flavor = inFlavor
  var duration: Duration = inDuration
  var comment: Option[String] = inComment

  def formatDuration: String = duration.toString()
}
