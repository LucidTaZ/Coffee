import org.specs2.mutable.Specification
import models.Flavor
import models.Rating
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import scala.collection.immutable.Map

@RunWith(classOf[JUnitRunner])
class UnitSpec extends Specification {
  "Flavor" should {
    "accept a name" in {
      val flavor = Flavor("Yellow Bourbon")
      flavor.name must equalTo("Yellow Bourbon")
    }
  }

  "Rating" should {
    "calculate stars correctly" in {
      Rating(new Flavor("a"), 0.0f).nStars must equalTo(0.0f)
      Rating(new Flavor("a"), 0.1f).nStars must equalTo(0.5f)
      Rating(new Flavor("a"), 0.2f).nStars must equalTo(1.0f)
      Rating(new Flavor("a"), 0.3f).nStars must equalTo(1.5f)
      Rating(new Flavor("a"), 0.4f).nStars must equalTo(2.0f)
      Rating(new Flavor("a"), 0.5f).nStars must equalTo(2.5f)
      Rating(new Flavor("a"), 0.6f).nStars must equalTo(3.0f)
      Rating(new Flavor("a"), 0.7f).nStars must equalTo(3.5f)
      Rating(new Flavor("a"), 0.8f).nStars must equalTo(4.0f)
      Rating(new Flavor("a"), 0.9f).nStars must equalTo(4.5f)
      Rating(new Flavor("a"), 1.0f).nStars must equalTo(5.0f)
    }
    "have an optional comment property" in {
      Rating(new Flavor("a"), 1.0f) must be
      Rating(new Flavor("b"), 0.0f, Some("It was terrible")) must be
    }
  }
}
