package gd

import java.lang.Long

class BitwiseEncoderTest extends UnitSpec {

  import gd.BitwiseEncoderTest.IntToBase

  private val DefaultMaxAttributesSupported = 500

  it should "encode one single active index correctly" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("single index is encoded. Here 8 corresponds to boolean array with all cells false but for one at index 8")
    val encoded = encoder.encode(8)

    Then("8 index is encoded correctly as one long word")
    encoded(0) shouldBe "100000000".b
    encoded.tail should contain only(0)
  }

  it should "encode two active indexes correctly" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("single index is encoded. Here 8 corresponds to boolean array with all cells false but for one at index 8")
    val encoded = encoder.encode(Array(2, 5))

    Then("indexes 2 and 5 are encoded correctly as one long word")
    encoded(0) shouldBe "100100".b
    encoded.tail should contain only(0)
  }

  it should "encode one single active index correctly: boundary case for index 0 (first index in long machine word)" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("")
    val encoded = encoder.encode(0)

    Then("8 index is encoded correctly as one long word")
    encoded(0) shouldBe "1".b
    encoded.tail should contain only(0)
  }

  it should "encode one single active index correctly: boundary case for index 63" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("")
    val encoded = encoder.encode(63)

    Then("63 index is encoded correctly as one long word")
    encoded.head shouldBe Long.MIN_VALUE
    encoded.tail should contain only(0)

    def stringWithNzeros(n: Int): String = List.fill(n)("0").mkString
  }

  it should "encode one single active index correctly: boundary case for index 64 (next machine word needed)" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("")
    val encoded = encoder.encode(64)

    Then("64 index is encoded correctly in second machine word")
    encoded.head shouldBe 0
    encoded(1) shouldBe "1".b
    encoded.drop(2) should contain only(0)
  }

  it should "encoded representation of single active index must pass test of itself containing that one index" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("single index is encoded. Here 8 corresponds to boolean array with all cells false but for one at index 8")
    val encoded = encoder.encode(8)

    Then("encoded representation must contain index 8")
    encoder.forAllAttributes(encoded, Array(8)) shouldBe true
  }

  it should "encoded representation of single active index must pass test of itself containing that one index: test for boundary value 0" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("representation of boolean array which has only first element true and all others false")
    val encoded = encoder.encode(Array(0))

    Then("encoded representation must contain index 0")
    encoder.forAllAttributes(encoded, Array(0)) shouldBe true

    Then("encoded representation must not contain any other indexes")
    encoder.forAllAttributes(encoded, Array(1)) shouldBe false
    encoder.forAllAttributes(encoded, Array(10)) shouldBe false
    encoder.forAllAttributes(encoded, Array(100)) shouldBe false
  }

  it should "encoded representation of 3 active indexes must contain any subset of that active indexes" in {
    Given("")
    val encoder = new BitwiseEncoder(DefaultMaxAttributesSupported)

    When("encode array with several indexes active")
    val encoded = encoder.encode(Array(0, 4, 100, 200))

    Then("encoded representation must contain all those indexes")
    encoder.forAllAttributes(encoded, Array(0, 4, 100, 200)) shouldBe true

    Then("encoded representation must contain all subsets of those indexes")
    encoder.forAllAttributes(encoded, Array(0)) shouldBe true
    encoder.forAllAttributes(encoded, Array(0, 4)) shouldBe true
    encoder.forAllAttributes(encoded, Array(0, 100)) shouldBe true

    Then("encoded representation must not contain indexes that were not encoded (even if some other indexes that were coded are also passes - AND, not OR)")
    encoder.forAllAttributes(encoded, Array(0, 4, 11)) shouldBe false
  }

  it should "work for max attributes less than one machine word" in {
    Given("")
    val encoder = new BitwiseEncoder(10)

    When("try to encode attributes within max attributes indexes, including last possible index (boundary test)")
    val encoded = encoder.encode(Array(0, 9))

    Then("")
    encoder.forAllAttributes(encoded, Array(0, 9)) shouldBe true
  }

  // preconditions checks disabled for performance
  ignore should "not accept attribute indexes greater than max capacity" in {
    Given("encoder with some max capacity")
    val encoder = new BitwiseEncoder(100)

    assertThrows[IllegalArgumentException] {
      When("trying to encode index that is already not with capacity")
      encoder.encode(100)
    }
  }

  def stringWithNzeros(n: Int): String = List.fill(n)("0").mkString
}

object BitwiseEncoderTest {
  implicit class IntToBase( val digits:String ) extends AnyVal {
    def base(b:Int) = Long.parseLong( digits, b)
    def b = base(2)
    def o = base(8)
    def x = base(16)
  }
}


