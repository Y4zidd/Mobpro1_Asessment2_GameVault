package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.detail

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.R
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.AppTopBar
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.DeleteDialog
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.DetailTopBarActions
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.FormGame
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDb
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore.SettingsDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.ImageStorage
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.ViewModelFactory

@Composable
fun DetailScreen(navController: NavHostController, id: Long = -1L) {
    val context = LocalContext.current
    val db = GameDb.getInstance(context)
    val dataStore = SettingsDataStore(context)
    val factory = ViewModelFactory(db.dao, dataStore)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    val state by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.loadGame(id)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = if (state.isEdit) R.string.edit_game else R.string.add_game),
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                actions = {
                    DetailTopBarActions(
                        isEdit = state.isEdit,
                        onDeleteClick = { showDeleteDialog = true }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image Preview
            if (state.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            FormGame(
                title = state.title,
                onTitleChange = { viewModel.onAction(DetailAction.OnTitleChange(it)) },
                platform = state.platform,
                onPlatformChange = { viewModel.onAction(DetailAction.OnPlatformChange(it)) },
                rating = state.rating,
                onRatingChange = { viewModel.onAction(DetailAction.OnRatingChange(it)) },
                playTime = state.playTime,
                onPlayTimeChange = { viewModel.onAction(DetailAction.OnPlayTimeChange(it)) },
                finished = state.finished,
                onFinishedChange = { viewModel.onAction(DetailAction.OnFinishedChange(it)) },
                imageUrl = state.imageUrl,
                onImageUrlChange = { uriString ->
                    val uri = Uri.parse(uriString)
                    val savedPath = ImageStorage.saveToInternalStorage(context, uri)
                    if (savedPath != null) {
                        viewModel.onAction(DetailAction.OnImageUrlChange(savedPath))
                    }
                }
            )

            Button(
                onClick = {
                    if (state.title.isBlank() || state.platform.isBlank()) {
                        Toast.makeText(context, R.string.validation_error, Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.onAction(DetailAction.OnSave)
                        Toast.makeText(context, R.string.success_save, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }

    DeleteDialog(
        openDialog = showDeleteDialog,
        onDismissRequest = { showDeleteDialog = false },
        onConfirm = {
            viewModel.onAction(DetailAction.OnDelete)
            showDeleteDialog = false
            Toast.makeText(context, R.string.success_delete, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    )
}
