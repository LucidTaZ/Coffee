import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models.Flavor
import models.Flavor
import java.time.Duration
import models.Roasting

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beSome.which (status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Flavors")
    }

    "link to the flavors page" in new WithApplication {
      // Separate view file testing
      val html = views.html.Application.index()
      contentAsString(html) must contain ("/flavors")
    }

    "render the flavors page" in new WithApplication{
      val flavorsPage = route(FakeRequest(GET, "/flavors")).get

      status(flavorsPage) must equalTo(OK)
      contentType(flavorsPage) must beSome.which(_ == "text/html")
      contentAsString(flavorsPage) must contain ("Flavors")
    }
  }

  "Flavors" should {
    "link to flavor views" in new WithApplication {
      val flavors = Seq(
        Flavor(Some(1), "Sample flavor")
      )
      val html = views.html.FlavorsController.list(flavors)

      contentAsString(html) must contain ("/flavors/1")
    }
    
    "link to roasting views" in new WithApplication {
      val flavor = Flavor(None, "Sample flavor")
      val roastings = Seq(
        Roasting(Some(1), Duration.ZERO, None)
      )
      val html = views.html.FlavorsController.show(flavor, roastings)

      contentAsString(html) must contain ("/roastings/1")
    }
  }
}
