package gd

object ProfilingHelper {

  def time[R](userComment: String)(block: => R): R = {
    val startTime = System.nanoTime()
    val result = block
    println(userComment + ": elapsed time: " + (System.nanoTime - startTime) / 1000000 + " ms")
    result
  }
}
