package com.tomato.compose.unit3state.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme
import com.tomato.compose.unit3state.TodoScreenPage
import com.tomato.compose.unit3state.bean.TodoItem


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

   //val sss = MutableLiveData<List<TodoItem>>(listOf())
    @Composable
    private fun TodoActivityScreen() {
        //sss.observeAsState()
        // 补充委托知识点 by 属性委托 P31
        //items可以视为一个状态 LiveData可以直接通过observeAsState转为一个state对象
        //val items: List<TodoItem> by viewModel.todoItems.observeAsState(listOf())
        TodoScreenPage(
            data = viewModel.todoItems,
            currentEditing = viewModel.currentEditItem,
            //可以按传统方式调用viewmodel中的函数 也可以传入函数的引用
            //添加事件
            onAddItem = {
                viewModel.addItem(it)
            },
            //传入函数的引用
            onRemoveItem = viewModel::removeItem,//删除
            onStartEdit = viewModel::onEditItemSelected,//开始编辑
            onEditItemChange = viewModel::onEditItemChange,//编辑中
            onEditDone = viewModel::onEditDone//保存
        )
    }
}



