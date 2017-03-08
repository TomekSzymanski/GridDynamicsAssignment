name := "GridDynamicsAssignment"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.openjdk.jmh" % "jmh-core" % "1.17.5",
"org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalactic" %% "scalactic" % "3.0.0" % "test",
  "org.mockito" % "mockito-core" % "1.10.19" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

lazy val root = (project in file(".")).
  enablePlugins(JmhPlugin)
    