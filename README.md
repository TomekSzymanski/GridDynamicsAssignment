# Running performance benchmarks
$sbt
> jmh:run -i 10 -wi 10 -f1 -t1

To test for other sizes of input task change in QueryEvaluatorPerformanceTest.scala: BenchmarkState.InputTaskSize

Possible optimisations:
1. Throw away Scala, only pure Java
2. In interfaces pass Java arrays of primitives (int, long). To safe on auto boxing and collection structures. Currently profiler shows 40% CPU spent in boxing integers!!
3. Try out more primitive collections (fastutils, GoldmanSachs etc.)