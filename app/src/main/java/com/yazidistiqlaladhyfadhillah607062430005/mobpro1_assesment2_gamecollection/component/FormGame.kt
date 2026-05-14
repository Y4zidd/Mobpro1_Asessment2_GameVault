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
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormGame(
    title: String,
    onTitleChange: (String) -> Unit,
    platform: String,
    onPlatformChange: (String) -> Unit,
    categoryId: Long,
    onCategoryChange: (Long) -> Unit,
    categories: List<Category>,
    customCategoryName: String,
    onCustomCategoryChange: (String) -> Unit,
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
    var platformExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val selectedCategory = categories.find { it.categoryId == categoryId }
    val selectedCategoryName = selectedCategory?.name ?: "Pilih Kategori"
    val isOtherSelected = selectedCategory?.name == "Other"

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

        Column {
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedCategoryName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                onCategoryChange(category.categoryId)
                                categoryExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            if (isOtherSelected) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = customCategoryName,
                    onValueChange = onCustomCategoryChange,
                    label = { Text("Nama Kategori Baru") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Masukkan genre game...") }
                )
            }
        }

        ExposedDropdownMenuBox(
            expanded = platformExpanded,
            onExpandedChange = { platformExpanded = !platformExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = platform,
                onValueChange = {},
                readOnly = true,
                label = { Text("Choose Platform") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = platformExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = platformExpanded,
                onDismissRequest = { platformExpanded = false }
            ) {
                platforms.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onPlatformChange(selectionOption)
                            platformExpanded = false
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
