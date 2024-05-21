package com.tomato.compose.unit2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme

/***
 * 第二章 标准布局组件
 * 基础组件学习
 * */
class WidgetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatoComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                ConstrainLayoutContent1()
            }
        }
    }
}
