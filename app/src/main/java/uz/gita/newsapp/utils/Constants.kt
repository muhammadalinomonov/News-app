package uz.gita.newsapp.utils

import androidx.compose.ui.unit.dp
import timber.log.Timber


fun log(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}

val ROUNDED_CORNER = 12.dp
val HORIZONTAL_MARGIN_STD = 12.dp
val VERTICAL_MARGIN_STD = 12.dp

val languageList = listOf("in", "us", "au", "ru", "fr", "gb")