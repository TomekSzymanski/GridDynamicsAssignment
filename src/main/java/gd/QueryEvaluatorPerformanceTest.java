package gd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class QueryEvaluatorPerformanceTest {

  @Benchmark
  public void test(BenchmarkState state, Blackhole bh) {
    List<Attributes> filterAttributes = new ArrayList<Attributes>() {{
      this.add(Attributes.MALE);
      this.add(Attributes.FEMALE);
    }};

    List<Attributes> groupByAttributes = new ArrayList<Attributes>() {{
      this.add(Attributes.SINGLE);
    }};
    GroupByRollupResult results = state.evaluator.findAllAttributesMatch(filterAttributes, groupByAttributes);
    bh.consume(results);
  }
}
