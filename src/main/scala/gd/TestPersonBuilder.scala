package gd

import scala.util.Random

class TestPersonBuilder(private val encoder: BitwiseEncoder) {

  private[this] val rnd = new Random()

  def buildRandomPerson(personId: Int): Person = {
    // this takes time, in the loop. For 1M records 8.5 second
    val numAttrsToSet = 1 + rnd.nextInt(encoder.maxAttributes)
    val attributeIndexes = 0 until numAttrsToSet map {dummy => rnd.nextInt(encoder.maxAttributes)} toArray
    val attribs = encoder.encode(attributeIndexes)
    new Person(personId, attribs)
  }

  def buildSamplePerson(personId: Int): Person = {
    val randomAttr1 = rnd.nextInt(10)
    val randomAttr2 = rnd.nextInt(10)
    new Person(personId, encoder.encode(Array(randomAttr1, randomAttr2, 100, 200, 400)))
  }
}
