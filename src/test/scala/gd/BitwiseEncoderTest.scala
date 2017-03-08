package gd

class BitwiseEncoderTest extends UnitSpec {

  it should "be reversible" in {
    val encoded = BitwiseEncoder.encode(8)
    BitwiseEncoder.isTrue(encoded, 8) shouldBe true
  }

  it should "be reversible for multiple attrs set" in {
    val encoded = BitwiseEncoder.encode(0, 4, 10)
    BitwiseEncoder.isTrue(encoded, 0, 4, 10) shouldBe true

    BitwiseEncoder.isTrue(encoded, 0, 4) shouldBe false
    BitwiseEncoder.isTrue(encoded, 0, 10) shouldBe false
    BitwiseEncoder.isTrue(encoded, 0, 4, 11) shouldBe false
  }

}
