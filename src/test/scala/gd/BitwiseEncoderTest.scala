package gd

class BitwiseEncoderTest extends UnitSpec {

  it should "be reversible" in {
    Given("")
    val encoder = new BitwiseEncoder
    val encoded = encoder.encode(Array(8))

    When("")
    Then("")
    encoder.forAllAttributes(encoded, Array(8)) shouldBe true
  }

  it should "handle attribute index 0" in {
    Given("")
    val encoder = new BitwiseEncoder
    val encoded = encoder.encode(Array(0))

    When("")
    Then("")
    encoder.forAllAttributes(encoded, Array(0)) shouldBe true
    encoder.forAllAttributes(encoded, Array(1)) shouldBe false
  }

  it should "be reversible for multiple attrs set indexes lower than one machine word size" in {
    Given("")
    val encoder = new BitwiseEncoder
    val encoded = encoder.encode(Array(0, 4, 10))

    When("")
    Then("")
    encoder.forAllAttributes(encoded, Array(0, 4, 10)) shouldBe true

    encoder.forAllAttributes(encoded, Array(0, 4)) shouldBe true
    encoder.forAllAttributes(encoded, Array(0, 10)) shouldBe true
    encoder.forAllAttributes(encoded, Array(0, 4, 11)) shouldBe false
  }

  it should "be reversible for multiple attrs with any indexes" in {
    Given("")
    val encoder = new BitwiseEncoder
    val encoded = encoder.encode(Array(0, 4, 10, 200, 400))

    When("")
    Then("")
    encoder.forAllAttributes(encoded, Array(0, 4, 10, 200, 400)) shouldBe true
  }

  it should "words for max attributes less than one machine word" in {
    Given("")
    val encoder = new BitwiseEncoder(maxAttributes = 10)
    val encoded = encoder.encode(Array(0, 4))

    When("")
    Then("")
    encoder.forAllAttributes(encoded, Array(0, 4)) shouldBe true
  }

  ignore should "not accept attribute indexes greater than max capacity" in {
    Given("")
    val encoder = new BitwiseEncoder(100)
    assertThrows[IllegalArgumentException] {
      encoder.encode(Array(100))
    }
  }
}
