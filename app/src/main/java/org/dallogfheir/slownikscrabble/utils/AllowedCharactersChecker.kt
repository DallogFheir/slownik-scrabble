package org.dallogfheir.slownikscrabble.utils

import org.dallogfheir.slownikscrabble.constants.Constants


fun containsOnlyAllowedCharacters(word: String): Boolean {
    val wordUppercase = word.uppercase(Constants.polishLocale)

    return wordUppercase.all {
        Constants.ALLOWED_CHARACTERS.contains(it)
    }
}
