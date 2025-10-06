package org.dallogfheir.slownikscrabble.constants

import androidx.compose.ui.unit.dp
import java.util.Locale

object Constants {
    const val ALLOWED_CHARACTERS = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻ"
    const val PROGRESS_INDICATOR_DELAY_MILLIS = 500L
    const val SJP_URL = "https://sjp.pl/"
    val checkInSJPTextAlpha = 0.7f
    val checkInSJPTextPadding = 32.dp
    val errorMessageIconPadding = 8.dp
    val errorMessagePartPadding = 8.dp
    val mainColumnGap = 32.dp
    val mainRowGap = 64.dp
    val polishLocale: Locale = Locale.Builder().setLanguage("pl").setRegion("PL").build()
    val progressIndicatorStrokeWidth = 16.dp
    val tileSize = 256.dp
}
