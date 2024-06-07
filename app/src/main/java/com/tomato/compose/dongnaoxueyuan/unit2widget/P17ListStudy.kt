package com.tomato.compose.dongnaoxueyuan.unit2widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

/**
 * Created by Tomato on 2024/5/21.
 * Description：
 */

/**
 *  //单独的Column是不能滚动的
 * */
@Composable
fun SimpleColumn() {

    Column {
        repeat(100) {
            Text(text = "item${it}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


/**
 * 添加scrollState后可以滑动 类似于ListView 或者ScrollView
 * */
@Composable
fun SimpleList() {
    //添加scrollState后可以滑动 类似于ListView
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text(text = "item${it}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


/**
 * LazyColumn类似于RecycleView
 * */
@Composable
fun SimpleLazyList() {
    //LazyColumn类似于RecycleView
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(100) {
            Text(text = "item${it}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


/**
 * LazyColumn类似于RecycleView
 * */
@Composable
fun ScrollingList() {
    val listSize = 70
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }

            }, modifier = Modifier.weight(1f)) {
                Text(text = "Scroll to the top")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize)
                }
            }, modifier = Modifier.weight(1f)) {
                Text(text = "Scroll to the bottom")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }
    }

}


@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(data = "https://img2.baidu.com/it/u=1798855361,2052437588&fm=253&fmt=auto&app=138&f=JPEG"),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "item${index}", style = MaterialTheme.typography.bodySmall)
    }
}

