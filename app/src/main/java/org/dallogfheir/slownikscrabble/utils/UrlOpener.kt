package org.dallogfheir.slownikscrabble.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import org.dallogfheir.slownikscrabble.constants.Constants

fun openWordInSjpInContext(word: String, context: Context) {
    val url = Constants.SJP_URL + word
    val parsedUrl = url.toUri()
    val intent = Intent(Intent.ACTION_VIEW, parsedUrl)
    context.startActivity(intent)
}
