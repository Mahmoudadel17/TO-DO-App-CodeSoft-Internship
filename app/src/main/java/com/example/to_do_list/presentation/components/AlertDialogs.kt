package com.example.to_do_list.presentation.components



import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.to_do_list.data.local.Task
import com.example.to_do_list.ui.theme.floatingActionButton


@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = onDismiss,
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this task?", color = MaterialTheme.colorScheme.onSurface) },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.primary),
                ) {
                    Text("Delete", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.primary),
                ) {
                    Text("Cancel",color = MaterialTheme.colorScheme.background)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = floatingActionButton
                )
            }
        )
    }
}



fun showDatePicker(
    context: Context,
    task: Task,
    onDateChange:(Task,Int,Int,Int) -> Unit
) {
    val datePicker = DatePickerDialog(
        /*context*/ context,
        /*listener*/{ _, year, month, day ->
            onDateChange(task,year,month+1,day)
            Toast.makeText(context, "$day/${month + 1}/$year", Toast.LENGTH_SHORT).show()
        },
        /*year*/ task.dueDate.year,
        /*month*/ task.dueDate.monthValue-1,
        /*day*/ task.dueDate.dayOfMonth
    )
    datePicker.show()
}

fun showTimePicker(
    context: Context,
    task: Task,
    onTimeChange: (Task,Int,Int) -> Unit
) {
    val timePicker = TimePickerDialog(
        /*context*/ context,
        /*listener*/ { _, hour, minute ->
            onTimeChange(task,hour,minute)
            Toast.makeText(context,  "$hour:$minute", Toast.LENGTH_SHORT).show()
        },
        /*hour*/ task.dueDate.hour,
        /*minute*/ task.dueDate.minute,
        /*is24Hour*/ true
    )
    timePicker.show()
}



