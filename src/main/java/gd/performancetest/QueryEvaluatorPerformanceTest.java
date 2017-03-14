package gd.performancetest;

import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableList;
import gd.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class QueryEvaluatorPerformanceTest {

    @Benchmark
    public void verySmallFilterAttrsLimittedGroupByAttrsAnd1mRecords(StateVerySmallFilterAttrsLimittedGroupByAttrsAnd1mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new QueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimittedGroupByAttrsAnd1mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(1)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attributes.MALE, Attributes.FEMALE))
                .groupByAttributes(ImmutableList.of(Attributes.SINGLE, Attributes.MARRIED, Attributes.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimittedGroupByAttrsAnd20mRecords(StateVerySmallFilterAttrsLimittedGroupByAttrsAnd20mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new QueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimittedGroupByAttrsAnd20mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attributes.MALE, Attributes.FEMALE))
                .groupByAttributes(ImmutableList.of(Attributes.SINGLE, Attributes.MARRIED, Attributes.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimittedGroupByAttrsAnd20mRecordsAnd2000Attributes(StateVerySmallFilterAttrsLimittedGroupByAttrsAnd20mRecordsAnd2000Attributes s, Blackhole bh) {
        QueryEvaluator evaluator = new QueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimittedGroupByAttrsAnd20mRecordsAnd2000Attributes {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(20)
                .maxAttributes(2000)
                .filterAttributes(ImmutableList.of(Attributes.MALE, Attributes.FEMALE))
                .groupByAttributes(ImmutableList.of(Attributes.SINGLE, Attributes.MARRIED, Attributes.DIVORCED))
                .build();
    }

    @Benchmark
    public void verySmallFilterAttrsLimittedGroupByAttrsAnd50mRecords(StateVerySmallFilterAttrsLimittedGroupByAttrsAnd50mRecords s, Blackhole bh) {
        QueryEvaluator evaluator = new QueryEvaluator(s.testState.getInput(), s.testState.getEncoder());
        GroupByRollupResult results = evaluator.findAllAttributesMatch(s.testState.getFilterAttributes(), s.testState.getGroupByAttributes());
        bh.consume(results);
    }

    @State(Scope.Thread)
    public static class StateVerySmallFilterAttrsLimittedGroupByAttrsAnd50mRecords {
        final TestState testState = new TestStateBuilder()
                .inputTaskSizeMillions(50)
                .maxAttributes(500)
                .filterAttributes(ImmutableList.of(Attributes.MALE, Attributes.FEMALE))
                .groupByAttributes(ImmutableList.of(Attributes.SINGLE, Attributes.MARRIED, Attributes.DIVORCED))
                .build();
    }
}


