package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormGame(
    title: String,
    onTitleChange: (String) -> Unit,
    platform: String,
    onPlatformChange: (String) -> Unit,
    rating: Float,
    onRatingChange: (Float) -> Unit,
    playTime: String,
    onPlayTimeChange: (String) -> Unit,
    finished: Boolean,
    onFinishedChange: (Boolean) -> Unit,
    imageUrl: String,
    onImageUrlChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageUrlChange(it.toString()) }
    }

    val platforms = listOf("PC", "PlayStation 5", "PlayStation 4", "Xbox Series X/S", "Nintendo Switch", "Android / iOS", "Other")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(stringResource(id = R.string.title)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        // Dropdown Platform
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = platform,
                onValueChange = {},
                readOnly = true,
                label = { Text("Choose Platform") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                platforms.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onPlatformChange(selectionOption)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        OutlinedTextField(
            value = playTime,
            onValueChange = { if (it.all { char -> char.isDigit() }) onPlayTimeChange(it) },
            label = { Text(stringResource(id = R.string.play_time)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        OutlinedButton(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (imageUrl.isBlank()) "Pilih Banner dari Galeri" else "Ganti Banner")
        }

        Column {
            Text(
                text = stringResource(id = R.string.rating, rating.toInt()),
                style = MaterialTheme.typography.bodyMedium
            )
            Slider(
                value = rating,
                onValueChange = onRatingChange,
                valueRange = 1f..5f,
                steps = 3
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(id = R.string.finished))
            Switch(
                checked = finished,
                onCheckedChange = onFinishedChange
            )
        }
    }
}
