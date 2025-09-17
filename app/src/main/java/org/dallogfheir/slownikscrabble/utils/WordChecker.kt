package org.dallogfheir.slownikscrabble.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.dallogfheir.slownikscrabble.constants.OspsApiConstants
import org.dallogfheir.slownikscrabble.constants.WordStatus
import org.dallogfheir.slownikscrabble.errors.SlownikScrabbleError

fun tryCheckWord(word: String): WordStatus {
    val client = OkHttpClient()
    val formBody =
        FormBody.Builder().add(OspsApiConstants.S_PARAM_KEY, OspsApiConstants.S_PARAM_VALUE)
            .add(
                OspsApiConstants.WORD_PARAM_KEY, word
            ).build()
    val request = Request.Builder()
        .url(OspsApiConstants.URL)
        .post(formBody)
        .build()
    val response = client.newCall(request).execute()

    val body = response.body?.string()

    if (!response.isSuccessful) {
        val code = response.code
        val errorMessage = "$code $body"

        throw SlownikScrabbleError(errorMessage)
    }

    if (body == OspsApiConstants.INCORRECT_RESPONSE) {
        return WordStatus.INCORRECT
    }
    if (body == OspsApiConstants.CORRECT_RESPONSE) {
        return WordStatus.CORRECT
    }

    throw SlownikScrabbleError(body ?: "")
}

fun checkWord(word: String): WordStatus {
    return try {
        tryCheckWord(word)
    } catch (e: Exception) {
        throw SlownikScrabbleError(e.message ?: "")
    }
}
