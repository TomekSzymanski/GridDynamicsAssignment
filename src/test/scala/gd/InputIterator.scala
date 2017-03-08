package gd

import scala.util.Random

class InputIterator(numRecords: Int) extends Iterator[Person] {

  private[this] var cnt = 0

  private[this] val rnd = new Random()

  override def hasNext: Boolean = {
    val result = cnt < numRecords
    cnt = cnt + 1
    result
  }

  override def next(): Person = {
    assume(cnt <= numRecords)

    val numPossibleAttribs = Attributes.values.length
    val numAttrsToSet = rnd.nextInt(numPossibleAttribs)
    val attribs = BitwiseEncoder.encode(0 until numAttrsToSet:_*)

    new Person(cnt, attribs)
  }
}
