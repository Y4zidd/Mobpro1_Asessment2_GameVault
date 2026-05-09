package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
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
    themeMode: Int,
    onLayoutToggle: () -> Unit,
    onThemeToggle: () -> Unit,
    onAboutClick: () -> Unit
) {
    // Tombol Toggle Layout (List/Grid)
    IconButton(onClick = onLayoutToggle) {
        Icon(
            painter = painterResource(
                id = if (isListLayout) R.drawable.ic_grid else R.drawable.ic_list
            ),
            contentDescription = "Toggle Layout"
        )
    }

    // Tombol Toggle Tema (Matahari / Bulan)
    IconButton(onClick = onThemeToggle) {
        Icon(
            imageVector = if (themeMode == 2) Icons.Default.WbSunny else Icons.Default.NightsStay,
            contentDescription = "Toggle Theme",
            tint = if (themeMode == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }

    // Tombol About
    IconButton(onClick = onAboutClick) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "About Developer"
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
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete)) },
                onClick = {
                    showMenu = false
                    onDeleteClick()
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            )
        }
    }
}
