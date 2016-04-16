package models

import slick.driver.H2Driver.api._

case class Flavor(
  id: Option[Long] = None,
  name: String
)

// Example: https://github.com/typesafehub/activator-hello-slick/blob/slick-3.1/src/main/scala/Tables.scala
class Flavors(tag: Tag) extends Table[Flavor](tag, "FLAVOR") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def * = (id.?, name) <> (Flavor.tupled, Flavor.unapply)
}

object Flavors {
  val flavors = TableQuery[Flavors]
}
