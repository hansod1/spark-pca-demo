organization := "com.hansod1"

name := "spark-pca-demo"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

publishTo := Some( "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.11.8",
  "org.apache.spark" %% "spark-core" % "2.0.2" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.0.2" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.0.2" % "provided",
  "org.apache.spark" %% "spark-hive" % "2.0.2" % "provided",
  "org.jsoup" % "jsoup" % "1.10.2"
)
