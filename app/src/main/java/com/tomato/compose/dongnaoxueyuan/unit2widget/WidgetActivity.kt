package com.tomato.compose.dongnaoxueyuan.unit2widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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

                MyBanner()

            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MyBanner() {
// Display 10 items
        val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE/2)
        Surface(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(state = pagerState, count = Int.MAX_VALUE) { page ->
                // Our page content
                Text(
                    text = "Page: $page"
                )
            }
        }

        //pagerState.scr(1)
    }
}
