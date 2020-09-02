package kicker.importer.Model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

object FirebaseModel {
  final case class RequestBody(writes: List[Update])
  object RequestBody {
    implicit val decode: Decoder[RequestBody] = deriveDecoder[RequestBody]
    implicit val encoder: Encoder[RequestBody] = deriveEncoder[RequestBody]
  }
  final case class Update(update: EntityWrite)
  object Update {
    implicit val decode: Decoder[Update] = deriveDecoder[Update]
    implicit val encoder: Encoder[Update] = deriveEncoder[Update]
  }
  final case class EntityWrite(name: String, fields: Fields)
  object EntityWrite {
    implicit val decode: Decoder[EntityWrite] = deriveDecoder[EntityWrite]
    implicit val encoder: Encoder[EntityWrite] = deriveEncoder[EntityWrite]
  }
  final case class Fields(
      city: StringValue,
      lastVisit: MapValue,
      loc: GeoPointValue,
      name: StringValue,
      plz: StringValue,
      street: StringValue
  )
  object Fields {
    implicit val decode: Decoder[Fields] = deriveDecoder[Fields]
    implicit val encoder: Encoder[Fields] = deriveEncoder[Fields]
  }
  final case class GeoPointValue(geoPointValue: GeoPointValueFields)
  object GeoPointValue {
    implicit val decode: Decoder[GeoPointValue] = deriveDecoder[GeoPointValue]
    implicit val encoder: Encoder[GeoPointValue] = deriveEncoder[GeoPointValue]
  }

  final case class GeoPointValueFields(latitude: Double, longitude: Double)
  object GeoPointValueFields {
    implicit val decode: Decoder[GeoPointValueFields] =
      deriveDecoder[GeoPointValueFields]
    implicit val encoder: Encoder[GeoPointValueFields] =
      deriveEncoder[GeoPointValueFields]
  }

  final case class MapValue(mapValue: MapValueFields)
  object MapValue {
    implicit val decode: Decoder[MapValue] = deriveDecoder[MapValue]
    implicit val encoder: Encoder[MapValue] = deriveEncoder[MapValue]
  }
  final case class MapValueFields(fields: LastVisitFields)
  object MapValueFields {
    implicit val decode: Decoder[MapValueFields] = deriveDecoder[MapValueFields]
    implicit val encoder: Encoder[MapValueFields] =
      deriveEncoder[MapValueFields]
  }

  final case class LastVisitFields(month: StringValue, year: StringValue)
  object LastVisitFields {
    implicit val decode: Decoder[LastVisitFields] =
      deriveDecoder[LastVisitFields]
    implicit val encoder: Encoder[LastVisitFields] =
      deriveEncoder[LastVisitFields]
  }
  final case class StringValue(stringValue: String)
  object StringValue {
    implicit val decode: Decoder[StringValue] = deriveDecoder[StringValue]
    implicit val encoder: Encoder[StringValue] = deriveEncoder[StringValue]
  }

}
