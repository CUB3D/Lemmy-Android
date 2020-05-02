package pw.cub3d.lemmy.core.networking.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZonedDateTimeAdapter {
    @FromJson
    fun fromJson(s: String): ZonedDateTime {
        return ZonedDateTime.parse(s)
    }

    @ToJson
    fun toJson(date: ZonedDateTime): String {
        return date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
    }
}