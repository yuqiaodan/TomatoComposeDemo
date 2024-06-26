package com.tomato.compose.dongnaoxueyuan.unit3state

import com.tomato.compose.dongnaoxueyuan.unit3state.bean.TodoIcon
import com.tomato.compose.dongnaoxueyuan.unit3state.bean.TodoItem

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 */
object Unit3Utils {

    fun getRandomTodoItem(): TodoItem {
        val message = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11").random()
        val icon = TodoIcon.values().random()
        return TodoItem(message, icon)
    }

}




