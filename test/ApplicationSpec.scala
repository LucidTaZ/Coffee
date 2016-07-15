import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models.Flavor
import models.Flavor
import java.time.Duration
import models.Roasting
import play.api.i18n.Messages
import play.api.i18n.Lang
import controllers.FlavorsController
import models.Roasting
import models.Rating

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
  }

  "Flavors" should {
    "render the flavors page" in new WithApplication{
      val flavorsPage = route(FakeRequest(GET, "/flavors")).get

      status(flavorsPage) must equalTo(OK)
      contentType(flavorsPage) must beSome.which(_ == "text/html")
      contentAsString(flavorsPage) must contain ("Flavors")
    }

    "link to flavor views" in new WithApplication {
      val flavors = Seq(
        Flavor(Some(1), "Sample flavor")
      )
      val messages = Messages.Implicits.applicationMessages(Lang.defaultLang, app)
      val html = views.html.FlavorsController.list(flavors, FlavorsController.form)(messages)

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

  "Roastings" should {
    "show a roasting" in new WithApplication {
      val flavor = Flavor(None, "Sample flavor")
      val roasting = Roasting(None, Duration.ZERO, Some("Sample roasting comment"))
      val rating = Rating(None, 0.5f, Some("Sample rating comment"))
      val html = views.html.RoastingsController.show(flavor, roasting, Some(rating))

      contentAsString(html) must contain ("Sample flavor")
      contentAsString(html) must contain ("Roast time:")
      contentAsString(html) must contain ("Roasting comment: Sample roasting comment")
      contentAsString(html) must contain ("Rating:")
      contentAsString(html) must contain ("stars")
      contentAsString(html) must contain ("Rating comment: Sample rating comment")
    }
  }
}
