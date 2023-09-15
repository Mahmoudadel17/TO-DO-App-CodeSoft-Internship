package com.example.to_do_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.to_do_list.R
import com.example.to_do_list.ui.theme.Splash
import com.example.to_do_list.ui.theme.TextSplash

@Composable
fun SplashScreen() {

    Column(
        modifier = Modifier.fillMaxSize().background(Splash).padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Text(
            text = stringResource(id = R.string.to_do),
            fontSize = 40.sp,
            color = Color.Black
        )
        Text(
            text = stringResource(id = R.string.splash_text),
            fontSize = 16.sp,
            color = TextSplash,
            letterSpacing = 0.2.em
        )
        LottieAnimationShow(R.raw.loading,200,250)

    }


}
