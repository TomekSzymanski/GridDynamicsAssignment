# Running performance benchmarks
$sbt
> jmh:run -i 10 -wi 10 -f1 -t1

Possible optimisations:
0. Run profiler
1. Throw away Scala, only pure Java
2. In interfaces pass Java arrays of primitives (int, long). To safe on auto boxing and collection structures. Try out more primitive collections (fastutils, GoldmanSachs etc.)
3. log gc timestamp and details, maybe extend much heap
