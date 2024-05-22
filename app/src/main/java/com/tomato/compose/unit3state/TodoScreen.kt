package com.tomato.compose.unit3state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tomato.compose.log
import kotlin.random.Random

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 * Compose会根据状态的改变智能重组组件 将组件树中使用到状态的组件重组 未使用到的部分不重组
 */

@Composable
fun TodoScreenPage(
    data: List<TodoItem>,
    onAddItem: (item: TodoItem) -> Unit,
    onRemoveItem: (item: TodoItem) -> Unit
) {
    Column {
        LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(8.dp)) {
            items(data) { item ->
                //单个条目宽度填充父布局最大宽度
                TodoRow(todo = item, Modifier.fillParentMaxWidth(), onItemClick = { onRemoveItem(it) })
            }
        }
        Button(
            onClick = {
                //添加随机item
                onAddItem.invoke(Unit3Utils.getRandomTodoItem())
            }, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add Random Item")
        }
    }
}

@Composable
fun TodoRow(todo: TodoItem, modifier: Modifier = Modifier, onItemClick: (item: TodoItem) -> Unit) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onItemClick.invoke(todo)
            },
        //Arrangement.SpaceBetween 子元素水平均匀分布 第一元素前 和 最后一个元素后 不预留空间
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = todo.task)

        /***
         * 每次重组都会randomTint()获取到一个随机数 为防止iconAlpha每次重组都变化
         * 使用remember来根据key进行记录iconAlpha ，iconAlpha生成后会存储在内存中 ， 只要key不改变 则读取到的iconAlpha不变
         * **/
        val iconAlpha = remember(key1 = todo.id) {
            randomTint()
        }
        /***
         * 也可以使用remember来生成内部状态 比如是否勾选，是否展开这种情况
         */

        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = todo.icon.contentDescription
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}


@Composable
fun TodoItemInput(onItemComplete: (todo: TodoItem) -> Unit) {
    /**
     * val (text, setText) = remember {
     *         mutableStateOf("")
     *     }
     *是kotlin中结构声明的语法 其效果就是把一个对象中的多个成员变量同时赋值到多个变量去，和以下代码同理
     *     val state = remember {
     *         mutableStateOf("")
     *     }
     *     val text = state.component1()
     *     val setText = state.component2()
     *
     * 通过MutableState可以有三种方式创建state对象
     * -  val state = remember { mutableStateOf("default") }
     * -  var value:String by remember { mutableStateOf("default") }
     * -  val (text, setText) = remember { mutableStateOf("") }
     *
     * 在组合中务必使用remember来记录对象 否则它会在每次重组时重新创建
     * MutableState<T> 类似于MutableLiveData<T> ,于Compose运行是集成，它是可观察的，会在更新时通知Compose进行重组
     * */

    log("TodoItemInput Rebuild")
    val (text, setText) = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)

        ) {
            //输入框
            TodoEditText(
                text,
                onTextChange = setText,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .padding(end = 8.dp)
            )

            //按钮
            TodoEditButton(
                onClick = { onItemComplete.invoke(TodoItem(text, TodoIcon.Default)) },
                text = "Add",
                //按钮垂直居中
                modifier = Modifier.align(Alignment.CenterVertically),
                enable = text.isNotEmpty()
            )
        }
    }
}


/**
 * 输入框 对于输入框的状态是text onTextChange
 * */
@Composable
fun TodoEditText(text: String, onTextChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.colors(focusedTextColor = Color.Black),
        maxLines = 1,
        modifier = modifier.background(Color.Transparent)
    )


}


@Composable
fun TodoEditButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier, enable: Boolean = true) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, disabledContainerColor = Color.Gray),
        modifier = modifier,
        enabled = enable
    ) {
        Text(text = text)
    }
}