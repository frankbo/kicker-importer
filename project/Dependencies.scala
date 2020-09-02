import sbt._

object Dependencies {
  val circeVersion = "0.13.0"
  val Http4sVersion = "0.21.1"

  lazy val http4s = Seq(
    "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
    "org.http4s" %% "http4s-dsl" % Http4sVersion,
    "org.http4s" %% "http4s-circe" % Http4sVersion
  )

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-fs2"
  ).map(_ % circeVersion)

  lazy val scalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.1.1" % Test
  )
  lazy val pureConfig = Seq(
    "com.github.pureconfig" %% "pureconfig" % "0.13.0"
  )

  lazy val dependencies: Seq[ModuleID] =
    scalaTest ++ http4s ++ circe ++ pureConfig
}
