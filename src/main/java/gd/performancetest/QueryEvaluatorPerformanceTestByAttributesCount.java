package gd.performancetest;

import com.google.common.collect.ImmutableList;
import gd.Attribute;
import gd.EncodedPersonQueryEvaluator;
import gd.GroupByRollupResult;
import gd.QueryEvaluator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class QueryEvaluatorPerformanceTestByAttributesCount {

    // This test contains pairs of @Benchmark and @State methods for every test variant. It was necessary to separate out the test input data (people) from benchmarked search methods, as generation of test data itself takes much time.
    // Unfortunately, in JMH, such test setup (@State) can be injected into benchmark method only at specific class name (not interface) - that's why there is no single @Benchmark method and multiple @State methods.
    // Option is to generate such repetitive test script thru reflection

    // Performance benchmarking code is part of production code as it was not possible to have JMH recognize benchmarks in test code

	@Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd100Attributes(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd100Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd100Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(100)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }
	
    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd200Attributes(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd200Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd200Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(200)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd500Attributes(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd500Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd500Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd1000Attributes(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd1000Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd1000Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(1000)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }
	
	@Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd1500Attributes(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd1500Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd1500Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(1500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd2000Attributes(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd2000Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecordsAnd2000Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(2000)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }
}


