package com.tomato.compose.unit3state.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tomato.compose.unit3state.bean.TodoItem

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 */
class TodoViewModel : ViewModel() {

    /*val todoItems = MutableLiveData<List<TodoItem>>(listOf())

    fun addItem(item: TodoItem){
        //todoItems.value原集合加需要 新增 的集合 再赋值给todoItems
        todoItems.value = todoItems.value?.plus(listOf(item))
    }

    fun removeItem(item: TodoItem){
        //todoItems.value原集合减需要 移除 的集合 再赋值给todoItems
        todoItems.value = todoItems.value?.minus(listOf(item))
    }*/

    /**
     * 不使用livedata 将viewmodel正式修改为一个状态容器
     * */
    //创建TodoItem只读集合 private set
    var todoItems = mutableStateListOf<TodoItem>()
        private set


   private var currentEditPosition by mutableIntStateOf(-1)

    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)



    fun addItem(item: TodoItem){
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem){
        todoItems.remove(item)
        onEditDone()
    }

    //当todo列表内item被选中时 传入该对象 获取它在列表中的索引位置
    fun onEditItemSelected(item:TodoItem){
        currentEditPosition = todoItems.indexOf(item)
    }

    //todoItem编辑完成，重新给集合中的TodoItem进行赋值
    //不修改id 用于校验
    fun onEditItemChange(item: TodoItem){
        todoItems[currentEditPosition] = item
    }

    fun onEditDone(){
        currentEditPosition = -1
    }



}