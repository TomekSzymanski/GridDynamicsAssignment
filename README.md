# Assumptions
The goal is to build the system which will run the queries of specified type (filter by some attributes and count group by (other) attributes) on possibly large input data sets within 1 second.
It was assumed that loading of input data itself (people and their attributes) is not counted in performance target, as the input data may be loaded up front and only once and kept in operating memory for running the queries.

So loading of input data (in this case generation of test data) is NOT included in performance measurements (see @State classes in QueryEvaluatorPerformanceTest).

# Running performance benchmarks
$sbt
> jmh:run -i 5 -wi 5 -f1 -t1 -jvmArgs="-Xmx10g"
Increasing heap size is necessary for input task sizes over 10 millions

# Further possible optimisations