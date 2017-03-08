package gd

class QueryEvaluator(private val input: Iterator[Person]) {

  def findAllAttributesMatch(attributes: Attributes*): Seq[Long] = {
    val attribIndexes = attributes.map(_.ordinal)
    input.filter(p => {
      BitwiseEncoder.isTrue(p.attributes, attribIndexes:_*)
    }).map(_.id).toSeq
  }
}
