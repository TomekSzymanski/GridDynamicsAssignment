package gd

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import scala.collection.JavaConverters._
import scala.collection.parallel.ParSeq

class QueryEvaluator(private val input: Seq[Person], private val encoder: BitwiseEncoder) {

  def findAllAttributesMatch(filterAttributes: Seq[Attributes]): Seq[Long] = {
    findAllMatching(filterAttributes).map(_.id).seq
  }

  def findAllAttributesMatch(filterAttributes: Seq[Attributes], groupByAttributes: Seq[Attributes]): GroupByRollupResult = {
    require(groupByAttributes.nonEmpty)
    val groupCounts = new ConcurrentHashMap[Attributes, Long]()
    val overallCount = new AtomicLong
    val noGrpAttrsCount = new AtomicLong
    findAllMatching(filterAttributes).foreach(p => {
      overallCount.incrementAndGet()
      var anyGrpAttrMatched = false
      groupByAttributes.foreach(grpAttr => {
        if (encoder.forAllAttributes(p.attributes, Seq(grpAttr.ordinal()))) {
          anyGrpAttrMatched = true
          groupCounts.put(grpAttr, groupCounts.getOrDefault(grpAttr, 0L) + 1)
        }
      })
      if (!anyGrpAttrMatched) {
        noGrpAttrsCount.incrementAndGet()
      }
    })
    GroupByRollupResult(overallCount.longValue, noGrpAttrsCount.longValue, groupCounts.asScala.toMap)
  }

  private def findAllMatching(filterAttributes: Seq[Attributes]): ParSeq[Person] = {
    val attribIndexes = filterAttributes.map(_.ordinal)
    input.par.filter(p => {
      encoder.forAllAttributes(p.attributes, attribIndexes)
    })
  }
}
