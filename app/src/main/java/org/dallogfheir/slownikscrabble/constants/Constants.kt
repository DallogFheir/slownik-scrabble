package org.dallogfheir.slownikscrabble.constants

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

object Constants {
    const val ALLOWED_CHARACTERS = "AĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻ"
    const val CHECK_IN_SJP_TEXT_ALPHA = 0.7f
    const val CONTENT_MAX_SIZE_WEIGHT = 0.8f
    const val CONTENT_WEIGHT = 8f
    const val PROGRESS_INDICATOR_ASPECT_RATIO = 1f
    const val PROGRESS_INDICATOR_DELAY_MILLIS = 500L
    const val PROGRESS_INDICATOR_WEIGHT = 0.6f
    const val SJP_URL = "https://sjp.pl/"
    const val SPACER_WEIGHT = 1f
    const val TILE_WEIGHT = 8f
    val checkInSjpTextFontSize = 16.sp
    val errorMessageIconPadding = 8.dp
    val errorMessagePartPadding = 8.dp
    val mainColumnGap = 32.dp
    val polishLocale: Locale = Locale.Builder().setLanguage("pl").setRegion("PL").build()
    val progressIndicatorStrokeWidthMultiplier = 32.dp
}
