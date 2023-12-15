name := "LibraryManagementSystem"

version := "1.0"

val scala3Version = "3.3.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.3.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.github.tminglei" %% "slick-pg" % "0.20.3",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.20.3",
  "com.typesafe.akka" %% "akka-actor" % "2.6.18"
)
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"
