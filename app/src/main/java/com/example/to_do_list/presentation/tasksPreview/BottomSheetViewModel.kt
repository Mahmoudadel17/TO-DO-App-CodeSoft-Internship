package com.example.to_do_list.presentation.tasksPreview


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_do_list.data.local.Task
import com.example.to_do_list.data.local.TaskDao
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
class BottomSheetViewModel @Inject constructor(private val repo: TasksRepository):ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _isErrorTitle = MutableStateFlow(false)
    val isErrorTitle: StateFlow<Boolean> = _isErrorTitle.asStateFlow()

    private val _titleErrorMessage = MutableStateFlow("")
    val titleErrorMessage: StateFlow<String> = _titleErrorMessage.asStateFlow()

    // Password related properties
    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _isErrorContent = MutableStateFlow(false)
    val isErrorContent: StateFlow<Boolean> = _isErrorContent.asStateFlow()

    private val _contentErrorMessage = MutableStateFlow("")
    val contentErrorMessage: StateFlow<String> = _contentErrorMessage.asStateFlow()


    private val _dateTimeErrorMessage = MutableStateFlow("")
    val dateTimeErrorMessage: StateFlow<String> = _dateTimeErrorMessage.asStateFlow()

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

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()


    fun onShowBottomSheet(){
        _showBottomSheet.value = true
    }
    fun onTitleChange(title:String){
        val newTitle = title
        _title.value = newTitle

        // Reset error if found
        _isErrorTitle.value = false
        _titleErrorMessage.value = ""
    }

    fun onContentChange(content:String){
        val newContent = content
        _content.value = newContent

        // Reset error if found
        _isErrorContent.value = false
        _contentErrorMessage.value = ""

    }

    fun onDateSelect(year:Int,month:Int,day: Int){
        _year.value = year
        _month.value = month
        _day.value = day
        _dateTimeErrorMessage.value
    }
    fun onTimeSelect(hour:Int,minute: Int){
        _hour.value = hour
        _minute.value = minute
        _dateTimeErrorMessage.value = ""
    }

    fun onDismissRequest(){
        _showBottomSheet.value = false

        // Reset error if found
        _title.value=""
        _content.value=""
        _isErrorContent.value = false
        _contentErrorMessage.value = ""
        _isErrorTitle.value = false
        _titleErrorMessage.value = ""
        _dateTimeErrorMessage.value = ""
        _year.value=0
        _month.value=0
        _day.value=0
        _hour.value=0
        _minute.value=0


    }


    fun onFloatingActionButtonAdd(){
        if (
            title.value.isNotEmpty()
            &&content.value.isNotEmpty()
            &&year.value!=0
            &&month.value!=0
            &&day.value!=0
            &&hour.value!=0
        ){
           viewModelScope.launch (Dispatchers.IO){
               Log.i("hi",month.value.toString())
               repo.addTask(
                   Task(title = title.value, content = content.value,
                       dueDate = LocalDateTime.of(year.value,month.value,day.value,hour.value,minute.value) )
               )
               onDismissRequest()
           }

        }else{
            if (title.value.isEmpty()){
                _isErrorTitle.value = true
                _titleErrorMessage.value = "Please enter task title*"
            }
            if (content.value.isEmpty()){
                _isErrorContent.value = true
                _contentErrorMessage.value = "Please enter task description*"

            }
            if ((year.value==0 && month.value==0 &&day.value==0 ) || hour.value==0){
                _dateTimeErrorMessage.value = "make sure you select DeadLine (Date and Time)*"
            }

        }

    }

}