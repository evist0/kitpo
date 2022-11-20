ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "lab-2",
  )

scalacOptions += "-Ymacro-annotations"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "19.0.0-R30",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"
)

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true