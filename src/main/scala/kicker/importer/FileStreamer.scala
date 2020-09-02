package kicker.importer

import cats.effect.{Blocker, ContextShift, IO}
import kicker.importer.JsonModel.Location
import fs2.{Stream, text, io => fio}
import java.nio.file.Paths

import io.circe.fs2.{decoder, stringArrayParser}

object StreamFromFile {
  def apply(path: String, blocker: Blocker)(implicit
      cs: ContextShift[IO]
  ): Stream[IO, Location] =
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
