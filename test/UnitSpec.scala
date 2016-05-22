import org.specs2.mutable.Specification
import models.Flavor
import models.Rating
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import scala.collection.immutable.Map
import models.Roasting
import java.time.Duration

@RunWith(classOf[JUnitRunner])
class UnitSpec extends Specification {
  "Flavor" should {
    "accept a name" in {
      val flavor = Flavor(None, "Yellow Bourbon")
      flavor.name must equalTo("Yellow Bourbon")
    }
  }

  "Roasting" should {
    val flavor = Flavor(None, "a")
    "format its duration nicely" in {
      Roasting(None, Duration.ZERO, None).formatDuration must equalTo("PT0S") // Temporary formatting
    }
    "have an optional comment property" in {
      Roasting(None, Duration.ZERO, None) must be
      Roasting(None, Duration.ZERO, Some("The beans were superheated")) must be
    }
  }

  "Rating" should {
    val flavor = Flavor(None, "a")
    "calculate stars correctly" in {
      Rating(None, 0.0f).nStars must equalTo(0.0f)
      Rating(None, 0.1f).nStars must equalTo(0.5f)
      Rating(None, 0.2f).nStars must equalTo(1.0f)
      Rating(None, 0.3f).nStars must equalTo(1.5f)
      Rating(None, 0.4f).nStars must equalTo(2.0f)
      Rating(None, 0.5f).nStars must equalTo(2.5f)
      Rating(None, 0.6f).nStars must equalTo(3.0f)
      Rating(None, 0.7f).nStars must equalTo(3.5f)
      Rating(None, 0.8f).nStars must equalTo(4.0f)
      Rating(None, 0.9f).nStars must equalTo(4.5f)
      Rating(None, 1.0f).nStars must equalTo(5.0f)
    }
    "have an optional comment property" in {
      Rating(None, 1.0f) must be
      Rating(None, 0.0f, Some("It was terrible")) must be
    }
  }
}
