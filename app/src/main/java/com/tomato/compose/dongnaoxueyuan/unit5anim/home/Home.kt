/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/***
 * 官方动画教程示例代码：
 * - 教程地址：https://developer.android.com/codelabs/jetpack-compose-animation?hl=zh-cn#0
 * - 教程代码库：https://github.com/android/codelab-android-compose  AnimationCodelab
 * - b站视频同款代码
 * */


package com.tomato.compose.dongnaoxueyuan.unit5anim.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides.Companion.Horizontal
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomato.compose.R
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.Amber600
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.AnimationCodelabTheme
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.Green
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.GreenLight
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.PaleDogwood
import com.tomato.compose.dongnaoxueyuan.unit5anim.ui.Seashell
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private enum class TabPage {
    Home, Work
}

/**
 * Shows the entire screen.
 */
@Composable
fun Home() {
    // String resources.
    val allTasks = stringArrayResource(R.array.tasks)
    val allTopics = stringArrayResource(R.array.topics).toList()

    // The currently selected tab.
    var tabPage by remember { mutableStateOf(TabPage.Home) }

    // True if the whether data is currently loading.
    var weatherLoading by remember { mutableStateOf(false) }

    // Holds all the tasks currently shown on the task list.
    val tasks = remember { mutableStateListOf(*allTasks) }

    // Holds the topic that is currently expanded to show its body.
    var expandedTopic by remember { mutableStateOf<String?>(null) }

    // True if the message about the edit feature is shown.
    var editMessageShown by remember { mutableStateOf(false) }

    // Simulates loading weather data. This takes 3 seconds.
    suspend fun loadWeather() {
        if (!weatherLoading) {
            weatherLoading = true
            delay(3000L)
            weatherLoading = false
        }
    }

    // Shows the message about edit feature.
    suspend fun showEditMessage() {
        if (!editMessageShown) {
            editMessageShown = true
            delay(3000L)
            editMessageShown = false
        }
    }

    // Load the weather at the initial composition.
    LaunchedEffect(Unit) {
        loadWeather()
    }

    val lazyListState = rememberLazyListState()

    // The background color. The value is changed by the current tab.
    // TODO 1: Animate this color change.
    /**
     *  动画小节1：简单值动画
     *  使用 animate*AsState 可以创建不同类型的简单值动画 这里背景是颜色 所以使用animateColorAsState
     * */
    //修改为动画
    val backgroundColor by animateColorAsState(targetValue = if (tabPage == TabPage.Home) Seashell else GreenLight)
    //原代码（无动画）
    //val backgroundColor = if (tabPage == TabPage.Home) Seashell else GreenLight

    // The coroutine scope for event handlers calling suspend functions.
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HomeTabBar(
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        containerColor = backgroundColor,
        floatingActionButton = {
            HomeFloatingActionButton(
                extended = lazyListState.isScrollingUp(),
                onClick = {
                    coroutineScope.launch {
                        showEditMessage()
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                end = padding.calculateEndPadding(LayoutDirection.Ltr)
            )
        ) {
            LazyColumn(
                contentPadding = WindowInsets(
                    16.dp,
                    32.dp,
                    16.dp,
                    padding.calculateBottomPadding() + 32.dp
                ).asPaddingValues(),
                state = lazyListState
            ) {
                // Weather
                item { Header(title = stringResource(R.string.weather)) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shadowElevation = 2.dp
                    ) {
                        if (weatherLoading) {
                            LoadingRow()
                        } else {
                            WeatherRow(onRefresh = {
                                coroutineScope.launch {
                                    loadWeather()
                                }
                            })
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }

                // Topics
                item { Header(title = stringResource(R.string.topics)) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(allTopics) { topic ->
                    TopicRow(
                        topic = topic,
                        expanded = expandedTopic == topic,
                        onClick = {
                            expandedTopic = if (expandedTopic == topic) null else topic
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }

                // Tasks
                item { Header(title = stringResource(R.string.tasks)) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                if (tasks.isEmpty()) {
                    item {
                        TextButton(onClick = { tasks.clear(); tasks.addAll(allTasks) }) {
                            Text(stringResource(R.string.add_tasks))
                        }
                    }
                }
                items(tasks, key = { it }) { task ->
                    TaskRow(
                        task = task,
                        onRemove = { tasks.remove(task) }
                    )
                }
            }
            EditMessage(editMessageShown)
        }
    }
}

/**
 * Shows the floating action button.
 *
 * @param extended Whether the tab should be shown in its expanded state.
 */
// AnimatedVisibility is currently an experimental API in Compose Animation.
@Composable
private fun HomeFloatingActionButton(
    extended: Boolean,
    onClick: () -> Unit
) {
    // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
    // how it should animate.
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
            // Toggle the visibility of the content with animation.
            // TODO 2-1: Animate this visibility change.
            /**
             *  动画小节2-1：可见性动画 控件消失以及显示时自动播放动画
             *  AnimatedVisibility 是否可见
             * */
            AnimatedVisibility(visible = extended) {
                Text(
                    text = stringResource(R.string.edit),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }

            //原代码（无动画）
            /*if (extended) {
                Text(
                    text = stringResource(R.string.edit),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 3.dp)
                )
            }*/
        }
    }
}

/**
 * Shows a message that the edit feature is not available.
 */
@Composable
private fun EditMessage(shown: Boolean) {
    // TODO 2-2: The message should slide down from the top on appearance and slide up on
    //           disappearance.
    /**
     *  动画小节2-2：可见性动画 AnimatedVisibility 可以通过enter和exit设置进入和退出效果
     * */
    AnimatedVisibility(
        visible = shown,
        /**
         * enter设置进入动画
         * initialOffsetY 可以设置动画的偏移量（）
         * animationSpec 设置动画持续时间durationMillis 和 插值器easing（变化曲线）
         * */
        /***
         * animationSpec 理解为超级插值器 可以设置动画运行方式 回弹，先快后慢什么什么的
         * 除了spring（物理仿真动画）还有其他效果可选：
         * tween: 适用于大多数简单、基于时间的动画需求。
         * spring: 适用于需要物理仿真效果的动画。
         * keyframes: 适用于需要精确控制动画各个时间点具体值的动画。
         * repeatable: 适用于需要重复一定次数的动画。
         * infiniteRepeatable: 适用于需要无限次重复的动画。*/
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing),
            initialOffsetY = { fullHeight -> -fullHeight }
        ),
        exit = slideOutVertically(
            //FastOutLinearInEasing 先慢后快
            animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 18.dp
        ) {
            Text(
                text = stringResource(R.string.edit_message),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Returns whether the lazy list is currently scrolling up.
 */
@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

/**
 * Shows the header label.
 *
 * @param title The title to be shown.
 */
@Composable
private fun Header(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.semantics { heading() },
        style = MaterialTheme.typography.headlineLarge
    )
}

/**
 * Shows a row for one topic.
 *
 * @param topic The topic title.
 * @param expanded Whether the row should be shown expanded with the topic body.
 * @param onClick Called when the row is clicked.
 */
@Composable
private fun TopicRow(topic: String, expanded: Boolean, onClick: () -> Unit) {
    TopicRowSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        // TODO 3: Animate the size change of the content.
        /**
         * 动画小节3 内容大小动画animateContentSize 组件宽高变化后自动播放动画
         * Modifier.animateContentSize()
         * */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                //添加内容大小变化动画
                .animateContentSize()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = topic,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
    TopicRowSpacer(visible = expanded)
}

/**
 * Shows a separator for topics.
 */
@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

/**
 * Shows the bar that holds 2 tabs.
 *
 * @param backgroundColor The background color for the bar.
 * @param tabPage The [TabPage] that is currently selected.
 * @param onTabSelected Called when the tab is switched.
 */
@Composable
private fun HomeTabBar(
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    Column(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing.only(Horizontal))) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        TabRow(
            selectedTabIndex = tabPage.ordinal,
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            indicator = { tabPositions ->
                HomeTabIndicator(tabPositions, tabPage)
            }
        ) {
            HomeTab(
                icon = Icons.Default.Home,
                title = stringResource(R.string.home),
                onClick = { onTabSelected(TabPage.Home) }
            )
            HomeTab(
                icon = Icons.Default.AccountBox,
                title = stringResource(R.string.work),
                onClick = { onTabSelected(TabPage.Work) }
            )
        }
    }
}

/**
 * Shows an indicator for the tab.
 *
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param tabPage The [TabPage] that is currently selected.
 *
 * tabPositions 包含了TabRow中所有选项卡的位置信息
 * tabPositions[0]：读取第一个选项卡位置信息
 */
@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    // TODO 4: Animate these value changes.
    /**
     * 动画小节4 多值动画 对组件的颜色，位置，大小等属性同时进行改变并播放动画
     * = 使用updateTransition 创建过度动画 当targetState发生改变时 朝着指定目标值运行所有子动画 label是标签 注释
     * - 这里targetState就是tabPage
     * - 可以使用transition对象动态添加子动画：transition.animate*
     * - 这里的子动画包含3个需要修改的属性：就是指示器的左边位置，右边位置，颜色
     * - transition
     * */
    val transition = updateTransition(targetState = tabPage, label = "过渡动画")

    /**
     * 指示器平移动画
     * */
    /*//指示器左位置
    val indicatorLeft by transition.animateDp(label = "左边位置") { tabPage->
        tabPositions[tabPage.ordinal].left
    }
    //指示器右位置
    val indicatorRight by transition.animateDp(label = "右边位置") { tabPage->
        tabPositions[tabPage.ordinal].right
    }
    //指示器颜色
    val color by transition.animateColor(label = "颜色") {tabPage->
        if (tabPage == TabPage.Home) PaleDogwood else Green
    }*/
    /**
     * 指示器平移动画+弹性效果
     * 思路：向右时移动时右边距变化更快，向左移动时左边距变化更快 就可以达到效果
     * */
    //指示器左位置
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.Work) {
                //左边缘 向右移动时 变化慢点
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                //左边缘 向左移动时 变化快点
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "左边位置"
    ) { tabPage ->
        tabPositions[tabPage.ordinal].left
    }
    //指示器右位置
    val indicatorRight by transition.animateDp(transitionSpec = {
        if (TabPage.Home isTransitioningTo TabPage.Work) {
            //右边缘 向右移动时 变化快点
            spring(stiffness = Spring.StiffnessMedium)
        } else {
            //右边缘 向左移动时 变化慢点
            spring(stiffness = Spring.StiffnessVeryLow)
        }
    }, label = "右边位置") { tabPage ->
        tabPositions[tabPage.ordinal].right
    }
    //指示器颜色
    val color by transition.animateColor(label = "颜色") { tabPage ->
        if (tabPage == TabPage.Home) PaleDogwood else Green
    }

    //原代码（无动画）
    //指示器左位置
    //val indicatorLeft = tabPositions[tabPage.ordinal].left
    //指示器右位置
    //val indicatorRight = tabPositions[tabPage.ordinal].right
    //指示器颜色
    //val color = if (tabPage == TabPage.Home) PaleDogwood else Green

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                RoundedCornerShape(4.dp)
            )
    )
}

/**
 * Shows a tab.
 *
 * @param icon The icon to be shown on this tab.
 * @param title The title to be shown on this tab.
 * @param onClick Called when this tab is clicked.
 * @param modifier The [Modifier].
 */
@Composable
private fun HomeTab(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}

/**
 * Shows the weather.
 *
 * @param onRefresh Called when the refresh icon button is clicked.
 */
@Composable
private fun WeatherRow(
    onRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Amber600)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(R.string.temperature), fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh)
            )
        }
    }
}

