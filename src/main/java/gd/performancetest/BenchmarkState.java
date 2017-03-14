package gd.performancetest;

import gd.BitwiseEncoder;
import gd.Person;
import gd.QueryEvaluator;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@State(Scope.Thread)
public class BenchmarkState {

    private final static int INPUT_TASK_SIZE = 1000000;

    private BitwiseEncoder encoder = new BitwiseEncoder();
    private TestPersonBuilder testPersonBuilder = new TestPersonBuilder(encoder);
    private List<Person> input = IntStream.range(0, INPUT_TASK_SIZE).mapToObj(id -> testPersonBuilder.buildSamplePerson(id)).collect(Collectors.toList());
    QueryEvaluator evaluator = new QueryEvaluator(input, encoder);
}