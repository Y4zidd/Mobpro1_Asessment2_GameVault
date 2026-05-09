package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.R
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.component.*
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDb
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore.SettingsDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.model.Game
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.navigation.Screen
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.ViewModelFactory

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = GameDb.getInstance(context)
    val dataStore = SettingsDataStore(context)
    val factory = ViewModelFactory(db.dao, dataStore)
    val viewModel: MainViewModel = viewModel(factory = factory)

    val data by viewModel.data.collectAsState()
    val isListLayout by viewModel.layoutMode.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()

    var selectedPlatform by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    MainTopBarActions(
                        isListLayout = isListLayout,
                        themeMode = themeMode,
                        onLayoutToggle = { viewModel.onAction(MainAction.OnLayoutToggle(!isListLayout)) },
                        onThemeToggle = { 
                            val nextMode = if (themeMode == 2) 1 else 2
                            viewModel.onAction(MainAction.OnThemeChange(nextMode)) 
                        },
                        onAboutClick = { navController.navigate(Screen.About.route) }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Detail.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_game)
                )
            }
        }
    ) { innerPadding ->
        if (data.isEmpty()) {
            EmptyState(modifier = Modifier.padding(innerPadding))
        } else {
            val groupedGames = data.groupBy { it.platform }
            val platforms = groupedGames.keys.toList()
            val displayedGames = if (selectedPlatform == null) data else groupedGames[selectedPlatform] ?: emptyList()

            if (isListLayout) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    item {
                        PlatformAlbumRow(
                            platforms = platforms,
                            groupedGames = groupedGames,
                            selectedPlatform = selectedPlatform,
                            onPlatformSelect = { platform ->
                                selectedPlatform = if (selectedPlatform == platform) null else platform
                            }
                        )
                    }
                    
                    item {
                        Text(
                            text = if (selectedPlatform == null) "All Games" else "Games on $selectedPlatform",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    items(displayedGames) { game ->
                        GameListItem(game = game, onClick = { 
                            navController.navigate(Screen.Detail.route + "?id=${game.id}") 
                        })
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        PlatformAlbumRow(
                            platforms = platforms,
                            groupedGames = groupedGames,
                            selectedPlatform = selectedPlatform,
                            onPlatformSelect = { platform ->
                                selectedPlatform = if (selectedPlatform == platform) null else platform
                            }
                        )
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = if (selectedPlatform == null) "All Games" else "Games on $selectedPlatform",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    items(displayedGames) { game ->
                        GameGridItem(game = game, onClick = { 
                            navController.navigate(Screen.Detail.route + "?id=${game.id}") 
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PlatformAlbumRow(
    platforms: List<String>,
    groupedGames: Map<String, List<Game>>,
    selectedPlatform: String?,
    onPlatformSelect: (String) -> Unit
) {
    Column {
        Text(
            text = "Game Collections",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(platforms) { platform ->
                PlatformAlbumCard(
                    platform = platform,
                    games = groupedGames[platform] ?: emptyList(),
                    isSelected = selectedPlatform == platform,
                    onClick = { onPlatformSelect(platform) }
                )
            }
        }
    }
}
