lazy val root = (project in file(".")).settings(
  name := "hello",
  version := "1.0",
  scalaVersion := "2.11.8",
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.4.16",
    "com.typesafe.akka" %% "akka-http" % "10.0.1",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1",
    "com.github.levkhomich" %% "akka-tracing-core"  % "0.6-SNAPSHOT" changing(),
    "com.github.levkhomich" %% "akka-tracing-http"  % "0.6-SNAPSHOT" changing()
  )
)
