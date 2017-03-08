package gd

object BitwiseEncoder {

  private val wordLength: Int = 64
  private val maxWordsNeeded: Int = Math.ceil(MaxAttribs / wordLength).toInt

  def encode(paramIdx: Int*): Array[Long] = {
    val codingWords = initializeEmpty
    paramIdx.foreach(idx => {
      val wordIndex = idx / wordLength
      val indexInWord = idx % wordLength
      codingWords(wordIndex) = codingWords(wordIndex) | (1L << indexInWord)
    })
    codingWords
  }

  def isTrue(input: Array[Long], paramIdx: Int*): Boolean = {
    require(paramIdx.nonEmpty)
    val expected = encode(paramIdx:_*)
    input sameElements expected
  }

  private def initializeEmpty: Array[Long] = {
    Array.fill(maxWordsNeeded){0L}
  }
}
