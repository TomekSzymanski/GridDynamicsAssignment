package gd


class QueryEvaluatorTest extends UnitSpec {

  it should "run benchmark 1" in {
    Given("")
    val input = new InputIterator(1000000)
    val evaluator = new QueryEvaluator(input)
    var numFound = 0

    time {
      val results = evaluator.findAllAttributesMatch(Attributes.MALE)
      numFound = results.size
    }

    println(numFound)
  }

  def time[R](block: => R): R = {
    val startTime = System.nanoTime()
    val result = block
    println("Elapsed time: " + (System.nanoTime - startTime) / 1000000 + " ms")
    result
  }

}
