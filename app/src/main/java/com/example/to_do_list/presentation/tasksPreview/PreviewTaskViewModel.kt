package com.example.to_do_list.presentation.tasksPreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do_list.data.local.Task
import com.example.to_do_list.domain.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PreviewTaskViewModel @Inject constructor(private val repo:TasksRepository): ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _isErrorTitle = MutableStateFlow(false)
    val isErrorTitle: StateFlow<Boolean> = _isErrorTitle.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _isErrorContent = MutableStateFlow(false)
    val isErrorContent: StateFlow<Boolean> = _isErrorContent.asStateFlow()

    // date and time
    private val _dateTime = MutableStateFlow(LocalDateTime.now())
    val dateTime: StateFlow<LocalDateTime> = _dateTime.asStateFlow()

    private val _isDataChanged = MutableStateFlow(false)
    val isDataChanged: StateFlow<Boolean> = _isDataChanged.asStateFlow()

    fun setTask(taskId: Int){
        viewModelScope.launch (Dispatchers.IO){
            val task = repo.getTask(taskId)
            _title.value = task.title
            _content.value = task.content
            _dateTime.value = task.dueDate
        }

    }

    fun onTitleChange(newTitle:String) {

    }
    fun onContentChange(newContent:String) {

    }


    fun onDateSelect(year:Int,month:Int,day:Int){

    }

    fun onTimeSelect(hour:Int,minute:Int){

    }

    fun onSaveClick(){

    }

    fun onCancelClick(){

    }



}