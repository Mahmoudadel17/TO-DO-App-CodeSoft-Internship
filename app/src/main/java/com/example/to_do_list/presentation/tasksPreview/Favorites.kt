package com.example.to_do_list.presentation.tasksPreview

import com.example.to_do_list.R
import com.example.to_do_list.presentation.components.NoTasks
import com.example.to_do_list.presentation.components.TaskItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun FavoriteScreen(appNavController: NavHostController, viewModel: TasksScreenViewModel){
    val change by viewModel.isChange.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    if (tasks.filter { task -> task.isFavorite}.isNotEmpty()){

        if (change || change.not()) {
            Column {

                LazyColumn {
                    items(tasks.filter {task -> !task.isComplete && task.isFavorite}) {
                        TaskItem(
                            task = it,
                            showDialog=showDialog,
                            onCompleteChecked = { taskComplete -> viewModel.onTaskComplete(taskComplete) },
                            onFavoriteClicked = { taskFavorite -> viewModel.onTaskFavorite(taskFavorite) },
                            onDeleteConfirm = { viewModel.onTaskDelete() },
                            onShowDialog = { task -> viewModel.onShowDialogDelete(task) },
                            onDialogDismiss = { viewModel.onDismissDialogDelete() },
                            onDateChange = {task,year,month,day -> viewModel.onDateChange(task,year,month,day)},
                            onTimeChange = {task,hour,minute -> viewModel.onTimeChange(task,hour,minute)},
                        ) { updateTask->
                            // navigate to task view screen
                            viewModel.onTaskClick(updateTask, appNavController)
                        }
                    }
                    item {
                        if (tasks.filter {task -> task.isComplete && task.isFavorite}.isNotEmpty() && tasks.filter {task -> !task.isComplete && task.isFavorite}.isNotEmpty()){
                            Divider(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    items(tasks.filter {task -> task.isComplete && task.isFavorite}) {
                        TaskItem(
                            task = it,
                            showDialog=showDialog,
                            onCompleteChecked = { taskComplete -> viewModel.onTaskComplete(taskComplete) },
                            onFavoriteClicked = { taskFavorite -> viewModel.onTaskFavorite(taskFavorite) },
                            onDeleteConfirm = {viewModel.onTaskDelete() },
                            onShowDialog = { task -> viewModel.onShowDialogDelete(task) },
                            onDialogDismiss = { viewModel.onDismissDialogDelete() },
                            onDateChange = {task,year,month,day -> viewModel.onDateChange(task,year,month,day)},
                            onTimeChange = {task,hour,minute -> viewModel.onTimeChange(task,hour,minute)},
                        ) {updateTask->
                            // navigate to task view screen
                            viewModel.onTaskClick(updateTask, appNavController)
                        }
                    }

                }


            }
        }

    }else{
        NoTasks(
            R.drawable.favoritetasks,
            " no to day tasks",
            "No Tasks Found here",
            "Here is where you can find any tasks that you may have saved previously."
        )
    }

}