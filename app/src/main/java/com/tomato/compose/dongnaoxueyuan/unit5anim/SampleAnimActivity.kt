package com.tomato.compose.dongnaoxueyuan.unit5anim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.tomato.compose.dongnaoxueyuan.unit5anim.home.Home
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.AnimationCodelabTheme

class SampleAnimActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationCodelabTheme {
                Home()
            }
        }
    }




}

