package com.example.to_do_list.presentation.tasksPreview



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.to_do_list.data.local.Task
import com.example.to_do_list.domain.repository.TasksRepository
import com.example.to_do_list.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TasksScreenViewModel  @Inject constructor(private val repo:TasksRepository) :ViewModel(){


    private  val _tasks:MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasks = _tasks.asStateFlow()


    private val _isChange = MutableStateFlow(false)
    val isChange: StateFlow<Boolean> = _isChange.asStateFlow()

    private val _showDialog  = MutableStateFlow(false)
    val showDialog : StateFlow<Boolean> = _showDialog .asStateFlow()

    private var taskWillDeleted:Task? = null
    init {

        viewModelScope.launch(Dispatchers.IO) {
            getAllTasks()
        }
    }

    private fun getAllTasks(){
        _isChange.value = _isChange.value.not()
        viewModelScope.launch (Dispatchers.IO){
            repo.getAllTasks().collect{
                _tasks.value = sortTasks(it)
            }
        }

    }



    private fun sortTasks(tasks:List<Task>):List<Task>{
        return tasks.sortedWith(compareBy<Task> { it.dueDate }.thenBy { it.title })
    }


    fun filterToDay(task: Task):Boolean{
        val currentDateTime = LocalDateTime.now()
        return task.dueDate.year == currentDateTime.year
                &&task.dueDate.monthValue-1 == currentDateTime.monthValue-1
                &&task.dueDate.dayOfMonth == currentDateTime.dayOfMonth
    }

    fun formatDate(): String {
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH)
        return date.format(formatter)
    }




    fun onTaskComplete(task: Task){
        task.isComplete = task.isComplete.not()
        viewModelScope.launch (Dispatchers.IO){
            repo.updateTask(task)
            getAllTasks()
        }
    }
    fun onTaskFavorite(task: Task){
        task.isFavorite = task.isFavorite.not()
        viewModelScope.launch (Dispatchers.IO){
            repo.updateTask(task)
            getAllTasks()
        }
    }

    fun onTaskDelete(){
        viewModelScope.launch (Dispatchers.IO){
            taskWillDeleted?.let { repo.removeTask(it.id) }
            taskWillDeleted = null
            getAllTasks()
        }
    }


    fun onShowDialogDelete(task: Task){
        _showDialog.value = true
        taskWillDeleted = task
    }


    fun onDismissDialogDelete(){
        _showDialog.value = false
        getAllTasks()
    }


    fun onTimeChange(task: Task,hour:Int,minute:Int){
        val newDueDate : LocalDateTime = LocalDateTime.of(
            task.dueDate.year, task.dueDate.month, task.dueDate.dayOfMonth, hour, minute)

        task.dueDate = newDueDate
        viewModelScope.launch(Dispatchers.IO){
            repo.updateTask(task)
            getAllTasks()
        }
    }
    fun onDateChange(task: Task,year:Int,month:Int,day:Int){
        val newDueDate : LocalDateTime =LocalDateTime.of(year,month,day,task.dueDate.hour,task.dueDate.minute)
        task.dueDate = newDueDate
        viewModelScope.launch(Dispatchers.IO){
            repo.updateTask(task)
            getAllTasks()
        }
    }


    fun onUpdateTaskClick(task: Task, navController:NavHostController){
        navController.navigate("${Screens.UpdateScreen.route}/${task.id}")
    }

}

