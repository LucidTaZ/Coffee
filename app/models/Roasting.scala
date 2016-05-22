package models

import java.time.Duration
import slick.driver.H2Driver.api._

case class Roasting(
  id: Option[Long] = None,
  duration: Duration,
  comment: Option[String]
) {
  val formatDuration: String = duration.toString
}

class Roastings(tag: Tag) extends Table[Roasting](tag, "ROASTING") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def flavor_id = column[Long]("FLAVOR_ID")
  def duration_seconds = column[Int]("DURATION_SECONDS")
  def comment = column[String]("COMMENT")

  def duration = (duration_seconds) <>[Duration, Int] (Duration.ofSeconds(_), d => Some(d.getSeconds.toInt))
  def * = (id.?, duration, comment.?) <> (Roasting.tupled, Roasting.unapply)

  def flavor = foreignKey("FLAVOR", flavor_id, Flavors.flavors)(_.id)
}

object Roastings {
  val roastings = TableQuery[Roastings]
  def queryById(id: Long) = roastings.filter { _.id === id }
  def queryByFlavor(flavor: Flavor) = Roastings.roastings.filter { _.flavor_id === flavor.id }
}
