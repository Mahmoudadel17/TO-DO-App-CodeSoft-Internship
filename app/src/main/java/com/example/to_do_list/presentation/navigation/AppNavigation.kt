package com.example.to_do_list.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.to_do_list.presentation.HomeScreen
import com.example.to_do_list.presentation.components.UpdateTaskScreen
import com.example.to_do_list.presentation.tasksPreview.AllTasksScreen
import com.example.to_do_list.presentation.tasksPreview.BottomSheetViewModel
import com.example.to_do_list.presentation.tasksPreview.FavoriteScreen
import com.example.to_do_list.presentation.tasksPreview.PreviewTaskViewModel
import com.example.to_do_list.presentation.tasksPreview.ToDayTasksScreen

import com.example.to_do_list.presentation.tasksPreview.TasksScreenViewModel


@Composable
fun AppNavigate(
    tasksScreenViewModel: TasksScreenViewModel,
    bottomSheetViewModel: BottomSheetViewModel,
    taskPreviewViewModel: PreviewTaskViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.AppNavigation.route){

        composable(route = Screens.AppNavigation.route){
            HomeScreen(navController,tasksScreenViewModel, bottomSheetViewModel)
        }

        composable(route = "${Screens.UpdateScreen.route}/{taskId}", arguments = listOf(
            navArgument("taskId"){type = NavType.IntType}
        )){
            val id = it.arguments?.getInt("taskId")
            id?.let {idNotNull->
                UpdateTaskScreen(taskId = idNotNull,taskPreviewViewModel)
            }
        }    }
}

@Composable
fun BottomNav(
    bottomNavController: NavHostController,
    appNavController: NavHostController,
    viewModel: TasksScreenViewModel
) {
    NavHost(navController = bottomNavController, startDestination = NavigationScreens.ToDay.route){

        composable(route = NavigationScreens.ToDay.route){
            ToDayTasksScreen(appNavController,viewModel)
        }
        composable(route = NavigationScreens.AllTasks.route){
            AllTasksScreen(appNavController,viewModel)
        }

        composable(route = NavigationScreens.Favorite.route){
            FavoriteScreen(appNavController,viewModel)
        }

    }
}
