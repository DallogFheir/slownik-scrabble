package org.dallogfheir.slownikscrabble.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import org.dallogfheir.slownikscrabble.R
import org.dallogfheir.slownikscrabble.constants.Constants
import org.dallogfheir.slownikscrabble.viewModels.WordCheckerViewModel

@Composable
fun CheckForm(
    viewModel: WordCheckerViewModel,
    spacerModifier: Modifier,
    tileModifier: Modifier,
    contentModifier: Modifier,
    progressIndicatorModifier: Modifier
) {
    val clearText = stringResource(id = R.string.icon_clear)
    val checkText = stringResource(id = R.string.button_check).uppercase(Constants.polishLocale)
    val checkInSjpText =
        stringResource(id = R.string.info_check_in_sjp)

    if (viewModel.isLoading) {
        Box(modifier = contentModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = progressIndicatorModifier.aspectRatio(Constants.PROGRESS_INDICATOR_ASPECT_RATIO),
                strokeWidth = Constants.progressIndicatorStrokeWidthMultiplier * Constants.PROGRESS_INDICATOR_WEIGHT
            )
        }
    } else {
        Spacer(modifier = spacerModifier)

        Tile(modifier = tileModifier, viewModel = viewModel)

        Column(
            modifier = contentModifier.fillMaxHeight(Constants.CONTENT_MAX_SIZE_WEIGHT),
            verticalArrangement = Arrangement.spacedBy(
                Constants.mainColumnGap,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.wordToCheckUppercase,
                onValueChange = {
                    viewModel.updateWordToCheck(it)
                },
                trailingIcon = {
                    if (viewModel.wordToCheckUppercase.isNotEmpty()) {
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

            BasicText(
                modifier = Modifier
                    .alpha(Constants.CHECK_IN_SJP_TEXT_ALPHA),
                text = checkInSjpText,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = Constants.CHECK_IN_SJP_TEXT_ALPHA),
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(maxFontSize = Constants.checkInSjpTextFontSize)
            )
        }

        Spacer(modifier = spacerModifier)
    }
}
