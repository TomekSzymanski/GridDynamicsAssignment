package gd

import scala.util.Random

class InputIterator(numRecords: Int, private val encoder: BitwiseEncoder) extends Iterator[Person] {

  private[this] val rnd = new Random()

  private[this] val values = {
    ProfilingHelper.time("test data generation") {
      1 to numRecords map buildSamplePerson
    }
  }.iterator

  private def buildRandomPerson(personId: Int): Person = {
    // this takes time, in the loop. For 1M records 8.5 second
    val numAttrsToSet = 1 + rnd.nextInt(encoder.maxAttributes)
    val attributeIndexes = 0 until numAttrsToSet map {dummy => rnd.nextInt(encoder.maxAttributes)}
    val attribs = encoder.encode(attributeIndexes)
    new Person(personId, attribs)
  }

  private def buildSamplePerson(personId: Int): Person = {
    val randomAttr1 = rnd.nextInt(10)
    val randomAttr2 = rnd.nextInt(10)
    new Person(personId, encoder.encode(Seq(randomAttr1, randomAttr2, 100, 200, 400)))
  }

  override def hasNext: Boolean = {
    values.hasNext
  }

  override def next(): Person = {
    values.next()
  }

}
