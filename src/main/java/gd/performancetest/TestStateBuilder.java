package gd.performancetest;

import gd.Attributes;
import gd.BitwiseEncoder;
import gd.Person;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TestStateBuilder {

    private int inputTaskSize;
    private int maxAttributes;

    private List<Attributes> filterAttributes = Collections.emptyList();
    private List<Attributes> groupByAttributes = Collections.emptyList();

    TestStateBuilder inputTaskSizeMillions(int inputTaskSizeMillions) {
        this.inputTaskSize = inputTaskSizeMillions * 1000000;
        return this;
    }

    TestStateBuilder maxAttributes(int maxAttributes) {
        this.maxAttributes = maxAttributes;
        return this;
    }

    TestStateBuilder filterAttributes(List<Attributes> filterAttributes) {
        this.filterAttributes = filterAttributes;
        return this;
    }

    TestStateBuilder groupByAttributes(List<Attributes> groupByAttributes) {
        this.groupByAttributes = groupByAttributes;
        return this;
    }

    public TestState build() {
        assertMandatoryAttributesSet();
        BitwiseEncoder encoder = new BitwiseEncoder(maxAttributes);
        TestPersonFactory testPersonFactory = new TestPersonFactory(encoder, maxAttributes);
        List<Person> input = IntStream
                .range(0, inputTaskSize)
                .mapToObj(id -> {
                    int focusAttributesMaxIndex = Math.min(10, maxAttributes / 2);
                    return testPersonFactory.buildSamplePerson(id, focusAttributesMaxIndex);})
                .collect(Collectors.toList());
        return new TestState(input, encoder, filterAttributes, groupByAttributes);
    }

    private void assertMandatoryAttributesSet() {
        if (inputTaskSize == 0) {
            throw new IllegalStateException("you have to set inputTaskSizeMillions.");
        }
        if (maxAttributes == 0) {
            throw new IllegalStateException("you have to set maxAttributes.");
        }
        if (filterAttributes.isEmpty()) {
            throw new IllegalStateException("you have to set filterAttributes and set it to non empty list.");
        }
        if (groupByAttributes.isEmpty()) {
            throw new IllegalStateException("you have to set groupByAttributes and set it to non empty list.");
        }
    }
}
