package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.cancel)
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun MainTopBarActions(
    isListLayout: Boolean,
    onLayoutToggle: () -> Unit,
    onThemeClick: () -> Unit,
    onTrashClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    IconButton(onClick = onLayoutToggle) {
        Icon(
            painter = painterResource(
                id = if (isListLayout) R.drawable.ic_grid else R.drawable.ic_list
            ),
            contentDescription = "Toggle Layout"
        )
    }

    IconButton(onClick = onThemeClick) {
        Icon(
            imageVector = Icons.Default.Palette,
            contentDescription = "Pilih Tema"
        )
    }

    IconButton(onClick = onTrashClick) {
        Icon(imageVector = Icons.Default.RestoreFromTrash, contentDescription = "Recycle Bin")
    }

    IconButton(onClick = onAboutClick) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "About"
        )
    }
}

@Composable
fun DetailTopBarActions(
    isEdit: Boolean,
    onDeleteClick: () -> Unit
) {
    if (isEdit) {
        var showMenu by remember { mutableStateOf(false) }
        IconButton(onClick = { showMenu = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete)) },
                onClick = {
                    showMenu = false
                    onDeleteClick()
                },
                leadingIcon = { Icon(imageVector = Icons.Default.Delete, contentDescription = null) }
            )
        }
    }
}
