package org.dallogfheir.slownikscrabble.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import org.dallogfheir.slownikscrabble.R
import org.dallogfheir.slownikscrabble.constants.Constants

@Composable
fun ErrorDialog(message: String, isShown: Boolean, onDismiss: () -> Unit) {
    val title = stringResource(id = R.string.request_error_title)
    val messageFirstPartText = stringResource(id = R.string.request_error_message)
    val detailsText = stringResource(id = R.string.request_error_details)
    val okText = stringResource(id = R.string.ok)

    if (isShown) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = null,
                        modifier = Modifier.padding(end = Constants.errorMessageIconPadding)
                    )
                    Text(title)
                }
            },
            text = {
                Column {
                    Text(
                        messageFirstPartText,
                        modifier = Modifier.padding(bottom = Constants.errorMessagePartPadding)
                    )
                    if (message.isNotEmpty()) {
                        Text(
                            detailsText,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = Constants.errorMessagePartPadding)
                        )
                        Text(message)
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(okText)
                }
            }
        )
    }
}