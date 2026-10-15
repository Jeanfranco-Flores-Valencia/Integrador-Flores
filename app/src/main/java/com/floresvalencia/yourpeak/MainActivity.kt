package com.floresvalencia.yourpeak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.floresvalencia.yourpeak.navigation.AppNavigation
import com.floresvalencia.yourpeak.ui.theme.YourPeakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourPeakTheme {
                AppNavigation()
            }
        }
    }
}
