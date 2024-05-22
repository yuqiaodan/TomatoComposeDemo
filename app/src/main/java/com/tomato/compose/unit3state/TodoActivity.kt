package com.tomato.compose.unit3state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme

class TodoActivity : ComponentActivity() {

    private val viewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatoComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TodoActivityScreen()
                }
            }
        }
    }


    @Composable
    private fun TodoActivityScreen() {
        val items:List<TodoItem> by viewModel.todoItems.observeAsState(initial = listOf())

        TodoScreenPage(items, onAddItem = {

        }, onRemoveItem = {

        })
    }

}

