package kicker.importer

import java.util.UUID

import cats.effect.IO
import io.circe.syntax._
import org.http4s.circe._
import kicker.importer.Model.FirebaseModel.{
  EntityWrite,
  Fields,
  GeoPointValue,
  GeoPointValueFields,
  LastVisitFields,
  MapValue,
  MapValueFields,
  RequestBody,
  StringValue,
  Update
}
import kicker.importer.Model.JsonModel.Location
import org.http4s.{Header, Headers, Method, Request, Response}
import org.http4s.client.Client
import org.http4s.implicits.http4sLiteralsSyntax

object FirebaseImporter {
  private val Uri =
    uri"https://firestore.googleapis.com/v1/projects/kicker-app-be070/databases/(default)/documents:batchWrite"

  def apply(
      client: Client[IO],
      token: String,
      location: Location,
      uuid: UUID = UUID.randomUUID()
  ): fs2.Stream[IO, Response[IO]] = {
    val request = Request[IO](
      headers = Headers(List(Header("Authorization", s"Bearer $token"))),
      method = Method.POST,
      uri = Uri
    ).withEntity(requestBodyFromLocation(location, uuid).asJson)

    client.stream(request)
  }

  def requestBodyFromLocation(location: Location, id: UUID): RequestBody = {
    RequestBody(
      List(
        Update(
          EntityWrite(
            name =
              s"projects/kicker-app-be070/databases/(default)/documents/kicker-locations/$id",
            Fields(
              city = StringValue(location.city),
              lastVisit = MapValue(
                MapValueFields(
                  LastVisitFields(
                    StringValue(location.lastVisit.month),
                    StringValue(location.lastVisit.year)
                  )
                )
              ),
              loc = GeoPointValue(
                GeoPointValueFields(
                  location.loc.lat.toDoubleOption.getOrElse(0),
                  location.loc.long.toDoubleOption.getOrElse(0)
                )
              ),
              name = StringValue(location.name),
              plz = StringValue(location.plz),
              street = StringValue(location.street)
            )
          )
        )
      )
    )
  }
}
