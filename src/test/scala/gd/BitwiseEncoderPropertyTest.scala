package gd

import org.scalacheck.Gen
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class BitwiseEncoderPropertyTest extends FlatSpec with Matchers with GivenWhenThen with GeneratorDrivenPropertyChecks {

  private val maxAttributes = 1000
  private val encoder = new BitwiseEncoder(maxAttributes)

  "encoded value" must "must contain all values than were passed to encoding" in {
    Given("lists of random lenght, up to maxAttributes, with random attributes indexes")
    val attribIndexesGen = for {
      numAttrs <- Gen.choose(1, maxAttributes -1)
      list <- Gen.nonEmptyListOf(numAttrs)
    } yield list

    When("Encode that list")
    Then("all attributes from original list must be contained")
    forAll(attribIndexesGen) {(indexes: Seq[Int]) =>
      val encoded = encoder.encode(indexes.toArray)
      encoder.forAllAttributes(encoded, indexes.toArray) shouldBe true
    }
  }

}
