package com.tomato.compose.weijueye

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tomato.compose.R
import com.tomato.compose.bean.UserBean
import com.tomato.compose.dongnaoxueyuan.unit4effect.SampleEffectActivity
import com.tomato.compose.dongnaoxueyuan.unit5anim.SampleAnimActivity
import com.tomato.compose.log
import com.tomato.compose.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Created by Tomato on 2024/5/31.
 * Description：
 * 视频教程：https://www.bilibili.com/video/BV1Eb4y147zR
 * 博客地址：https://docs.bughub.icu/compose/
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun SampleOne(context: Context? = null) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {

       /* InputDialog(isShow = isShowInputDialog) {
            isShowInputDialog=false
        }*/

        UnitCard(title = "显示Dialog 输入框键盘适配") {

            var isShowInputDialog by remember {
                mutableStateOf(true)
            }

            Button(onClick = { isShowInputDialog=true }) {
                Text(text = "显示输入框")
            }

            InputDialog(isShow =isShowInputDialog ) {
                isShowInputDialog=false
            }
        }



        UnitCard(title = "isActive测试") {
            var isInitActiveCompose by remember {
                mutableStateOf(false)
            }
            Button(onClick = { isInitActiveCompose = !isInitActiveCompose }) {
                Text(text = "跳转->动画综合界面")
            }
            if(isInitActiveCompose){
                ActiveTest()
            }
        }

        UnitCard(title = "动画综合课程(动脑学院)") {
            Button(onClick = { context?.let { it.startActivity(Intent(it, SampleAnimActivity::class.java)) } }) {
                Text(text = "跳转->动画综合界面")
            }
        }


        UnitCard(title = "Effect副作用的用法(动脑学院)") {
            Button(onClick = { context?.let { it.startActivity(Intent(it, SampleEffectActivity::class.java)) } }) {
                Text(text = "跳转->Effect的使用")
            }
        }

        UnitCard(title = "Navigation导航的用法") {
            Button(onClick = { context?.let { it.startActivity(Intent(it, SampleNavigationActivity::class.java)) } }) {
                Text(text = "跳转->Navigation的使用")
            }
        }

        UnitCard(title = "animate*AsState 更基础的动画， AnimatedVisibility AnimatedContent都是基于此实现") {
            /***
             * animationSpec 理解为超级插值器 可以设置动画运行方式 回弹，先快后慢什么什么的
             * 除了spring（物理仿真动画）还有其他效果可选：
             * tween: 适用于大多数简单、基于时间的动画需求。
             * spring: 适用于需要物理仿真效果的动画。
             * keyframes: 适用于需要精确控制动画各个时间点具体值的动画。
             * repeatable: 适用于需要重复一定次数的动画。
             * infiniteRepeatable: 适用于需要无限次重复的动画。
             *
             *
             * animateFloatAsState 已经处理了内部状态管理和状态记忆，因此无需显式使用 remember。
             * 在日常使用中，这使得代码更加简洁和直观。
             * 在管理其他类型的状态时，remember 仍然是一个重要的工具，用于确保状态在重组过程中的一致性。
             * */
            var size by remember { mutableStateOf(60.dp) }
            val sizeAnim by animateDpAsState(targetValue = size, animationSpec = spring(Spring.DampingRatioHighBouncy))
            var color by remember { mutableStateOf(Color.Gray) }
            val colorAnim by animateColorAsState(targetValue = color)
            Icon(modifier = Modifier
                .size(sizeAnim)
                .clickable {
                    if (size == 120.dp) {
                        size = 60.dp
                        color = Color.Gray
                    } else {
                        size = 120.dp
                        color = Color.Red
                    }
                },
                tint = colorAnim, imageVector = Icons.Default.Favorite, contentDescription = null
            )

            Text(text = "换一种写法 通过isFavorite状态统一管理size和color")
            var isFavorite  by remember {
                mutableStateOf(false)
            }
            val favoriteSizeAnim by animateDpAsState(targetValue =  if (isFavorite) { 90.dp } else { 45.dp }, animationSpec = spring(Spring.DampingRatioHighBouncy))
            val favoriteColorAnim by animateColorAsState(targetValue = if (isFavorite) { Color.Red } else { Color.Gray })
            Icon(
                modifier = Modifier
                    .size(favoriteSizeAnim)
                    .clickable {
                        isFavorite = !isFavorite
                    },
                tint = favoriteColorAnim, imageVector = Icons.Default.Favorite, contentDescription = null
            )

        }

        UnitCard(title = "Modifier.animateContentSize() 组件宽高改变后自带动画") {
            var msg by remember {
                mutableStateOf("Hello World!")
            }
            Button(onClick = {
                msg = if (msg.length > 20) {
                    "Hello World!"
                } else {
                    "Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!Hello World!"

                }
            }) {
                Text(text = "Size改变动画")
            }
            Text(text = msg, modifier = Modifier.animateContentSize())
        }

        UnitCard(title = "AnimatedContent") {
            var count by remember {
                mutableStateOf(0)
            }
            Row {
                Button(onClick = { count-- }) {
                    Text(text = "减少")
                }
                Button(onClick = { count++ }) {
                    Text(text = "增加")
                }
            }
            //定义AnimatedContent内部状态改变的动画
            AnimatedContent(targetState = count,
                //自定义状态改变的状态
                transitionSpec = {
                    if (initialState > targetState) {
                        //初始值>目标值 表示减少
                        (slideInVertically { fullHeight -> fullHeight } + fadeIn())
                            .togetherWith(
                                slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                            )
                    } else {
                        //初始值>目标值 表示增加
                        (slideInVertically { fullHeight -> -fullHeight } + fadeIn())
                            .togetherWith(
                                slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
                            )
                    }
                }
            ) { targetCount ->
                Text(text = "今日步数${targetCount}")
            }
        }

        UnitCard(title = "AnimatedVisibility显示消失动画") {
            var imageVisible by remember {
                mutableStateOf(true)
            }
            Button(onClick = { imageVisible = !imageVisible }) {
                Text(text = "点击播放动画")
            }
            /***
             * AnimatedVisibility显示消失动画 可以通过enter和exit 分别设置显示和消失动画
             * 官方提供了一些内置动画 scaleIn scaleOut ，也可以自定义动画 自己拓展
             * ***/
            AnimatedVisibility(visible = imageVisible, enter = scaleIn(), exit = scaleOut()) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = painterResource(id = R.mipmap.iv_cover_happy), contentDescription = null
                )
            }

            /***
             * 在AnimatedVisibility作用域中
             * 也可以通过Modifier.animateEnterExit单独为某一个组件显示消失动画
             * 仅在AnimatedVisibility没有设置enter , exit 时生效
             * ***/
            AnimatedVisibility(visible = imageVisible) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .animateEnterExit(enter = slideInVertically(), exit = slideOutVertically()),
                    painter = painterResource(id = R.mipmap.iv_cover_happy), contentDescription = null
                )
            }


        }
        UnitCard(title = "Dialog用法") {
            /*val isShowLoading=false
            Dialog(onDismissRequest = { *//*TODO*//* }) {

            }*/

        }




        UnitCard(title = "LazyVerticalGrid表格列表的使用") {
            val list = listOf(
                "aaaaaaaa", "bbbb", "aaa", "ccccccccccc", "sssssssssssss", "aasda", "aa", "b",
                "cccccccccccccc", "aaaaaaaa", "bbbb", "aaa", "ccccccccccc", "sssssssssssss", "aasda", "aa", "b",
                "cccccccccccccc", "aaaaaaaa", "bbbb", "aaa", "ccccccccccc", "sssssssssssss", "aasda", "aa", "b",
                "cccccccccccccc"
            )
            //普通整齐表格
            //columns = 列数
            //GridCells.Fixed(3) 固定列数 3
            //GridCells.Adaptive(50.dp) 不指定列数 设置单元格最小宽度为50dp(根据屏幕宽度尽可能多的排列单元格进区)
            //GridCells.FixedSize (50.dp) 不指定列数 设置单元格固定宽度为50dp
            val state = rememberLazyGridState()
            LazyVerticalGrid(state = state,
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(200.dp),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp),  //设置内容上下左右padding
                verticalArrangement = Arrangement.spacedBy(10.dp), // 设置垂直方向项目之间的间距
                horizontalArrangement = Arrangement.spacedBy(10.dp) // 设置水平方向项目之间的间距
            ) {
                items(list) {
                    Text(text = it, modifier = Modifier.height(60.dp))
                }
            }
            //非固定表格 瀑布流表格 自己多研究一下
            /* LazyVerticalStaggeredGrid(columns = ) {
            }
             LazyHorizontalStaggeredGrid(rows = ) {

             }*/
        }


        UnitCard(title = "LazyRow列表的使用") {
            val list = listOf(1, 3, 4, 5, 6, 7, 8, 9, 10)
            LazyRow {
                stickyHeader {
                    Text(
                        text = "我是表头", modifier = Modifier
                            .size(100.dp)
                            .background(Color.Yellow)
                    )
                }
                items(list) {
                    Text(text = "index：${it}")
                }
            }
        }

        UnitCard(title = "LazyColumn列表的使用") {
            Button(onClick = { context?.let { it.startActivity(Intent(it, SampleListActivity::class.java)) } }) {
                Text(text = "跳转->列表的使用")
            }
        }

        UnitCard(title = "ListItem列表组件使用") {
            val userList = remember {
                mutableStateListOf<UserBean>(
                    UserBean("Name", "+86 12345678", false),
                    UserBean("Name", "+86 12345678", false),
                    UserBean("Name", "+86 12345678", false)
                )
            }
            Column {
                userList.forEachIndexed { index, userBean ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = null)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = userBean.name)
                            Text(text = userBean.number)
                        }
                        Checkbox(checked = userBean.isCheck, onCheckedChange = {
                            userList[index] = userList[index].copy(isCheck = it)
                        })
                    }
                }
            }
            Text(text = "ListItem就是一个模板组件 一个常见的列表item样式 填充不同的列表内容 不建议使用 最好自己写")
            Image(painter = painterResource(id = R.mipmap.eg_listitem), contentDescription = null)
        }

        UnitCard(title = "CheckBox单选框") {
            var isCheck by remember {
                mutableStateOf(false)
            }
            Checkbox(checked = isCheck, onCheckedChange = { isCheck = it })
        }

        UnitCard(title = "RadioButton单选按钮基本用法") {
            Text(text = "单选：")
            var isSelect by remember {
                mutableStateOf(false)
            }
            RadioButton(selected = isSelect, onClick = { isSelect = !isSelect })

            Text(text = "多选：")
            val checkedList = remember {
                mutableStateListOf<Boolean>(false, false)
            }
            Column {
                checkedList.forEachIndexed { index, b ->
                    RadioButton(selected = b, onClick = {
                        checkedList[index] = !checkedList[index]
                    })
                }
            }

        }

        UnitCard(title = "Divider分割线基本用法") {
            Column(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Green.copy(alpha = 0.1f)),
                //纵向排列方式
                verticalArrangement = Arrangement.Top,
                //横向对齐方式
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "第一行 item", modifier = Modifier.background(Color.Red.copy(alpha = 0.1f)))
                //分割线可以设置「Color ：颜色」和 「thickness：厚度」->Column中是高度 Row是宽度
                Divider(color = Color.Yellow, thickness = 3.dp)
                Text(text = "第二行 item", modifier = Modifier.background(Color.Blue.copy(alpha = 0.1f)))
            }
        }

        UnitCard(title = "Spacer基本用法 当成一个空View来用，可以当成margin来使用") {
            Column(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Green.copy(alpha = 0.1f)),
                //纵向排列方式
                verticalArrangement = Arrangement.Top,
                //横向对齐方式
                horizontalAlignment = Alignment.Start
            ) {
                //ColumnScope中可以使用Modifier.weight设置比重
                Text(text = "第一行 item", modifier = Modifier.background(Color.Red.copy(alpha = 0.1f)))
                //Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "第二行 item", modifier = Modifier.background(Color.Blue.copy(alpha = 0.1f)))
            }
        }

        UnitCard(title = "Row基本用法 用法和Colum差不多") {
            Row(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Green.copy(alpha = 0.1f)),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "第一列 item")
                Text(text = "第二列 item")
            }
            Text(text = "横向排列方式horizontalArrangement :Arrangement七个枚举值的效果：")
            Image(painter = painterResource(id = R.mipmap.eg_hor_arrangement_type), contentDescription = null)
        }

        UnitCard(title = "Colum基本用法 纵向布局") {
            Column(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Green.copy(alpha = 0.1f)),
                //纵向排列方式
                verticalArrangement = Arrangement.Bottom,
                //横向对齐方式
                horizontalAlignment = Alignment.Start
            ) {
                //ColumnScope中可以使用Modifier.weight设置比重
                Text(
                    text = "第一行 item 权重weight=1f", modifier = Modifier
                        .weight(1f)
                        .background(Color.Red.copy(alpha = 0.1f))
                )
                Text(text = "第二行 item")
            }
            Text(text = "纵向排列方式verticalArrangement :Arrangement七个枚举值的效果：")
            Image(painter = painterResource(id = R.mipmap.eg_ver_arrangement_type), contentDescription = null)
        }
        UnitCard(title = "Box基本用法") {
            Box(modifier = Modifier.background(Color.Green.copy(alpha = 0.1f))) {

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.Red.copy(alpha = 0.5f))
                )
                //可以通过align设置Box子组件对齐位置 align 可以设置9个位置 竖直：上中下 水平：左中右 两两组合
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Yellow.copy(alpha = 0.5f))
                        .align(Alignment.Center)
                )
                Text(text = "Text 1", modifier = Modifier.align(Alignment.TopEnd))
                Text(text = "Text 2", modifier = Modifier.align(Alignment.CenterStart))
            }

            //带约束的Box->BoxWithConstraints
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                //其作用域中多了几个属性
                log("maxWidth:$maxWidth  maxHeight:$maxHeight ")
                if (maxWidth > maxHeight) {
                    Text(text = "当前横屏")
                } else {
                    Text(text = "当前竖屏")
                }
            }
        }

        UnitCard(title = "Card基本用法") {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Yellow
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Text(modifier = Modifier.padding(30.dp), text = "WWWWW")
            }
        }
        UnitCard(title = "Slider基本用法(对应SeekBar)") {
            //进度条进度 强制0~1之间
            var progress by remember {
                mutableStateOf(0.5f)
            }
            //单点选择
            Slider(
                value = progress,
                onValueChange = {
                    progress = it
                },
                steps = 6,
                //修改选择条各个部分的颜色
                colors = SliderDefaults.colors(thumbColor = Color.Yellow)
            )
            //范围选择
            var progressRange by remember {
                mutableStateOf(0.3f..0.8f)
            }
            RangeSlider(
                value = progressRange,
                onValueChange = {
                    progressRange = it
                },
            )
        }

        UnitCard(title = "进度条基本用法") {
            //不设置进度条 则
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                color = Color.Red.copy(alpha = 0.5f),//进度条颜色
                trackColor = Color.Yellow,//轨道颜色
                strokeWidth = 5.dp,//进度条宽度
                strokeCap = StrokeCap.Round//进度条圆角
            )
            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(listOf(Color.Red, Color.Yellow)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(5.dp)
                    .height(5.dp)
                    .width(300.dp),
                color = Color.Red.copy(alpha = 0.5f),
                trackColor = Color.Yellow,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(10.dp))

            //进度条进度 强制0~1之间
            var progress by remember {
                mutableStateOf(0.5f)
            }
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                progress = progress,
                color = Color.Red,
                strokeWidth = 5.dp,//进度条宽度
                strokeCap = StrokeCap.Round//进度条圆角
            )
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(listOf(Color.Red, Color.Yellow)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(5.dp)
                    .height(5.dp)
                    .width(300.dp),
                color = Color.Red,
                trackColor = Color.Yellow,
                strokeCap = StrokeCap.Round
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                val anim = ObjectAnimator.ofFloat(0f, 1f)
                anim.addUpdateListener {
                    progress = (it.animatedValue as Float)
                }
                anim.duration = 3000L
                anim.start()
            }) {
                Text(text = "开始跑进度条")
            }

        }



        UnitCard(title = "TextField输入框的基本用法") {
            val (inputValue, setInputValue) = remember {
                mutableStateOf("")
            }
            //键盘控制 用于显示or隐藏键盘
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                value = inputValue,
                onValueChange = setInputValue,
                //输入标签
                label = {
                    Text(text = "姓名：")
                },
                //输入提示
                placeholder = {
                    Text(text = "请输入姓名")
                },
                //
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.SafetyCheck, contentDescription = null)
                },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,//设置键盘动作 例如：确认 搜索 回车等等
                    keyboardType = KeyboardType.Password//设置键盘类型例如：数字密码什么什么的
                ),
                //处理键盘的动作
                keyboardActions = KeyboardActions(onDone = {
                    toast("Keyboard onDone")
                    keyboardController?.hide()
                }),
                maxLines = 1,
            )
            //Jetpack Compose提供了不同样式的输入框 边框输入框
            //当然 还有BasicTextField更基础更原始的输入框 便于自定义
            OutlinedTextField(
                value = inputValue,
                onValueChange = setInputValue,
                //输入标签
                label = {
                    Text(text = "姓名：")
                },
                //输入提示
                placeholder = {
                    Text(text = "请输入姓名")
                },
                //
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.SafetyCheck, contentDescription = null)
                },

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,//设置键盘动作 例如：确认 搜索 回车等等
                    keyboardType = KeyboardType.Password//设置键盘类型例如：数字密码什么什么的
                ),
                //处理键盘的动作
                keyboardActions = KeyboardActions(onDone = {
                    toast("Keyboard onDone")
                    keyboardController?.hide()
                }),
                maxLines = 1,
            )
        }

        UnitCard(title = "Switch基本用法") {
            var isChecked by remember {
                mutableStateOf(false)
            }
            Switch(checked = isChecked, onCheckedChange = {
                isChecked = it
            })
        }

        UnitCard(title = "State用法") {
            log("重组UnitCard State用法")
            /*val (parentCount, setParentCount) = remember {
                mutableStateOf(0)
            }*/
            var parentCount by remember {
                mutableStateOf(0)
            }
            //状态提升：从子组件中提取状态，通过参数的形式传入 如果子组件修改状态 则必须要有参数：value 和 setValue
            StateSample(parentCount) {
                parentCount++
            }
        }
        UnitCard(title = "Image基本用法") {
            val ivPainter = painterResource(id = R.mipmap.iv_cover_happy)
            Image(
                modifier = Modifier
                    .size(width = 200.dp, height = 100.dp)
                    //剪裁圆角
                    .clip(RoundedCornerShape(8.dp))
                //通过Modifier设置Image宽高比 可达到效果：android:adjustViewBounds="true"
                //.aspectRatio(ivPainter.intrinsicSize.width / ivPainter.intrinsicSize.height)
                ,
                painter = ivPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop, // 填充方式 类比：scaleType
                colorFilter = ColorFilter.tint(Color.Red, blendMode = BlendMode.Color),// 设置滤镜
                alignment = Alignment.Center//图片对齐方式
            )
        }

        UnitCard(title = "Icon基本用法") {
            //可以使用官方的图片库
            Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
            //可以从资源中读取图标
            Icon(
                painter = painterResource(id = R.drawable.ic_setting),
                tint = Color.Red,//tint可以设置图标填充色 图标都是纯色的
                contentDescription = null
            )
        }

        UnitCard("Button基本用法") {
            /**
             * Button基本用法 button大家族 不同的button之间基本用法一样 根据需求样式可以灵活选择样式
             * */
            Button(
                onClick = { toast("点击了Button") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,//设置Button内容颜色
                    containerColor = Color.Yellow//设置Button容器为黄色
                )
            ) {
                Text(text = "普通Button")
            }

            TextButton(onClick = { toast("点击了TextButton") }) {
                Text(text = "TextButton")
            }

            OutlinedButton(onClick = { toast("点击了OutlinedButton") }) {
                Text(text = "OutlinedButton")
            }

            IconButton(onClick = { toast("点击了IconButton") }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }

        }

        UnitCard("Modifier基本用法") {
            /**
             * Modifier基本用法
             * */
            Text(
                text = "Modifier介绍",
                //style = TextStyle(background = Color.Blue),//Text可以使用TextStyle设置背景 但不是所有控件都有TextStyle 所以也可以使用modifier
                modifier = Modifier //Modifier方法调用顺序会直接影响到实际效果 根据实际情况调用
                    .background(Color.Yellow) //设置背景
                    .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(10.dp))//设置边框 shape设置形状
                    .padding(8.dp) //设置边距
                    .clickable {
                        toast("点击到我了")
                    }
                //.offset(x=10.dp) //相对于当前位置偏移
            )
        }
        UnitCard("Text 文本基础用法") {
            /**
             * Text 文本基础用法
             * */
            //Text置于SelectionContainer中 可以让文本可可选择 用于复制等操作
            SelectionContainer {
                Text(
                    text = stringResource(id = R.string.text_sample_1),//文本内容 可以直接设置 也可以stringResource读取资源
                    color = Color(255, 0, 255),//文本颜色
                    fontSize = 16.sp,//文本字体大小
                    fontFamily = FontFamily.Serif,//设置字体 可以使用官方预设的字体 也可以使用ttf文件作为自定义字体
                    letterSpacing = 10.sp,//文本字符间距
                    textDecoration = TextDecoration.Underline,//文字装饰线 删除线，下划线 也可以TextDecoration.combine(listOf(TextDecoration.Underline, TextDecoration.LineThrough))合并多个
                    textAlign = TextAlign.End,//文字对齐方式 居中，居左，居右
                    lineHeight = 30.sp, //行高
                    maxLines = 2,//最大行数
                    overflow = TextOverflow.Ellipsis,//省略方式 剪裁，省略号，不省略。目前不支持像TextView中间添加省略号
                    style = LocalTextStyle.current// TextStyle 可以内部包含了前边设置字体的所有属性，还有shadow等特性，可以统一设置多个Text的字体
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            /**
             * Text 富文本用法
             * */
            val annotatedString = buildAnnotatedString {
                append("点击登录即代表您已阅读并同意")
                /**
                 * push 和 pop一般成对出现 push代表开始  pop代表结束
                 * pushStringAnnotation 添加注解标识 直到pop结束
                 * pushStyle 添加style  直到pop结束
                 * */
                pushStringAnnotation("terms_url", "https://com.baidu.com")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("用户协议")
                }
                pop()
                append("和")
                pushStringAnnotation("privacy_url", "https://com.baidu.com")
                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                    append("隐私政策")
                }
                pop()
            }
            ClickableText(text = annotatedString, onClick = { index ->
                //根据点击位置 和 tag 从字符串中查找注解
                annotatedString.getStringAnnotations("terms_url", start = index, end = index).firstOrNull()?.let {
                    toast("点击${it.tag} ${it.item}")
                }
                annotatedString.getStringAnnotations("privacy_url", start = index, end = index).firstOrNull()?.let {
                    toast("点击${it.tag} ${it.item}")
                }
            })
        }
    }

}


