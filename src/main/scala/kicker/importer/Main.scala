package kicker.importer

import cats.effect.{ExitCode, IO, IOApp, _}
import cats.implicits._
import fs2.Stream
import org.http4s.client.blaze._

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {
  val token =
    "some token"

  override def run(args: List[String]): IO[ExitCode] = {
    (for {
      blocker <- Stream.resource(Blocker[IO])
      client <- BlazeClientBuilder[IO](global).stream
      filePath = getClass.getResource("/source.json").getPath
      location <- FileStreamer(filePath, blocker)
      response <- FirebaseImporter(client, token, location)
      _ = println(response.status)
    } yield response).compile.drain.as(ExitCode.Success)
  }
}
