package com.tomato.compose.unit3state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 */

@Composable
fun TodoScreenPage(data: List<TodoItem>, onAddItem: (item: TodoItem) -> Unit, onRemoveItem: (item: TodoItem) -> Unit) {
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
        Icon(imageVector = todo.icon.imageVector, contentDescription = todo.icon.contentDescription)
    }
}