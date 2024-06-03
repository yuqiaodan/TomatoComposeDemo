package com.tomato.compose.weijueye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tomato.compose.bean.UserBean
import com.tomato.compose.log
import kotlinx.coroutines.launch

class SampleListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userList = mutableStateListOf<UserBean>()
        for (i in 1..30) {
            userList.add(UserBean("User:${i}", "+86 ${i}", false))
        }
        setContent {
            PageContent(userList = userList, onCheckedChange = { index, isCheck ->
                userList[index] = userList[index].copy(isCheck = isCheck)
            })
        }
    }
}


@Composable
fun PageContent(userList: List<UserBean>, onCheckedChange: (index: Int, isCheck: Boolean) -> Unit) {

    val list = listOf(1, 3, 4, 5, 6, 7, 8, 9, 10)
    //普通整齐表格
    //columns = 列数
    val state = rememberLazyGridState()
    LazyVerticalGrid(state = state,columns = GridCells.Fixed(3)) {
        items(list) {
            Text(text = "index：${it}")
        }
    }
    //LazyList(userList,onCheckedChange)
    //Column实现的普通列表
    //NormalList(userList,onCheckedChange)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyList(userList: List<UserBean>, onCheckedChange: (index: Int, isCheck: Boolean) -> Unit) {
    //获取一个携程作用域
    val coroutineScope = rememberCoroutineScope()
    //滑动状态管理
    val scrollState = rememberLazyListState()

    /**
     * LazyColumn可以控制滚动到具体的Item位置
     * scrollToItem 滚动到指定item位置
     * animateScrollToItem 带动画的滚动
     *
     * scrollBy 从当前位置滚动n个像素
     * animateScrollBy 带动画的滚动
     * */
    LazyColumn(state = scrollState) {
        //可以给列表添加一个表头
        stickyHeader {
            Text(text = "我是表头"  , modifier = Modifier.height(100.dp).background(Color.Yellow))
        }
        itemsIndexed(userList){index, userBean ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(150.dp)) {
                Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = null)
                Column(modifier = Modifier
                    .weight(1f)
                    .clickable {
                        /**
                         * scrollTo 跳转到指定位置
                         * scrollBy 从当前位置跳转指定距离
                         * */
                        coroutineScope.launch {
                            log("animateScrollTo:$index")
                            scrollState.animateScrollToItem(15)
                        }
                    }) {
                    Text(text = userBean.name)
                    Text(text = userBean.number)
                }
                Checkbox(checked = userBean.isCheck, onCheckedChange = {
                    onCheckedChange(index, it)
                })
            }
        }
    }
}

@Composable
fun NormalList(userList: List<UserBean>, onCheckedChange: (index: Int, isCheck: Boolean) -> Unit){
    //获取一个携程作用域
    val coroutineScope = rememberCoroutineScope()
    //滑动状态管理
    val scrollState = rememberScrollState()
    /**
     * 注意:这里Column的状态只能控制滚动到像素位置
     * scrollTo 从开始位置滚动n个像素
     * scrollBy 从当前位置滚动n个像素
     * animateScrollTo() 带动画的滚动
     * animateScrollBy() 带动画的滚动
     * */
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        userList.forEachIndexed { index, userBean ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(150.dp)) {
                Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = null)
                Column(modifier = Modifier
                    .weight(1f)
                    .clickable {

                        coroutineScope.launch {
                            log("animateScrollTo:$index")
                            scrollState.scrollTo(15)


                        }
                    }) {
                    Text(text = userBean.name)
                    Text(text = userBean.number)
                }
                Checkbox(checked = userBean.isCheck, onCheckedChange = {
                    onCheckedChange(index, it)
                })
            }
        }
    }
}