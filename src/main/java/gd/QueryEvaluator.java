package gd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryEvaluator {

    private final List<Person> input;
    private final BitwiseEncoder encoder;

    public QueryEvaluator(List<Person> input, BitwiseEncoder encoder) {
        this.input = input;
        this.encoder = encoder;
    }

    public List<Long> findAllAttributesMatch(List<Attributes> filterAttributes) {
        return findAllMatching(filterAttributes).map(p -> p.id).collect(Collectors.toList());
    }

    public GroupByRollupResult findAllAttributesMatch(List<Attributes> filterAttributes, List<Attributes> groupByAttributes) {
        if (groupByAttributes.isEmpty()) {
            throw new IllegalArgumentException("groupByAttributes list must be non empty");
        }
        Map<Attributes, Long> groupCounts = new ConcurrentHashMap<>();
        AtomicLong overallCount = new AtomicLong();
        AtomicLong noGrpAttrsCount = new AtomicLong();
        Stream<Person> allMatching = findAllMatching(filterAttributes);
        boolean[] anyGrpAttrMatched = {false};
        Map<Attributes, long[]> encodedAttributes = createEncodedAttributes(groupByAttributes);
        allMatching.forEach(person -> {
            overallCount.incrementAndGet();
            anyGrpAttrMatched[0] = false;
            groupByAttributes.forEach(attr -> {
                if (personContainsAttribute(attr, person, encodedAttributes)) {
                    anyGrpAttrMatched[0] = true;
                    groupCounts.put(attr, groupCounts.getOrDefault(attr, 0L) + 1);
                }
            });
            if (!anyGrpAttrMatched[0]) {
                noGrpAttrsCount.incrementAndGet();
            }
        });
        return new GroupByRollupResult(overallCount.longValue(), noGrpAttrsCount.longValue(), groupCounts);
    }

    private Map<Attributes, long[]> createEncodedAttributes(List<Attributes> attributes) {
        Map<Attributes, long[]> encodedAttributes = new IdentityHashMap<>(attributes.size());
        attributes.forEach(attr -> encodedAttributes.put(attr, encoder.encode(attr.ordinal())));
        return encodedAttributes;
    }

    private boolean personContainsAttribute(Attributes grpAttr, Person person, Map<Attributes, long[]> encodedAttributes) {
        long[] encodedParamIndexes = encodedAttributes.get(grpAttr);
        return (encoder.forAllAttributes(person.attributes, encodedParamIndexes));
    }

    private Stream<Person> findAllMatching(List<Attributes> filterAttributes) {
        int[] attribIndexes = new int[filterAttributes.size()];
        int attributeIdx;
        for (int i = 0; i < filterAttributes.size(); i++) {
            attributeIdx = filterAttributes.get(i).ordinal();
            attribIndexes[i] = attributeIdx;
        }
        long[] encodedParamIndexes = encoder.encode(attribIndexes);
        return input.parallelStream().filter(person -> encoder.forAllAttributes(person.attributes, encodedParamIndexes));
    }
}
