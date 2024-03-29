package com.milenamelo.quickshop

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.setContent

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.milenamelo.quickshop.ui.theme.QuickShopTheme

class MainActivity : ComponentActivity() {
    private lateinit var fbAuthList: FBAuthListener

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = FavoriteMarketViewModel()

        this.fbAuthList = FBAuthListener(this)

        setContent {
            val navController = rememberNavController()
            val showDialog = remember { mutableStateOf(false) }
            val context = LocalContext.current
            val currentRoute = navController.currentBackStackEntryAsState()
            val showButton = currentRoute.value?.destination?.route !=
                    BottomNavItem.MapPage.route
            val launcher = rememberLauncherForActivityResult(contract =
            ActivityResultContracts.RequestPermission(), onResult = {} )

            QuickShopTheme {
                if (showDialog.value) FavMarketDialog(
                    onDismiss = { showDialog.value = false },
                    onConfirm = { market ->
                        if (market.isNotBlank()) viewModel.add(market)
                        showDialog.value = false
                    })
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Bem-vindo/a!") },
                            actions = {
                                IconButton(onClick = {
                                    // Sign out from Firebase authentication
                                    FirebaseAuth.getInstance().signOut()
                                    // Finish the current activity
                                    //finish()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ExitToApp,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    },
                    floatingActionButton = {
                        if (showButton) {
                            FloatingActionButton(onClick = { showDialog.value = true }) {
                                Icon(Icons.Default.Add, contentDescription = "Adicionar")
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        MainNavHost(navController = navController , viewModel = viewModel, context = context)
                    }
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(fbAuthList)
    }
    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(fbAuthList)
    }

}