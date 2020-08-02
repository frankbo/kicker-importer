import sbt._

object Dependencies {
  private val circeVersion = "0.13.0"
  private val Http4sVersion = "0.26.0"

  private lazy val http4s = Seq(
    "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
    "org.http4s" %% "http4s-dsl" % Http4sVersion,
    "org.http4s" %% "http4s-circe" % Http4sVersion
  )

  private lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)

  private lazy val scalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.1.1" % Test
  )

  lazy val dependencies: Seq[ModuleID] = scalaTest ++ circe ++ http4s
}
