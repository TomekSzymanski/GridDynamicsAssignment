package gd

class BitwiseEncoder(val maxAttributes: Int = MaxAttribs) {
  require(maxAttributes > 0)

  private val wordLength: Int = 64

  private val wordsNeeded: Int = Math.ceil(maxAttributes.toDouble / wordLength).toInt

  def encode(paramIdx: Seq[Int]): Array[Long] = {
    ensureAllIdexesValid(paramIdx)
    val codingWords = initializeEmpty
    paramIdx.foreach(idx => {
      val wordIndex = idx / wordLength
      val indexInWord = idx % wordLength
      codingWords(wordIndex) = codingWords(wordIndex) | (1L << indexInWord)
    })
    codingWords
  }

  def forAllAttributes(input: Array[Long], paramIdx: Seq[Int]): Boolean = {
    ensureAllIdexesValid(paramIdx)
    val expected = encode(paramIdx)
    for(i <- input.indices) {
      if ((input(i) & expected(i)) != expected(i)) {
        return false
      }
    }
    true
  }

  private def ensureAllIdexesValid(paramIdx: Seq[Int]): Unit = {
    require(paramIdx.nonEmpty)
    require(paramIdx.forall(_ >= 0))
    require(allIndexesWithinCapacity(paramIdx))
  }

  private def allIndexesWithinCapacity(paramIdx: Seq[Int]): Boolean = {
    paramIdx.forall(_ < maxAttributes)
  }

  private def initializeEmpty: Array[Long] = {
    Array.fill(wordsNeeded){0L}
  }
}
