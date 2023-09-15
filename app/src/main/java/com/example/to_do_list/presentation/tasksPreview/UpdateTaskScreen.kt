package com.example.to_do_list.presentation.tasksPreview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.to_do_list.R
import com.example.to_do_list.presentation.components.NormalButtonClickOn
import com.example.to_do_list.presentation.components.showDatePicker
import com.example.to_do_list.presentation.components.showTimePicker

@Composable
fun UpdateTaskScreen(
    taskId: Int,
    taskPreviewViewModel: UpdateTaskViewModel,
    navController: NavHostController
) {
    taskPreviewViewModel.setTask(taskId)
    PreviewTask(taskPreviewViewModel,navController)
}

@Composable
fun PreviewTask(taskPreviewViewModel: UpdateTaskViewModel, navController: NavHostController) {
    val context = LocalContext.current

    val title by taskPreviewViewModel.title.collectAsState()
    val content by taskPreviewViewModel.content.collectAsState()
    val isErrorTitle by taskPreviewViewModel.isErrorTitle.collectAsState()
    val isErrorContent by taskPreviewViewModel.isErrorContent.collectAsState()


    Column (modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = title,
            isError = isErrorTitle,
            onValueChange = {
                taskPreviewViewModel.onTitleChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,

                ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.10f),
            maxLines = 2,
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        )
        OutlinedTextField(
            value = content,
            isError = isErrorContent,
            onValueChange = {
                taskPreviewViewModel.onContentChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,

                ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.40f),
            maxLines = 15,
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 5.dp, top = 20.dp)
                .clickable {
                    showDatePicker(context) { year, month, day ->
                        taskPreviewViewModel.onDateSelect(year, month, day)
                    }
                }) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription ="Date Select",
                tint = Color.DarkGray,

                )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Select DeadLine Date",fontSize = 18.sp,color = Color.DarkGray)
        }


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 5.dp, top = 15.dp)
                .clickable {
                    showTimePicker(context) { hour, minute ->
                        taskPreviewViewModel.onTimeSelect(hour, minute)
                    }
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription ="Date Select",
                tint = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Select DeadLine Time",fontSize = 18.sp, color = Color.DarkGray)
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 5.dp,end = 5.dp, bottom  = 10.dp)


        ){
            NormalButtonClickOn(buttonText = "Save" ,paddingValue = 30,Modifier.weight(0.50f)) {
                taskPreviewViewModel.onSaveClick(navController)

            }
            Spacer(modifier = Modifier.width(8.dp))
            NormalButtonClickOn(buttonText = "Cancel", paddingValue = 30,Modifier.weight(0.50f)) {
                taskPreviewViewModel.onCancelClick(navController)
            }

        }
    }
}

