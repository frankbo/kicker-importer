package kicker.importer

import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp, _}
import fs2.Stream
import kicker.importer.Model.ServiceConf
import org.http4s.client.blaze._
import pureconfig._
import pureconfig.generic.auto._

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends IOApp {
  val config: ServiceConf = ConfigSource.default.loadOrThrow[ServiceConf]

  override def run(args: List[String]): IO[ExitCode] =
    (for {
      blocker <- Stream.resource(Blocker[IO])
      client <- BlazeClientBuilder[IO](global).stream
      filePath = getClass.getResource("/source.json").getPath
      location <- FileStreamer(filePath, blocker)
      response <- FirebaseImporter(client, config.token, location)
      _ = println(response.status)
    } yield response).compile.drain.as(ExitCode.Success)
}
