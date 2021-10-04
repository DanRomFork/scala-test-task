name := "test-task"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.16"
val AkkaHttpVersion = "10.2.6"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6" % Runtime
libraryDependencies += "org.jsoup" % "jsoup" % "1.14.2"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test