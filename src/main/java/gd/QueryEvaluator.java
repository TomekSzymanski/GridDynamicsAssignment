package gd;

import java.util.List;

public interface QueryEvaluator {

    /**
     * Finds ids of all people that have all provided attributes active
     */
    List<Long> findAllAttributesMatch(List<Attribute> filterAttributes);

    /**
     * Returns group by rollup result for query with given filter attributes and group by attributes
     * @param filterAttributes
     * @param groupByAttributes list of attributes to group by. Every person must have at least one group by attribute active to be included in GroupByRollupResult.perAttributeCounts, otherwise it is included in noGrpAttrsRecordsCount. See unit tests.
     * @return
     */
    GroupByRollupResult findAllAttributesMatch(List<Attribute> filterAttributes, List<Attribute> groupByAttributes);
}
