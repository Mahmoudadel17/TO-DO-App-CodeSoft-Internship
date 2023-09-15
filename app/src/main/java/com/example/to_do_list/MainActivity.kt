package com.example.to_do_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.to_do_list.presentation.components.SplashScreen
import com.example.to_do_list.presentation.navigation.AppNavigate
import com.example.to_do_list.presentation.tasksPreview.BottomSheetViewModel
import com.example.to_do_list.presentation.tasksPreview.UpdateTaskViewModel
import com.example.to_do_list.presentation.tasksPreview.TasksScreenViewModel
import com.example.to_do_list.ui.theme.TODOListTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tasksScreenViewModel by viewModels<TasksScreenViewModel>()
    private val bottomSheetViewModel by viewModels<BottomSheetViewModel>()
    private val taskPreviewViewModel by viewModels<UpdateTaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isSplashScreenVisible by remember { mutableStateOf(true) }
                    if (isSplashScreenVisible) {
                        SplashScreen()
                    } else {

                        AppNavigate(tasksScreenViewModel, bottomSheetViewModel, taskPreviewViewModel)
                    }
                    LaunchedEffect(Unit) {
                        delay(4.seconds)
                        isSplashScreenVisible = false
                    }
                }
            }
        }
    }
}

