package kicker.importer

import cats.effect._
import cats.effect.{ExitCode, IO, IOApp}
import scala.io.{Source, Codec}

object Main extends IOApp {
  def readSourceFile(path: String): Resource[IO, Source] = {
    val acquire = IO(Source.fromFile(path)(Codec.UTF8))

    Resource.fromAutoCloseable(acquire)
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val resourcePath: String = getClass.getResource("/source.json").getPath
    readSourceFile(resourcePath)
      .use(source => IO(println(source.mkString))) //use ~ map on resource
      .unsafeRunSync()
    IO("hello").map(_ => ExitCode.Success)
  }
}
