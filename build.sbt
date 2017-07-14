name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies += javaJpa
libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"
libraryDependencies += "org.mariadb.jdbc" % "mariadb-java-client" % "1.5.7"
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.160"
libraryDependencies += "net.sf.supercsv" % "super-csv" % "2.3.1"