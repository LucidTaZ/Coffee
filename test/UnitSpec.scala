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
      val flavor = Flavor("Yellow Bourbon")
      flavor.name must equalTo("Yellow Bourbon")
    }
  }

  "Roasting" should {
    val flavor = Flavor("a");
    "format its duration nicely" in {
      new Roasting(flavor, Duration.ZERO).formatDuration must equalTo("PT0S") // Temporary formatting
    }
    "have an optional comment property" in {
      new Roasting(flavor, Duration.ZERO) must be
      new Roasting(flavor, Duration.ZERO, Some("The beans were superheated")) must be
    }
  }

  "Rating" should {
    val flavor = Flavor("a");
    val roasting = new Roasting(flavor, Duration.ZERO);
    "calculate stars correctly" in {
      new Rating(0.0f).nStars must equalTo(0.0f)
      new Rating(0.1f).nStars must equalTo(0.5f)
      new Rating(0.2f).nStars must equalTo(1.0f)
      new Rating(0.3f).nStars must equalTo(1.5f)
      new Rating(0.4f).nStars must equalTo(2.0f)
      new Rating(0.5f).nStars must equalTo(2.5f)
      new Rating(0.6f).nStars must equalTo(3.0f)
      new Rating(0.7f).nStars must equalTo(3.5f)
      new Rating(0.8f).nStars must equalTo(4.0f)
      new Rating(0.9f).nStars must equalTo(4.5f)
      new Rating(1.0f).nStars must equalTo(5.0f)
    }
    "have an optional comment property" in {
      new Rating(1.0f) must be
      new Rating(0.0f, Some("It was terrible")) must be
    }
  }
}
