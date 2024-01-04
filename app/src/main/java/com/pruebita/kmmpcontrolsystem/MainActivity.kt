package com.pruebita.kmmpcontrolsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pruebita.kmmpcontrolsystem.ui.screens.MainPanelScreen
import com.pruebita.kmmpcontrolsystem.ui.theme.KmmpControlSystemTheme
import com.pruebita.kmmpcontrolsystem.viewmodel.MainPanelViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmmpControlSystemTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: MainPanelViewModel = MainPanelViewModel()
                    MainPanelScreen(navController = navController, viewModel)
                }
            }
        }
    }
}
