package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.R

@Composable
fun DeleteDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = stringResource(id = R.string.delete)) },
            text = { Text(text = stringResource(id = R.string.delete_confirm)) },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}
