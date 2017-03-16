package gd;

import lombok.Value;

import java.util.Map;

@Value
public class GroupByRollupResult {

    public final long overallCount;

    /** Count of records that did not have any grouping attribute set.
     * For example records that passed the filtering but then did not have any grouping attribute set to true.
     */
    public final long noGrpAttrsRecordsCount;

    public final Map<Attribute, Long> perAttributeCounts;
}

