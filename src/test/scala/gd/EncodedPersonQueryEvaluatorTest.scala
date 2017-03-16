package gd

import java.util.Collections

import com.google.common.collect.ImmutableList

import collection.JavaConverters._

class EncodedPersonQueryEvaluatorTest extends UnitSpec {

  private val encoder = new BitwiseEncoder(500)

  it should "pass all input records when filtering on empty filtering attributes list " in {
    Given("two person input, both with different attributes active")
    val twoElementsInput = Seq(
      new EncodedPerson(1, encode(Attribute.MALE)),
      new EncodedPerson(2, encode(Attribute.FEMALE))
    ).toList.asJava
    val evaluator = new EncodedPersonQueryEvaluator(twoElementsInput, encoder)

    When("filter by empty attributes list")
    val peopleFoundIds = evaluator.findAllAttributesMatch(Collections.emptyList())

    Then("All people passed")
    peopleFoundIds should have size 2
    peopleFoundIds should contain allElementsOf Seq(1, 2)
  }

  it should "basic filtering works. One filter attribute" in {
    Given("two person input, both with different attributes active")
    val twoElementsInput = Seq(
      new EncodedPerson(1, encode(Attribute.MALE)),
      new EncodedPerson(2, encode(Attribute.FEMALE))
    ).toList.asJava
    val evaluator = new EncodedPersonQueryEvaluator(twoElementsInput, encoder)

    When("filtering by specific attributes")
    val peopleFoundIds = evaluator.findAllAttributesMatch(Collections.singletonList(Attribute.MALE))

    Then("Only matching people are returned")
    peopleFoundIds should have size 1
    peopleFoundIds.get(0) shouldBe 1
  }

  it should "return empty group by result when no input records passed filtering" in {
    Given("Single element input")
    val twoElementsInput = Seq(new EncodedPerson(1, encode(Attribute.FEMALE))).toList.asJava
    val evaluator = new EncodedPersonQueryEvaluator(twoElementsInput, encoder)

    When("Trying to filter by attribute that is not active in any input, and then grouping by other attributes")
    val countGroupBy = evaluator.findAllAttributesMatch(Collections.singletonList(Attribute.MALE), ImmutableList.of(Attribute.ATTR_0, Attribute.ATTR_1))

    Then("Overall count from group by is 0, as not attributes passed filtering")
    countGroupBy.overallCount shouldBe 0
    Then("the count of people that did not match any group by attribute is 0, as not attributes passed filtering")
    countGroupBy.noGrpAttrsRecordsCount shouldBe 0
    Then("map of counters for every group by attribute is empty")
    countGroupBy.perAttributeCounts.isEmpty shouldBe true
  }

  it should "return empty group by attribute counts when no grouping attribute was active in any input record" in {
    Given("Single element input with no group by attribute active")
    val twoElementsInput = Seq(new EncodedPerson(1, encode(Attribute.MALE))).toList.asJava
    val evaluator = new EncodedPersonQueryEvaluator(twoElementsInput, encoder)

    When("filtering by attribute which is active and then grouping by attributes that are not active in any input record")
    val countGroupBy = evaluator.findAllAttributesMatch(ImmutableList.of(Attribute.MALE), ImmutableList.of(Attribute.ATTR_0, Attribute.ATTR_1))

    Then("Overall count of records is 1")
    countGroupBy.overallCount shouldBe 1
    Then("the count of people that did not match any group by attribute is 1, as that one record did not have any group by attributes set")
    countGroupBy.noGrpAttrsRecordsCount shouldBe 1
    countGroupBy.perAttributeCounts.isEmpty shouldBe true
  }

  it should "return empty no group attributes count when all input records have at least one grouping attribute set" in {
    Given("two elements input, each hav at least one grouping attribute active")
    val twoElementsInput = Seq(
      new EncodedPerson(1, encode(Attribute.MALE, Attribute.ATTR_0)),
      new EncodedPerson(2, encode(Attribute.MALE, Attribute.ATTR_1))
    ).toList.asJava
    val evaluator = new EncodedPersonQueryEvaluator(twoElementsInput, encoder)

    When("filter by one attribute which is active in all input records and group by attributes which records have at least one active")
    val countGroupBy = evaluator.findAllAttributesMatch(ImmutableList.of(Attribute.MALE), ImmutableList.of(Attribute.ATTR_0, Attribute.ATTR_1))

    Then("")
    countGroupBy.overallCount shouldBe 2
    Then("there must be no people not matched to aby group by attribute")
    countGroupBy.noGrpAttrsRecordsCount shouldBe 0
    countGroupBy.perAttributeCounts.asScala shouldEqual Map(
      Attribute.ATTR_0 -> 1,
      Attribute.ATTR_1 -> 1
    )
  }

  it should "return separate sums for all group by attributes" in {
    Given("input which contains both people that have both group by attributes active, either one or another group by attribute active, or no group by attributes active")
    val twoElementsInput = Seq(
      new EncodedPerson(1, encode(Attribute.MALE, Attribute.ATTR_0, Attribute.ATTR_1)),
      new EncodedPerson(2, encode(Attribute.MALE, Attribute.ATTR_0)),
      new EncodedPerson(3, encode(Attribute.MALE, Attribute.ATTR_1)),
      new EncodedPerson(4, encode(Attribute.MALE)),
      new EncodedPerson(5, encode(Attribute.FEMALE))
    ).toList.asJava
    val evaluator = new EncodedPersonQueryEvaluator(twoElementsInput, encoder)

    When("filter by one attribute and group by other attributes")
    val countGroupBy = evaluator.findAllAttributesMatch(ImmutableList.of(Attribute.MALE), ImmutableList.of(Attribute.ATTR_0, Attribute.ATTR_1))

    Then("")
    countGroupBy.overallCount shouldBe 4
    Then("There is one person who passed filtering but did not have any group by attributes active")
    countGroupBy.noGrpAttrsRecordsCount shouldBe 1
    countGroupBy.perAttributeCounts.asScala shouldEqual Map( //
      Attribute.ATTR_0 -> 2,
      Attribute.ATTR_1 -> 2
    )
  }

  def encode(attributes: Attribute*): Array[Long] = {
    val attributesArr = attributes.map(_.ordinal).toArray
    encoder.encode(attributesArr)
  }

}
