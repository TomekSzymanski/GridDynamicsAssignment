package gd;

import java.util.Map;

public class GroupByRollupResult {
    public final long overallCount;
    public final long noGrpAttrsCount;
    public final Map<Attributes, Long> perAttributeCounts;

    public GroupByRollupResult(long overallCount, long noGrpAttrsCount, Map<Attributes, Long> perAttributeCounts) {
        this.overallCount = overallCount;
        this.noGrpAttrsCount = noGrpAttrsCount;
        this.perAttributeCounts = perAttributeCounts;
    }
}

