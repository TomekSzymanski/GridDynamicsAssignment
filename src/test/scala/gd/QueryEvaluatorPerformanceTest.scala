package gd

class QueryEvaluatorPerformanceTest extends UnitSpec {

  private val encoder = new BitwiseEncoder

  it should "run benchmark 1" in {
    Given("")
    val input = new InputIterator(1000000, encoder)
    val evaluator = new QueryEvaluator(input, encoder)
    var numFound = 0

    ProfilingHelper.time("evaluation") {
      val results = evaluator.findAllAttributesMatch(Seq(Attributes.MALE, Attributes.SINGLE))
      numFound = results.size
    }

    println(numFound)
  }

}
