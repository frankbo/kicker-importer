package kicker.importer.Model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

object JsonModel {
  final case class Locations(location: List[Location])
  object Locations {
    implicit val decode: Decoder[Locations] = deriveDecoder[Locations]
    implicit val encode: Encoder[Locations] = deriveEncoder[Locations]
  }
  final case class Location(
      name: String,
      street: String,
      plz: String,
      city: String,
      loc: Loc,
      connection: String,
      telephone: String,
      fax: String,
      email: String,
      homepage: String,
      openingTimes: String,
      table: String,
      tableStatus: String,
      price: String,
      playingTimes: String,
      skill: String,
      lastVisit: LastVisit,
      id: String,
      other: String
  )
  object Location {
    implicit val decode: Decoder[Location] = deriveDecoder[Location]
    implicit val encode: Encoder[Location] = deriveEncoder[Location]
  }

  final case class LastVisit(month: String, year: String)
  object LastVisit {
    implicit val decode: Decoder[LastVisit] = deriveDecoder[LastVisit]
    implicit val encode: Encoder[LastVisit] = deriveEncoder[LastVisit]
  }

  final case class Loc(lat: String, long: String)
  object Loc {
    implicit val decode: Decoder[Loc] = deriveDecoder[Loc]
    implicit val encode: Encoder[Loc] = deriveEncoder[Loc]
  }
}
