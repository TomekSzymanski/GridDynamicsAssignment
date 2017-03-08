package gd

case class GroupByRollupResult(overallCount: Long,
                               noGrpAttrsCount: Long,
                               perAttributeCounts: Map[Attributes, Long])