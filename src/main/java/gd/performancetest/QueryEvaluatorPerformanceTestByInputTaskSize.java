package gd.performancetest;

import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableList;
import gd.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class QueryEvaluatorPerformanceTestByInputTaskSize {

    // This test contains pairs of @Benchmark and @State methods for every test variant. It was necessary to separate out the test input data (people) from benchmarked search methods, as generation of test data itself takes much time.
    // Unfortunately, in JMH, such test setup (@State) can be injected into benchmark method only at specific class name (not interface) - that's why there is no single @Benchmark method and multiple @State methods.
    // Option is to generate such repetitive test script thru reflection

    // Performance benchmarking code is part of production code as it was not possible to have JMH recognize benchmarks in test code

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd1mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd1mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd1mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(1)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd5mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd5mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd5mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(5)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd10mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd10mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd10mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(10)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }
	
	@Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd15mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd15mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd15mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(15)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd20mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd20mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }
	
	@Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd35mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd35mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd35mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(35)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimitedGroupByAttrsAnd50mRecords(StateVerySmallFilterAttrsLimitedGroupByAttrsAnd50mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new EncodedPersonQueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimitedGroupByAttrsAnd50mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(50)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attribute.MALE, Attribute.FEMALE))
                .groupByAttributes(ImmutableList.of(Attribute.SINGLE, Attribute.MARRIED, Attribute.DIVORCED))
                .build();
    }
}


