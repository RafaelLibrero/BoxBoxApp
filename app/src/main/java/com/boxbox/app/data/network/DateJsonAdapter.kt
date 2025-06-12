package com.boxbox.app.data.network

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter : JsonDeserializer<Date> {
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date {
        return try {
            formatter.parse(json.asString) ?: throw JsonParseException("Invalid date format")
        } catch (e: Exception) {
            throw JsonParseException("Failed to parse Date", e)
        }
    }
}