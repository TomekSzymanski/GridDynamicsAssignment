package gd

import scala.collection.mutable

class QueryEvaluator(private val input: Seq[Person], private val encoder: BitwiseEncoder) { //TODO iterator will be exhausted for next query

  def findAllAttributesMatch(filterAttributes: Seq[Attributes]): Seq[Long] = {
    findAllMatching(filterAttributes).map(_.id)
  }

  def findAllAttributesMatch(filterAttributes: Seq[Attributes], groupByAttributes: Seq[Attributes]): GroupByRollupResult = {
    require(groupByAttributes.nonEmpty)
    val groupCounts = new mutable.HashMap[Attributes, Long]()
    var overallCount: Long = 0
    var noGrpAttrsCount: Long = 0
    findAllMatching(filterAttributes).foreach(p => {
      overallCount = overallCount + 1
      var anyGrpAttrMatched = false
      groupByAttributes.foreach(grpAttr => {
        if (encoder.forAllAttributes(p.attributes, Seq(grpAttr.ordinal()))) {
          anyGrpAttrMatched = true
          groupCounts.put(grpAttr, groupCounts.getOrElse(grpAttr, 0L) + 1)
        }
      })
      if (!anyGrpAttrMatched) {
        noGrpAttrsCount = noGrpAttrsCount + 1
      }
    })
    GroupByRollupResult(overallCount, noGrpAttrsCount, groupCounts.toMap)
  }

  private def findAllMatching(filterAttributes: Seq[Attributes]): Stream[Person] = {
    val attribIndexes = filterAttributes.map(_.ordinal)
    input.filter(p => {
      encoder.forAllAttributes(p.attributes, attribIndexes)
    }).toStream
  }
}
