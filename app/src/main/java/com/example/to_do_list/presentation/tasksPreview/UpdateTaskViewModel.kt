package com.example.to_do_list.presentation.tasksPreview

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.to_do_list.data.local.Task
import com.example.to_do_list.domain.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UpdateTaskViewModel @Inject constructor(private val repo:TasksRepository): ViewModel() {
    private var _id:Int? = null

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _isErrorTitle = MutableStateFlow(false)
    val isErrorTitle: StateFlow<Boolean> = _isErrorTitle.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _isErrorContent = MutableStateFlow(false)
    val isErrorContent: StateFlow<Boolean> = _isErrorContent.asStateFlow()

    private val _year = MutableStateFlow(0)
    val year: StateFlow<Int> = _year.asStateFlow()

    private val _month = MutableStateFlow(0)
    val month: StateFlow<Int> = _month.asStateFlow()

    // Password related properties
    private val _day = MutableStateFlow(0)
    val day: StateFlow<Int> = _day.asStateFlow()

    private val _hour = MutableStateFlow(0)
    val hour: StateFlow<Int> = _hour.asStateFlow()

    private val _minute = MutableStateFlow(0)
    val minute: StateFlow<Int> = _minute.asStateFlow()

    private val isDataChanged = mutableStateOf(false)

    fun setTask(taskId: Int){
        viewModelScope.launch (Dispatchers.IO){
            val task = repo.getTask(taskId)
            _id = taskId
            _title.value = task.title
            _content.value = task.content
            _year.value = task.dueDate.year
            _month.value = task.dueDate.monthValue
            _day.value = task.dueDate.dayOfMonth
            _hour.value = task.dueDate.hour
            _minute.value = task.dueDate.minute
        }

    }

    fun onTitleChange(newTitle:String) {
        _title.value = newTitle
        isDataChanged.value = true
    }
    fun onContentChange(newContent:String) {
        _content.value = newContent
        isDataChanged.value = true

    }


    fun onDateSelect(year:Int,month:Int,day:Int){
        _year.value  = year
        _month.value = month
        _day.value = day
        isDataChanged.value = true

    }

    fun onTimeSelect(hour:Int,minute:Int){
        _hour.value  = hour
        _minute.value = minute
        isDataChanged.value = true

    }

    fun onSaveClick(navController: NavHostController) {
        if (isDataChanged.value){
       viewModelScope.launch (Dispatchers.IO){
             _id?.let {
                 Task(
                     id =it,
                     title = _title.value,
                     content = _content.value,
                     dueDate = LocalDateTime.of(_year.value,_month.value,_day.value,_hour.value,_minute.value)
                 ) }?.let {
                 repo.updateTask(
                     it
                 )
             }
           withContext(Dispatchers.Main){
               navController.popBackStack()
           }
         }
        }else{
            navController.popBackStack()

        }
    }

    fun onCancelClick(navController: NavHostController) {
        navController.popBackStack()
    }



}