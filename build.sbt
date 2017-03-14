name := "GridDynamicsAssignment"

version := "2.0"

scalaVersion := "2.11.5"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

libraryDependencies ++= Seq(
  "org.openjdk.jmh" % "jmh-core" % "1.17.5",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalactic" %% "scalactic" % "3.0.0" % "test",
  "org.mockito" % "mockito-core" % "1.10.19" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "com.google.guava" % "guava" % "19.0",
  "org.projectlombok" % "lombok" % "1.16.14"
)

enablePlugins(JmhPlugin)
    