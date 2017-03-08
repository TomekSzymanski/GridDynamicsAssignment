package gd

class BitwiseEncoder(val maxAttributes: Int = MaxAttribs) {
  require(maxAttributes > 0)

  private val wordLength: Int = 64

  private val wordsNeeded: Int = Math.ceil(maxAttributes.toDouble / wordLength).toInt

  def encode(paramIdx: Int*): Array[Long] = {
    ensureAllIdexesValid(paramIdx:_*)
    val codingWords = initializeEmpty
    paramIdx.foreach(idx => {
      val wordIndex = idx / wordLength
      val indexInWord = idx % wordLength
      codingWords(wordIndex) = codingWords(wordIndex) | (1L << indexInWord)
    })
    codingWords
  }

  def forAllAttributes(input: Array[Long], paramIdx: Int*): Boolean = {
    ensureAllIdexesValid(paramIdx:_*)
    val expected = encode(paramIdx:_*)
    for(i <- 0 until input.length) {
      if ((input(i) & expected(i)) != expected(i)) {
        return false
      }
    }
    true
  }

  private def ensureAllIdexesValid(paramIdx: Int*): Unit = {
    require(paramIdx.nonEmpty)
    require(allIndexesWithinCapacity(paramIdx:_*))
  }

  private def allIndexesWithinCapacity(paramIdx: Int*): Boolean = {
    paramIdx.forall(_ < maxAttributes)
  }

  private def initializeEmpty: Array[Long] = {
    Array.fill(wordsNeeded){0L}
  }
}
