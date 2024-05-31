package com.tomato.compose.weijueye

import android.animation.ObjectAnimator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.SafetyCheck
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.tomato.compose.R
import com.tomato.compose.log
import com.tomato.compose.toast

/**
 * Created by Tomato on 2024/5/31.
 * Description：
 * 视频教程：https://www.bilibili.com/video/BV1Eb4y147zR
 * 博客地址：https://docs.bughub.icu/compose/
 */
@Preview(showSystemUi = true)
@Composable
fun SampleOne() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        UnitCard(title = "Box基本用法") {
            Box (modifier=Modifier.background(Color.Green.copy(alpha = 0.1f))){

                Box(modifier = Modifier
                    .size(200.dp)
                    .background(Color.Red.copy(alpha = 0.5f)))
                //可以通过align设置Box子组件对齐位置 align 可以设置9个位置 竖直：上中下 水平：左中右 两两组合
                Box(modifier = Modifier
                    .size(100.dp)
                    .background(Color.Yellow.copy(alpha = 0.5f))
                    .align(Alignment.Center))
                Text(text = "Text 1",modifier = Modifier.align(Alignment.TopEnd))
                Text(text = "Text 2",modifier = Modifier.align(Alignment.CenterStart))
            }

            //带约束的Box->BoxWithConstraints
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                //其作用域中多了几个属性
                log("maxWidth:$maxWidth  maxHeight:$maxHeight " )
                if(maxWidth>maxHeight){
                    Text(text = "当前横屏")
                }else{
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
                modifier = Modifier.size(width = 200.dp, height = 100.dp)
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
private fun UnitCard(title: String, content: @Composable () -> Unit) {
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

