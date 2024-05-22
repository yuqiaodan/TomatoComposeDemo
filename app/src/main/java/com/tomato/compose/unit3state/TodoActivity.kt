package com.tomato.compose.unit3state

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.tomato.compose.log
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme

class TodoActivity : ComponentActivity() {

    private val viewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatoComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TodoItemInput{item->
                        log("${item.task}")
                    }
                }
            }
        }
    }


    @Composable
    private fun TodoActivityScreen() {
        // 补充委托知识点 by 属性委托 P31
        //items可以视为一个状态
        val items: List<TodoItem> by viewModel.todoItems.observeAsState(listOf())
        TodoScreenPage(data = items, onAddItem = {
            viewModel.addItem(it)
        }, onRemoveItem = {
            viewModel.removeItem(it)
        })
    }
}



