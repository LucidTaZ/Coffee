package models

import java.time.Duration
import slick.driver.H2Driver.api._

case class Roasting(
  id: Option[Long] = None,
  flavor_id: Long,
  duration: Duration,
  comment: Option[String]
) {
  def formatDuration: String = duration.toString()
  val flavorQuery = Flavors.queryById(flavor_id)
}

class Roastings(tag: Tag) extends Table[Roasting](tag, "ROASTING") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def flavor_id = column[Long]("FLAVOR_ID")
  def duration_seconds = column[Int]("DURATION_SECONDS")
  def comment = column[String]("COMMENT")

  def duration = (duration_seconds) <>[Duration, Int] (Duration.ofSeconds(_), d => Some(d.getSeconds().toInt))
  def * = (id.?, flavor_id, duration, comment.?) <> (Roasting.tupled, Roasting.unapply)

  def flavor = foreignKey("FLAVOR", flavor_id, Flavors.flavors)(_.id)
}

object Roastings {
  val roastings = TableQuery[Roastings]
  def queryByFlavor(flavor: Flavor) = Roastings.roastings.filter { _.flavor_id === flavor.id }
}
