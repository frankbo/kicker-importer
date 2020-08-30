package kicker.importer

import java.nio.file.Paths

import cats.effect.{ExitCode, IO, IOApp, _}
import cats.implicits._
import fs2.{Stream, text, io => fio}
import io.circe.fs2.{decoder, stringArrayParser}
import kicker.importer.Model.Location
import org.http4s.client.Client
import org.http4s.client.blaze._
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{Method, Request, Response, UrlForm}

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {
  def streamSourceFile(path: String, blocker: Blocker): Stream[IO, Location] = {
    fio.file
      .readAll[IO](
        Paths.get(path),
        blocker,
        512
      )
      .through(text.utf8Decode)
      .through(stringArrayParser)
      .through(decoder[IO, Location])
  }

  def importDatabase(
      location: Location,
      client: Client[IO]
  ): Stream[IO, Response[IO]] = {
    val request = Request[IO](
      method = Method.POST,
      uri = uri"https://hookb.in/RZjJOdjxQVFREEj72Lpn"
    ).withEntity(
      UrlForm(
        "name" -> location.name
      )
    )
    client.stream(request)
  }

  override def run(args: List[String]): IO[ExitCode] = {
    (for {
      blocker <- Stream.resource(Blocker[IO])
      client <- BlazeClientBuilder[IO](global).stream
      location <-
        streamSourceFile(getClass.getResource("/source.json").getPath, blocker)
      response <- importDatabase(location, client)
    } yield response).compile.drain.as(ExitCode.Success)
  }
}
