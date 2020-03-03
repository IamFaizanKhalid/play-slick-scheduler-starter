name := """play-slick-scheduler-starter"""
organization := "com.datumbrain"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
//libraryDependencies += "org.typesafe.play" %% "play" % "2.8.1"
//libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.29"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.datumbrain.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.datumbrain.binders._"

// set the main class for 'sbt run'
//mainClass in (Compile, run) := Some("com.datumbrain.WebFetcher")