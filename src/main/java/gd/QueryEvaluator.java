package gd;

import java.util.Arrays;
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

    List<Long> findAllAttributesMatch(List<Attributes> filterAttributes) {
        return findAllMatching(filterAttributes).map(p -> p.id).collect(Collectors.toList());
    }

    GroupByRollupResult findAllAttributesMatch(List<Attributes> filterAttributes, List<Attributes> groupByAttributes) {
        if (groupByAttributes.isEmpty()) {
            throw new IllegalArgumentException("groupByAttributes list must be non empty");
        }
        Map<Attributes, Long> groupCounts = new ConcurrentHashMap<>();
        AtomicLong overallCount = new AtomicLong();
        AtomicLong noGrpAttrsCount = new AtomicLong();
        findAllMatching(filterAttributes).forEach(p -> {
                overallCount.incrementAndGet();
            final boolean[] anyGrpAttrMatched = {false};
                groupByAttributes.forEach(grpAttr -> {
                    int[] paramIndexes = new int[1];
                    Arrays.fill(paramIndexes, grpAttr.ordinal());
                    if (encoder.forAllAttributes(p.attributes, paramIndexes)) {
                        anyGrpAttrMatched[0] = true;
                        groupCounts.put(grpAttr, groupCounts.getOrDefault(grpAttr, 0L) + 1);
                    }
                });
        if (!anyGrpAttrMatched[0]) {
            noGrpAttrsCount.incrementAndGet();
        }
        });
        return new GroupByRollupResult(overallCount.longValue(), noGrpAttrsCount.longValue(), groupCounts);
    }

    private Stream<Person> findAllMatching(List<Attributes> filterAttributes)  {
        int [] attribIndexes = new int[filterAttributes.size()];
        int attributeIdx;
        for (int i = 0; i < filterAttributes.size(); i++) {
            attributeIdx = filterAttributes.get(i).ordinal();
            attribIndexes[i] = attributeIdx;
        }
        return input.parallelStream().filter(p -> encoder.forAllAttributes(p.attributes, attribIndexes));
    }
}