/**
 * 没有在Composable内的变量 则不需要使用remember
 * 例如viewmodel内
 * */
var globalCount by mutableStateOf(0) //通过mutableStateOf来创建一个可观察的状态对象

@Composable
private fun StateSample(parentCount: Int, setParent: (Int) -> Unit) {
    log("重组UnitCard StateSample")

    /***
     * 如果是在Composable内的变量
     * 需要使用remember来记住这个变量的值
     * 防止Composable重组后导致变量被重新赋值
     * **/
    var count by remember {
        mutableStateOf(0) //通过mutableStateOf来创建一个可观察的状态对象
    }
    Button(onClick = {
        count++
        globalCount++
        setParent(count)
    }
    ) {
        Text(text = "点击次数记录：子组件${count} 全局记录：${globalCount} 父组件记录：${parentCount}")
    }
}


@Composable
fun UnitCard(title: String, content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        Surface(color = Color.White, modifier = Modifier.fillMaxWidth(), shadowElevation = 2.dp, shape = RoundedCornerShape(10.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = title, color = Color.Gray)
                Spacer(modifier = Modifier.height(10.dp))
                content()
            }
        }
    }
}


@Composable
fun ActiveTest() {
    var number by remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        log("LaunchedEffect isActive1 ---> $isActive ")
        while (isActive) {
            delay(200L)
            number++
            log("number-> $number")

        }
        /*delay(200L)
        scope.launch {
            log("scope isActive ---> $isActive ")
            while (isActive) {
                delay(200L)
                number++
                log("number-> $number")

            }
        }*/
        log("LaunchedEffect isActive2 ---> $isActive ")
    }
    DisposableEffect(Unit)  {
        log("DisposableEffect init")
        onDispose {
            log("DisposableEffect onDispose ")
        }
    }
    Text(text = "count: $number")


}







