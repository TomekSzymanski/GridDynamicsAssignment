package gd

class QueryEvaluator(private val input: Iterator[Person], private val encoder: BitwiseEncoder) { //TODO iterator will be exhausted for next query

  def findAllAttributesMatch(attributes: Attributes*): Seq[Long] = {
    val attribIndexes = attributes.map(_.ordinal)
    input.filter(p => {
      encoder.forAllAttributes(p.attributes, attribIndexes:_*)
    }).map(_.id).toSeq
  }
}
