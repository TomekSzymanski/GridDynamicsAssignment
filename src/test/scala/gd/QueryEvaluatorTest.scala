package gd

import java.util.Collections

import com.google.common.collect.ImmutableList

import collection.JavaConverters._

class QueryEvaluatorTest extends UnitSpec {

  private val encoder = new BitwiseEncoder

  it should "return overall sum" in {
    Given("")
    val twoElementsInput = Seq(
      new Person(1, encoder.encode(Array(Attributes.MALE.ordinal()))),
      new Person(2, encoder.encode(Array(Attributes.FEMALE.ordinal())))
    ).toList.asJava
    val evaluator = new QueryEvaluator(twoElementsInput, encoder)

    When("")
    val peopleFoundIds = evaluator.findAllAttributesMatch(Collections.singletonList(Attributes.MALE))
    peopleFoundIds should contain only (1)
  }

  it should "return empty group by result when no input records passed filtering" in {
    Given("")
    val twoElementsInput = Seq(new Person(1, encoder.encode(Array(Attributes.FEMALE.ordinal())))).toList.asJava
    val evaluator = new QueryEvaluator(twoElementsInput, encoder)

    When("")
    val countGroupBy = evaluator.findAllAttributesMatch(Collections.singletonList(Attributes.MALE), ImmutableList.of(Attributes.ATTR_0, Attributes.ATTR_1))

    Then("")
    countGroupBy.overallCount shouldBe 0
    countGroupBy.noGrpAttrsCount shouldBe 0
    countGroupBy.perAttributeCounts shouldBe 'empty
  }

  it should "return empty group by attribute counts when no grouping attribute was set in any input record" in {
    Given("")
    val twoElementsInput = Seq(new Person(1, encoder.encode(Array(Attributes.MALE.ordinal())))).toList.asJava
    val evaluator = new QueryEvaluator(twoElementsInput, encoder)

    When("")
    val countGroupBy = evaluator.findAllAttributesMatch(ImmutableList.of(Attributes.MALE), ImmutableList.of(Attributes.ATTR_0, Attributes.ATTR_1))

    Then("")
    countGroupBy.overallCount shouldBe 1
    countGroupBy.noGrpAttrsCount shouldBe 1
    countGroupBy.perAttributeCounts shouldBe 'empty
  }

  it should "return empty no group attributes count when all input records set at least one grouping attribute set" in {
    Given("")
    val twoElementsInput = Seq(
      new Person(1, encoder.encode(Array(Attributes.MALE.ordinal(), Attributes.ATTR_0.ordinal()))),
      new Person(2, encoder.encode(Array(Attributes.MALE.ordinal(), Attributes.ATTR_1.ordinal())))
    ).toList.asJava
    val evaluator = new QueryEvaluator(twoElementsInput, encoder)

    When("")
    val countGroupBy = evaluator.findAllAttributesMatch(ImmutableList.of(Attributes.MALE), ImmutableList.of(Attributes.ATTR_0, Attributes.ATTR_1))

    Then("")
    countGroupBy.overallCount shouldBe 2
    countGroupBy.noGrpAttrsCount shouldBe 0
    countGroupBy.perAttributeCounts.asScala shouldEqual Map(
      Attributes.ATTR_0 -> 1,
      Attributes.ATTR_1 -> 1
    )
  }

  it should "return separate sums for all group by attributes" in {
    Given("")
    val twoElementsInput = Seq(
      new Person(1, encoder.encode(Array(Attributes.MALE.ordinal(), Attributes.ATTR_0.ordinal(), Attributes.ATTR_1.ordinal()))),
      new Person(2, encoder.encode(Array(Attributes.MALE.ordinal(), Attributes.ATTR_0.ordinal()))),
      new Person(3, encoder.encode(Array(Attributes.MALE.ordinal(), Attributes.ATTR_1.ordinal()))),
      new Person(4, encoder.encode(Array(Attributes.MALE.ordinal()))),

      new Person(5, encoder.encode(Array(Attributes.FEMALE.ordinal())))
    ).toList.asJava
    val evaluator = new QueryEvaluator(twoElementsInput, encoder)

    When("")
    val countGroupBy = evaluator.findAllAttributesMatch(ImmutableList.of(Attributes.MALE), ImmutableList.of(Attributes.ATTR_0, Attributes.ATTR_1))

    Then("")
    countGroupBy.overallCount shouldBe 4
    countGroupBy.noGrpAttrsCount shouldBe 1
    countGroupBy.perAttributeCounts.asScala shouldEqual Map(
      Attributes.ATTR_0 -> 2,
      Attributes.ATTR_1 -> 2
    )
  }

}
