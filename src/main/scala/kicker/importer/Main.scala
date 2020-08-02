package kicker.importer

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = IO("hello").map(_ => ExitCode.Success)


}
