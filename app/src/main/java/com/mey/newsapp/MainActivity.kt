package com.mey.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.mey.newsapp.domain.usecases.AppEntryUseCases
import com.mey.newsapp.presentation.onboarding.OnBoardingScreen
import com.mey.newsapp.presentation.onboarding.OnBoardingViewModel
import com.mey.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var useCases: AppEntryUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {
            useCases.readAppEntry().collect{
                println("test, ${it.toString()}")
            }
        }

        setContent {
            NewsAppTheme {
                Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
                    val viewModel: OnBoardingViewModel =  hiltViewModel()
                    OnBoardingScreen(
                        event = viewModel::onEvent
                    )
                }
            }
        }
    }
}

