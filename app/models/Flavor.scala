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
  class FlavorTable(tag: Tag) extends Table[(Long, String)](tag, "FLAVOR") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def * = (id, name)
  }
  val flavors = TableQuery[FlavorTable]
  
  def listPlain: Seq[(Long, String)] = {
    val db = Database.forConfig("mycoffee")

    var result = Seq[(Long, String)]()
    Await.ready(db.run(flavors.result.map{rows => result = rows}), Duration.Inf)
    result
  }
  
//  def list: Seq[Flavor] = {
//    val db = Database.forConfig("mycoffee")

//    var result = Seq[Flavor]()
//    Await.ready(db.run(flavors.result.map{rows => result = rows}), Duration.Inf)
//    result
//  }
}
