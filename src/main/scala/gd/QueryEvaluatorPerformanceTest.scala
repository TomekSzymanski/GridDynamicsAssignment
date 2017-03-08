package gd

import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
class QueryEvaluatorPerformanceTest {

  @Benchmark
  def test(state: BenchmarkState, bh: Blackhole): Unit = {
    val results = state.evaluator.findAllAttributesMatch(Seq(Attributes.MALE, Attributes.SINGLE), Seq(Attributes.SINGLE))
    bh.consume(results)
  }
}

@State(Scope.Thread)
class BenchmarkState {
  private val encoder = new BitwiseEncoder
  private val testPersonBuilder = new TestPersonBuilder(encoder)
  def evaluator = {
    val input = 1 to 1000000 map testPersonBuilder.buildSamplePerson
    new QueryEvaluator(input, encoder)
  }
}
