package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.trash

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.R
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.AppTopBar
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.DeleteDialog
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDb
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore.SettingsDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main.MainAction
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main.MainViewModel
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.ViewModelFactory

@Composable
fun TrashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = GameDb.getInstance(context)
    val dataStore = SettingsDataStore(context)
    val factory = ViewModelFactory(db.dao, db.categoryDao, dataStore)
    val viewModel: MainViewModel = viewModel(factory = factory)

    val deletedGames by viewModel.deletedData.collectAsState()
    var gameToDeleteId by remember { mutableLongStateOf(-1L) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Recycle Bin",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        if (deletedGames.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Recycle Bin is empty")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(deletedGames) { game ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = game.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = game.platform,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Row {
                                IconButton(onClick = { 
                                    viewModel.onAction(MainAction.OnRestoreGame(game.id))
                                    Toast.makeText(context, "Game restored", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Restore,
                                        contentDescription = "Restore",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = { 
                                    gameToDeleteId = game.id
                                    showDeleteDialog = true 
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteForever,
                                        contentDescription = "Delete Permanently",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    DeleteDialog(
        openDialog = showDeleteDialog,
        onDismissRequest = { showDeleteDialog = false },
        onConfirm = {
            if (gameToDeleteId != -1L) {
                viewModel.onAction(MainAction.OnDeletePermanently(gameToDeleteId))
                showDeleteDialog = false
                gameToDeleteId = -1L
                Toast.makeText(context, R.string.success_delete, Toast.LENGTH_SHORT).show()
            }
        }
    )
}
