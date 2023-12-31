ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name         := "min-triangle-path",
    organization := "com.github.dmytromitin",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core"           % "2.10.0",
      "org.typelevel" %% "cats-effect"         % "3.5.2",
      "org.scalameta" %% "munit"               % "0.7.29" % Test,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"  % Test,
    ),
    assembly / assemblyJarName := "MinTrianglePath",
  )
