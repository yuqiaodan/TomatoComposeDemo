package com.tomato.compose.unit3state.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomato.compose.unit3state.bean.TodoItem

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 */
class TodoViewModel:ViewModel() {
    val todoItems = MutableLiveData<List<TodoItem>>(listOf())

    fun addItem(item: TodoItem){
        //todoItems.value原集合加需要 新增 的集合 再赋值给todoItems
        todoItems.value = todoItems.value?.plus(listOf(item))
    }

    fun removeItem(item: TodoItem){
        //todoItems.value原集合减需要 移除 的集合 再赋值给todoItems
        todoItems.value = todoItems.value?.minus(listOf(item))
    }

}