@Composable
fun FullImeScreenPopup(onDismissRequest: () -> Unit, content: @Composable BoxScope.() -> Unit) {
    //imePadding在Dialog中不生效 所以自建一个dialog处理一下返回事件 点击返回消失
    //.imePadding()
    //.imePadding()   .safeDrawingPadding() 二选一设置都可以 safeDrawingPadding除了适配键盘还适配了底部导航栏
    Box(
        Modifier
            .imePadding()
            .safeDrawingPadding()
            .background(Color.Black.copy(alpha = 0.4f))
            .fillMaxSize()
            .clickable { }
    ) {
        content()
    }
    BackHandler {
        onDismissRequest()
    }
}



@Composable
fun InputDialog(isShow:Boolean,onDismissRequest:()->Unit) {
    if(isShow){
        Dialog(onDismissRequest = onDismissRequest,
            //usePlatformDefaultWidth = false 解除两边的宽度。
            //decorFitsSystemWindows= false 可以禁止强行装饰系统窗口，从而可以通过imePadding自动适配键盘，但黑色背景就没有了 最好是有输入框的时候进行设置
            //imePadding  .safeDrawingPadding() 在 Dialog中 decorFitsSystemWindows= true 不生效
            //.imePadding() .safeDrawingPadding() 二选一设置都可以 safeDrawingPadding除了适配键盘还适配了底部导航栏
            DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
        ) {
            Box (modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f))
                .safeDrawingPadding()
            ){
                EditFolderNamePopup(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    , onCancel = onDismissRequest)
            }
        }

    }
}




@Composable
fun EditFolderNamePopup( modifier: Modifier = Modifier, onCancel: () -> Unit = {}, onConfirm: (folderName: String) -> Unit = {}) {
    val (inputValue, setInputValue) = remember {
        mutableStateOf( "")
    }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material3.Text(
                text ="123123", fontSize = 18.sp, color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputValue, onValueChange = setInputValue, label = {
                    androidx.compose.material3.Text(text = "123123", color = Color.Black)
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedContainerColor = Color(0xFFF3F3F3),
                    unfocusedContainerColor = Color(0xFFF3F3F3),
                    focusedLabelColor = Color.Transparent
                )
            )
            Row(
                modifier = Modifier.padding(top = 30.dp)
            ) {
                val btnModifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                Button(onClick = {
                    onCancel()
                }) {
                    androidx.compose.material.Text(text = "关闭")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {

                }) {
                    androidx.compose.material.Text(text = "关闭")
                }
            }
        }
    }
}




