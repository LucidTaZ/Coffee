package models

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.driver.H2Driver.api._
import java.util.Properties
import slick.lifted.Rep

case class Flavor(
  id: Option[Long] = None,
  name: String
)

object Flavor {
  // Example: https://github.com/typesafehub/activator-hello-slick/blob/slick-3.1/src/main/scala/Tables.scala
  class FlavorTable(tag: Tag) extends Table[Flavor](tag, "FLAVOR") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def * = (id.?, name) <> ((Flavor.apply _).tupled, Flavor.unapply) // https://github.com/VirtusLab/unicorn/issues/11 and http://queirozf.com/entries/slick-error-message-value-tupled-is-not-a-member-of-object
  }
  val flavors = TableQuery[FlavorTable]
  
  def list: Seq[Flavor] = {
    val db = Database.forConfig("mycoffee")

    var result = Seq[Flavor]()
    Await.ready(db.run(flavors.result.map{rows => result = rows}), Duration.Inf)
    result
  }
}
