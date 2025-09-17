package org.dallogfheir.slownikscrabble.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.dallogfheir.slownikscrabble.R
import org.dallogfheir.slownikscrabble.constants.Constants
import org.dallogfheir.slownikscrabble.constants.TileConstants
import org.dallogfheir.slownikscrabble.constants.WordStatus

@Composable
fun Tile(wordStatus: WordStatus) {
    val (stringId, tileId, tintColor) = when (wordStatus) {
        WordStatus.NOT_CHECKED_YET -> Triple(
            R.string.word_not_checked_yet,
            R.drawable.tile_not_checked_yet,
            if (isSystemInDarkTheme()) TileConstants.notCheckedYetDarkTheme else TileConstants.notCheckedYetLightTheme
        )

        WordStatus.CORRECT -> Triple(
            R.string.word_correct,
            R.drawable.tile_correct,
            if (isSystemInDarkTheme()) TileConstants.correctDarkTheme else TileConstants.correctLightTheme
        )

        WordStatus.INCORRECT -> Triple(
            R.string.word_incorrect,
            R.drawable.tile_incorrect,
            if (isSystemInDarkTheme()) TileConstants.incorrectDarkTheme else TileConstants.incorrectLightTheme
        )
    }
    val text = stringResource(id = stringId)

    Image(
        painter = painterResource(id = tileId),
        contentDescription = text,
        colorFilter = ColorFilter.tint(tintColor),
        modifier = Modifier
            .size(Constants.tileSize)

    )
}
