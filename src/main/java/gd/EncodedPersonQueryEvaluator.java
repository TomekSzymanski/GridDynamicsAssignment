package gd;

import com.google.common.util.concurrent.AtomicLongMap;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncodedPersonQueryEvaluator implements QueryEvaluator {

    private final List<EncodedPerson> input;
    private final BitwiseEncoder encoder;

    /**
     * Creates new query evaluator that will execute queries on given list of people. We assume the input list of people is constant, therefore is passed in constructor.
     */
    public EncodedPersonQueryEvaluator(List<EncodedPerson> input, BitwiseEncoder encoder) {
        this.input = input;
        this.encoder = encoder;
    }

    @Override
    public List<Long> findAllAttributesMatch(List<Attribute> filterAttributes) {
        return findAllMatching(filterAttributes).map(p -> p.id).collect(Collectors.toList());
    }

    @Override
    public GroupByRollupResult findAllAttributesMatch(List<Attribute> filterAttributes, List<Attribute> groupByAttributes) {
        if (groupByAttributes.isEmpty()) {
            throw new IllegalArgumentException("groupByAttributes list must be non empty");
        }
        AtomicLongMap<Attribute> groupCounts = AtomicLongMap.create();
        AtomicLong overallCount = new AtomicLong();
        AtomicLong noGrpAttrsCount = new AtomicLong();
        Stream<EncodedPerson> allMatching = findAllMatching(filterAttributes);
        Map<Attribute, long[]> encodedAttributes = createEncodedAttributes(groupByAttributes);
        allMatching.forEach(person -> {
            overallCount.incrementAndGet();
            boolean[] anyGrpAttrMatched = {false};
            groupByAttributes.forEach(attr -> {
                if (personContainsAttribute(attr, person, encodedAttributes)) {
                    anyGrpAttrMatched[0] = true;
                    groupCounts.incrementAndGet(attr);
                }
            });
            if (!anyGrpAttrMatched[0]) {
                noGrpAttrsCount.incrementAndGet();
            }
        });
        return new GroupByRollupResult(overallCount.longValue(), noGrpAttrsCount.longValue(), groupCounts.asMap());
    }

    private Map<Attribute, long[]> createEncodedAttributes(List<Attribute> attributes) {
        Map<Attribute, long[]> encodedAttributes = new IdentityHashMap<>(attributes.size());
        attributes.forEach(attr -> encodedAttributes.put(attr, encoder.encode(attr.ordinal())));
        return encodedAttributes;
    }

    private boolean personContainsAttribute(Attribute grpAttr, EncodedPerson person, Map<Attribute, long[]> encodedAttributes) {
        long[] encodedParamIndexes = encodedAttributes.get(grpAttr);
        return (encoder.forAllAttributes(person.attributes, encodedParamIndexes));
    }

    private Stream<EncodedPerson> findAllMatching(List<Attribute> filterAttributes) {
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
