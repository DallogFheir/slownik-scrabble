package org.dallogfheir.slownikscrabble.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import org.dallogfheir.slownikscrabble.constants.Constants
import org.dallogfheir.slownikscrabble.ui.components.CheckForm
import org.dallogfheir.slownikscrabble.ui.components.ErrorDialog
import org.dallogfheir.slownikscrabble.viewModels.WordCheckerViewModel

@Composable
fun WordChecker(modifier: Modifier) {
    val viewModel: WordCheckerViewModel = viewModel()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckForm(
                viewModel = viewModel,
                spacerModifier = Modifier.weight(Constants.SPACER_WEIGHT),
                tileModifier = Modifier.weight(Constants.TILE_WEIGHT),
                contentModifier = Modifier.weight(Constants.CONTENT_WEIGHT),
                progressIndicatorModifier = Modifier.fillMaxHeight(Constants.PROGRESS_INDICATOR_WEIGHT)
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CheckForm(
                viewModel = viewModel,
                spacerModifier = Modifier.weight(Constants.SPACER_WEIGHT),
                tileModifier = Modifier.weight(Constants.TILE_WEIGHT),
                contentModifier = Modifier.weight(Constants.CONTENT_WEIGHT),
                progressIndicatorModifier = Modifier.fillMaxWidth(Constants.PROGRESS_INDICATOR_WEIGHT)
            )
        }
    }
    ErrorDialog(
        message = viewModel.errorMessage ?: "",
        isShown = viewModel.errorMessage != null,
        onDismiss = { viewModel.dismissErrorMessage() }
    )
}