/**
 * Shows the loading state of the weather.
 */
@Composable
private fun LoadingRow() {
    // TODO 5: Animate this value between 0f and 1f, then back to 0f repeatedly.
    /**
     * 动画小节5 rememberInfiniteTransition 重复动画 让alpha的值在0f-1f之间不断重复
     * */
    val infiniteTransition = rememberInfiniteTransition(label = "")
    //alpha变化 ： 0f->1f->0f->1f 总时长1秒
    //animateFloat返回的是State<Float>
    val alpha by infiniteTransition.animateFloat(initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
        //keyframes创建KeyframesSpec
        keyframes {
            //持续时间 1000毫秒
            durationMillis = 1000
            //指定关键帧 500毫秒时 value = 1f
            1f at 500
        }
    ), label = "无限重复的透明度动画")

    //原代码（无动画）
    //val alpha = 1f
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

/**
 * Shows a row for one task.
 *
 * @param task The task description.
 * @param onRemove Called when the task is swiped away and removed.
 */
@Composable
private fun TaskRow(task: String, onRemove: () -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .swipeToDismiss(onRemove),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = task,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/**
 * The modified element can be horizontally swiped away.
 *
 * @param onDismissed Called when the element is swiped to the edge of the screen.
 *
 * 动画小节6 通过手势动画实现滑动删除task效果
 */


private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    /**
     * TODO 6-1: Create an Animatable instance for the offset of the swiped element.
     * 创建一个修饰符，用于处理修改元素区域内的光标输入
     * 调用 PointerInputScope.awaitPointerEventScope可以安装等待PointerInputScope的光标输入处理程序
     * */
    //使用offsetX
    val offsetX = remember { Animatable(0f) }
    pointerInput(Unit) {

        //样条函数衰减器 物理角度来讲：投掷item从初始速度开始 越来越慢
        val decay = splineBasedDecay<Float>(this)

        //需要在协程的作用域中
        coroutineScope {
            while (true) {
                //等待触摸按下事件
                //awaitPointerEventScope:挂起并安装指针输入块 该块等待输入时间并立即响应它们
                //awaitFirstDown：读取事件 直到收到第一个down
                val pointerId = awaitPointerEventScope {
                    awaitFirstDown().id
                }
                //记录滑动的时间和距离
                val velocityTracker = VelocityTracker()

                awaitPointerEventScope {
                    //监听水平滑动 change为水滑动距离的变化
                    horizontalDrag(pointerId) { change ->
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        launch {
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        velocityTracker.addPosition(change.uptimeMillis, change.position)

                        //消费掉此手势事件 不再向上传递 解决横竖滑动冲突
                        change.consumePositionChange()
                    }
                }
                //计算滑动的速度 拖动完成 这就是我们投掷item的速度
                val velocity = velocityTracker.calculateVelocity().x
                //计算投掷的最终位置来决定是将元素归位还是滑动移除 最终位置=初始速度衰减为0后的距离
                val targetOffsetX = decay.calculateTargetValue(offsetX.value,velocity)
                //设置一下偏移量动画 避免滑出Task列表横向边界
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                if(targetOffsetX.absoluteValue<=size.width){
                    //滑动到原位置
                    offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                }else{
                    //滑动出去
                    offsetX.animateDecay(velocity,decay)
                    onDismissed()
                }
            }
        }
    }.offset { IntOffset(offsetX.value.roundToInt(), 0) }

    // TODO 6-1: Create an Animatable instance for the offset of the swiped element.
    /*pointerInput(Unit) {
        // Used to calculate a settling position of a fling animation.
        val decay = splineBasedDecay<Float>(this)
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // Wait for a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                // TODO 6-2: Touch detected; the animation should be stopped.
                // Prepare for drag events and record velocity of a fling.
                val velocityTracker = VelocityTracker()
                // Wait for drag events.
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // TODO 6-3: Apply the drag change to the Animatable offset.
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        // Consume the gesture event, not passed to external
                        if (change.positionChange() != Offset.Zero) change.consume()
                    }
                }
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // TODO 6-4: Calculate the eventual position where the fling should settle
                //           based on the current offset value and velocity
                // TODO 6-5: Set the upper and lower bounds so that the animation stops when it
                //           reaches the edge.
                launch {
                    // TODO 6-6: Slide back the element if the settling position does not go beyond
                    //           the size of the element. Remove the element if it does.
                }
            }
        }
    }.offset {
            // TODO 6-7: Use the animating offset value here.
            IntOffset(0, 0)
        }*/


}

@Preview
@Composable
private fun PreviewHomeTabBar() {
    AnimationCodelabTheme {
        HomeTabBar(
            backgroundColor = White,
            tabPage = TabPage.Home,
            onTabSelected = {}
        )
    }
}

@Preview
@Composable
private fun PreviewHome() {
    AnimationCodelabTheme {
        Home()
    }
}
