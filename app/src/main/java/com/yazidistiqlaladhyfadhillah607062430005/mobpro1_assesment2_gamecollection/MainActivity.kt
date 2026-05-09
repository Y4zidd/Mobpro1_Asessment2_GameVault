package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.database.GameDb
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.datastore.SettingsDataStore
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.navigation.SetupNavGraph
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.screen.main.MainViewModel
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.ui.theme.Mobpro1_Assesment2_GameCollectionTheme
import com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val db = GameDb.getInstance(context)
            val dataStore = SettingsDataStore(context)
            val factory = ViewModelFactory(db.dao, dataStore)
            val mainViewModel: MainViewModel = viewModel(factory = factory)
            
            val themeMode by mainViewModel.themeMode.collectAsState()

            Mobpro1_Assesment2_GameCollectionTheme(themeMode = themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}
