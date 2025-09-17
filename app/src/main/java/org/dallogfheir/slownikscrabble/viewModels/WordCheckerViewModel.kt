package org.dallogfheir.slownikscrabble.viewModels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.dallogfheir.slownikscrabble.constants.Constants
import org.dallogfheir.slownikscrabble.constants.WordStatus
import org.dallogfheir.slownikscrabble.errors.SlownikScrabbleError
import org.dallogfheir.slownikscrabble.utils.checkWord
import org.dallogfheir.slownikscrabble.utils.containsOnlyAllowedCharacters

class WordCheckerViewModel : ViewModel() {
    var checkStatus by mutableStateOf(WordStatus.NOT_CHECKED_YET)
        private set
    var errorMessage: String? by mutableStateOf(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var wordToCheck by mutableStateOf("")
        private set
    val wordToCheckUppercase by derivedStateOf { wordToCheck.uppercase(Constants.polishLocale) }
    val shouldButtonsBeEnabled by derivedStateOf { wordToCheck.isNotEmpty() }

    fun updateWordToCheck(newWordToCheck: String) {
        val trimmedWord = newWordToCheck.trim()

        val newWordUppercase = trimmedWord.uppercase(Constants.polishLocale)
        if (!containsOnlyAllowedCharacters(newWordUppercase)) {
            return
        }
        val newWordLowecase = trimmedWord.lowercase(Constants.polishLocale)

        checkStatus = WordStatus.NOT_CHECKED_YET
        wordToCheck = newWordLowecase
    }

    fun clearWordToCheck() {
        updateWordToCheck("")
    }

    fun checkWord() {
        if (wordToCheck.isEmpty()) {
            return
        }

        viewModelScope.launch {
            val loadingJob = launch {
                delay(Constants.PROGRESS_INDICATOR_DELAY_MILLIS)
                isLoading = true
            }

            withContext(Dispatchers.IO) {
                try {
                    checkStatus = checkWord(wordToCheck)
                    errorMessage = null
                } catch (e: SlownikScrabbleError) {
                    checkStatus = WordStatus.NOT_CHECKED_YET
                    errorMessage = e.message
                }
            }
            loadingJob.cancel()
            isLoading = false
        }
    }

    fun dismissErrorMessage() {
        errorMessage = null
    }
}
