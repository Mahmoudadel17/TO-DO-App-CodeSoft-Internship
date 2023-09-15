package com.example.to_do_list.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonClickOn(buttonText:String,paddingValue:Int ,onButtonClick:() -> Unit ) {
    Button (colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        enabled = true,
        onClick = {onButtonClick()},
        modifier = Modifier
            .padding(top =paddingValue.dp)
            .fillMaxWidth(1f)

    ){
        Text(text = buttonText, fontSize = 30.sp, style = TextStyle(color = MaterialTheme.colorScheme.background))
    }
}

@Composable
fun NormalButtonClickOn(buttonText:String,paddingValue:Int,modifier: Modifier ,onButtonClick:() -> Unit ) {
    Button (colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        enabled = true,
        onClick = {onButtonClick()},
        modifier = modifier
            .padding(top =paddingValue.dp)


    ){
        Text(text = buttonText, fontSize = 30.sp, style = TextStyle(color = MaterialTheme.colorScheme.background))
    }
}