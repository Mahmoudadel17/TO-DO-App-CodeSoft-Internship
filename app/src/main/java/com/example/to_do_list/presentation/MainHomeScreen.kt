package com.example.to_do_list.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.example.to_do_list.presentation.navigation.NavigationScreens
import com.example.to_do_list.presentation.navigation.BottomNav
import com.example.to_do_list.presentation.tasksPreview.BottomSheetViewModel
import com.example.to_do_list.presentation.tasksPreview.TasksScreenViewModel
import com.example.to_do_list.ui.theme.floatingActionButton
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavHostController
import com.example.to_do_list.presentation.components.BottomSheetModel
import com.example.to_do_list.presentation.components.SplashScreen
import com.example.to_do_list.presentation.navigation.Screens

import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appNavController: NavHostController,
    tasksScreenViewModel: TasksScreenViewModel,
    bottomSheetViewModel: BottomSheetViewModel
) {

    var doubleBackToExitPressedOnce = false
    val showBottomSheet by bottomSheetViewModel.showBottomSheet.collectAsState()

    val activity = LocalOnBackPressedDispatcherOwner.current as ComponentActivity

    val context = LocalContext.current
    val navController = rememberNavController()
    val items = listOf(
        NavigationScreens.ToDay,
        NavigationScreens.AllTasks,
        NavigationScreens.Favorite,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationBarItems = remember {NavigationBarItems.values()}
    var selectedIndex by remember { mutableStateOf(0) }
    if(currentRoute != Screens.AppNavigation.route){
        if (currentRoute== NavigationScreens.AllTasks.route){
            selectedIndex = 1

        }else if  (currentRoute== NavigationScreens.Favorite.route){
            selectedIndex = 2
        }
    }



    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex,
                modifier = Modifier.height(64.dp),
                cornerRadius = shapeCornerRadius(cornerRadius = 34.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = MaterialTheme.colorScheme.primary,
                ballColor = MaterialTheme.colorScheme.primary

            ) {
                navigationBarItems.forEach {item->
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                selectedIndex = item.ordinal
                                navController.navigate(items[item.ordinal].route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ){
                       Row (verticalAlignment = Alignment.CenterVertically){
                           Icon(
                               modifier = Modifier.size(26.dp),
                               imageVector = item.icon,
                               contentDescription = "Bottom Bar Icon",
                               tint = if (selectedIndex == item.ordinal) floatingActionButton
                               else MaterialTheme.colorScheme.background
                           )
                           Spacer(modifier = Modifier.width(5.dp))
                           AnimatedVisibility(selectedIndex == item.ordinal){
                               Text(
                                   text = item.title,
                                   color = floatingActionButton,
                                   modifier = if(item.title == "Favorites"){Modifier.padding(end = 10.dp)}
                                   else {Modifier.padding(end = 0.dp)}
                               )
                           }

                       }

                    }

                }

            }
        },

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    var title = NavigationScreens.ToDay.title
                    items.forEach {screen ->
                        if (currentRoute == screen.route){
                            title = screen.title
                        }
                    }
                    Text(
                        text = title,
                        fontSize = 35.sp,
                        color = floatingActionButton,
                    ) },
            )
        },

        floatingActionButton = {
            FloatingActionButton(containerColor = floatingActionButton,onClick = {
                bottomSheetViewModel.onShowBottomSheet()
            }) {
                Icon(imageVector = Icons.Filled.Add,tint = MaterialTheme.colorScheme.background, contentDescription = "Add")
            }

        }

    ) {
        Box(modifier = Modifier.padding(it)){
            BottomNav(navController,appNavController,tasksScreenViewModel)
        }
        val title by bottomSheetViewModel.title.collectAsState()
        val content by bottomSheetViewModel.content.collectAsState()
        val isErrorTitle by bottomSheetViewModel.isErrorTitle.collectAsState()
        val isErrorContent by bottomSheetViewModel.isErrorContent.collectAsState()
        val titleErrorMessage by bottomSheetViewModel.titleErrorMessage.collectAsState()
        val contentErrorMessage by bottomSheetViewModel.contentErrorMessage.collectAsState()
        val dateTimeErrorMessage by bottomSheetViewModel.dateTimeErrorMessage.collectAsState()

        if (showBottomSheet){
            BottomSheetModel(
                title = title,
                isTitleError = isErrorTitle,
                titleMessageError = titleErrorMessage,
                content = content,
                isContentError = isErrorContent,
                contentMessageError = contentErrorMessage,
                dateTimeErrorMessage = dateTimeErrorMessage,
                onDismissRequest = {bottomSheetViewModel.onDismissRequest()},
                onTitleChange = {newTitle ->bottomSheetViewModel.onTitleChange(title = newTitle)},
                onContentChange = {newContent->bottomSheetViewModel.onContentChange(newContent)},
                onDateSelect = {year,month,day->bottomSheetViewModel.onDateSelect(year,month,day)},
                onTimeSelect = {hour,minute->bottomSheetViewModel.onTimeSelect(hour,minute)},
                onAddRequest = {bottomSheetViewModel.onFloatingActionButtonAdd()}
            )
        }



        BackHandler(onBack = {
            // Handle the back button press here
            // Navigate to a specific screen using navController.navigate(...)

            if(currentRoute == items[0].route){
                if (doubleBackToExitPressedOnce) {
                    finishAffinity(activity) //This will exit the app
                } else {
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                    // Reset the flag after 2 seconds
                }
            }else{
                selectedIndex = 0
                navController.navigate(items[0].route){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

        })


    }
}


enum class NavigationBarItems(val icon:ImageVector,val title:String){
    ToDay(icon = Icons.Default.Home,"To Day"),
    AllTasks(icon = Icons.Default.List,"All Tasks"),
    Favorite(icon = Icons.Default.Favorite,"Favorites")
}

