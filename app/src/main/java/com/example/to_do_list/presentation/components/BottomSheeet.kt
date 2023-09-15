package com.example.to_do_list.presentation.components


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_do_list.R


import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetModel(
    title: String,
    isTitleError: Boolean,
    titleMessageError: String,
    content: String,
    isContentError: Boolean,
    contentMessageError: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onDateSelect: (Int, Int, Int) -> Unit,
    onTimeSelect: (Int, Int) -> Unit,
    onDismissRequest: () -> Unit,
    onAddRequest: () -> Unit,
    dateTimeErrorMessage: String
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.height(570.dp),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        BottomSheetContent(
            title=title,
            isTitleError = isTitleError,
            titleMessageError=titleMessageError,
            isContentError=isContentError,
            contentMessageError=contentMessageError,
            content=content,
            dateTimeErrorMessage=dateTimeErrorMessage,
            onTitleChange=onTitleChange,
            onContentChange=onContentChange,
            onDateSelect=onDateSelect,
            onTimeSelect=onTimeSelect,
            onAddRequest = onAddRequest,
        )
    }
}

@Composable
fun BottomSheetContent(
    title: String,
    isTitleError: Boolean,
    titleMessageError: String,
    content: String,
    isContentError: Boolean,
    contentMessageError: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onDateSelect: (Int, Int, Int) -> Unit,
    onTimeSelect: (Int, Int) -> Unit,
    onAddRequest: () -> Unit,
    dateTimeErrorMessage: String,
) {
    val context = LocalContext.current



    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        OutlinedTextField(
            label = { Text("Title") },
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            isError = isTitleError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),

            )
        Row {
            Text(
                titleMessageError, style = MaterialTheme.typography.bodyMedium, modifier = Modifier
                    .padding(top = 3.dp, start = 25.dp), color = Color.Red
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        OutlinedTextField(
            label = { Text("Description") },
            value = content,
            isError = isContentError,
            onValueChange = {
                onContentChange(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,

            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            maxLines = 3,
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        )
        Row {
            Text(
                contentMessageError, style = MaterialTheme.typography.bodyMedium, modifier = Modifier
                    .padding(top = 3.dp, start = 25.dp), color = Color.Red
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 5.dp, top = 20.dp)
                .clickable {
                    showDatePicker(context) { year, month, day ->
                        onDateSelect(year, month, day)
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
                .padding(start = 5.dp, top = 30.dp)
                .clickable {
                    showTimePicker(context) { hour, minute ->
                        onTimeSelect(hour, minute)
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


        Row {
            Text(
                dateTimeErrorMessage, style = MaterialTheme.typography.bodyMedium, modifier = Modifier
                    .padding(top = 3.dp, start = 25.dp), color = Color.Red
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        Row(
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            ButtonClickOn(buttonText = "Add Task", paddingValue = 30) {
                onAddRequest()
            }
        }
    }

}


fun showDatePicker(
    context: Context,
    onDateChange:(Int, Int, Int) -> Unit
) {
    val currentDateTime = LocalDateTime.now()
    val datePicker = DatePickerDialog(
        /*context*/ context,
        /*listener*/{ _, year, month, day ->
            onDateChange(year,month+1,day)
            Toast.makeText(context, "$day/${month + 1}/$year", Toast.LENGTH_SHORT).show()
        },
        /*year*/ currentDateTime.year,
        /*month*/ currentDateTime.monthValue-1,
        /*day*/ currentDateTime.dayOfMonth
    )
    datePicker.show()
}

fun showTimePicker(
    context: Context,
    onTimeChange: ( Int, Int) -> Unit
) {
    val currentDateTime = LocalDateTime.now()
    val timePicker = TimePickerDialog(
        /*context*/ context,
        /*listener*/ { _, hour, minute ->
            onTimeChange(hour,minute)
            Toast.makeText(context,  "$hour:$minute", Toast.LENGTH_SHORT).show()
        },
        /*hour*/ currentDateTime.hour,
        /*minute*/ currentDateTime.minute,
        /*is24Hour*/ true
    )
    timePicker.show()
}


