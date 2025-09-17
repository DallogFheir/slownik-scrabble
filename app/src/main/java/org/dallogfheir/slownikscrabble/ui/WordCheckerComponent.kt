package org.dallogfheir.slownikscrabble.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import org.dallogfheir.slownikscrabble.R
import org.dallogfheir.slownikscrabble.constants.Constants
import org.dallogfheir.slownikscrabble.ui.components.ErrorDialog
import org.dallogfheir.slownikscrabble.ui.components.Tile
import org.dallogfheir.slownikscrabble.utils.openWordInSjpInContext
import org.dallogfheir.slownikscrabble.viewModels.WordCheckerViewModel

@Composable
fun WordChecker(modifier: Modifier) {
    val viewModel: WordCheckerViewModel = viewModel()

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val wordToCheck = viewModel.wordToCheck
    val wordToCheckUppercase = viewModel.wordToCheckUppercase

    val clearText = stringResource(id = R.string.icon_clear)
    val checkText = stringResource(id = R.string.button_check).uppercase(Constants.polishLocale)
    val checkInSjpText =
        stringResource(id = R.string.button_check_in_sjp).uppercase(Constants.polishLocale)

    val content: @Composable () -> Unit = {
        if (viewModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(Constants.tileSize),
                strokeWidth = Constants.progressIndicatorStrokeWidth
            )
        } else {
            Tile(viewModel.checkStatus)

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    Constants.mainColumnGap,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = wordToCheckUppercase,
                    onValueChange = {
                        viewModel.updateWordToCheck(it)
                    },
                    trailingIcon = {
                        if (wordToCheckUppercase.isNotEmpty()) {
                            IconButton(onClick = { viewModel.clearWordToCheck() }) {
                                Icon(Icons.Filled.Clear, contentDescription = clearText)
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.checkWord()
                        }
                    )
                )
                Button(
                    onClick = { viewModel.checkWord() },
                    enabled = viewModel.shouldButtonsBeEnabled
                ) {
                    Text(checkText)
                }
                Button(
                    onClick = {
                        openWordInSjpInContext(wordToCheck, context)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(),
                    enabled = viewModel.shouldButtonsBeEnabled
                ) {
                    Icon(
                        Icons.Filled.OpenInBrowser,
                        contentDescription = null,
                        modifier = Modifier.padding(end = Constants.checkInSJPIconMargin)
                    )
                    Text(checkInSjpText)
                }
            }
        }
    }

    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                Constants.mainRowGap,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                Constants.mainColumnGap,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
    ErrorDialog(
        message = viewModel.errorMessage ?: "",
        isShown = viewModel.errorMessage != null,
        onDismiss = { viewModel.dismissErrorMessage() }
    )
}
