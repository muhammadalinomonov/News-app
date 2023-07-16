package uz.gita.newsapp.utils

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import com.google.gson.Gson
import uz.gita.dima.xabarlarqr.data.remote.response.Source
import java.text.ParseException
import java.text.SimpleDateFormat

class DataConverter {
    @TypeConverter
    fun fromDataClass(dataClass: Source): String {
        return Gson().toJson(dataClass)
    }

    @TypeConverter
    fun toDataClass(json: String): Source {
        return Gson().fromJson(json, Source::class.java)
    }
}

@SuppressLint("SimpleDateFormat")
fun String.parseDate(): String {
    return try {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("EEE, MMM dd yyyy")
        val parseDate = input.parse(this)
        parseDate?.let { output.format(it) } ?: String.EMPTY
    } catch (e: ParseException) {
        String.EMPTY
    }
}

val String.Companion.EMPTY: String
    get() = ""
