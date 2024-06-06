package com.tomato.compose.dongnaoxueyuan.unit2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

/**
 * Created by Tomato on 2024/5/21.
 * Description：
 * Compose中也有约束布局 很关键 非常常用 P22
 * 创建引用：多次赋值 createRefs()   或单次赋值：createRefFor()
 * 约束条件：constrainAs() lintTo()
 * parent是一个现有的引用 指ConstraintLayout本身
 */

@Preview
@Composable
fun ConstrainLayoutContent1() {
    ConstraintLayout(modifier = Modifier.background(Color.White)) {
        val (button, text) = createRefs()
        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button) {
            top.linkTo(parent.top, margin = 16.dp)
            /*verticalChainWeight
            bottom.linkTo(parent.bottom,)*/
        }) {
            Text(text = "Button")
        }

        Text(text = "Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
    }
}

@Preview
@Composable
fun ConstrainLayoutContent2() {
    ConstraintLayout(modifier = Modifier.background(Color.White)) {
        val (button1, button2, text1, text2) = createRefs()
        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
        }) {
            Text(text = "Button1")
        }



        Text(text = "Text", modifier = Modifier.constrainAs(text1) {
            top.linkTo(button1.bottom, margin = 16.dp)
            //文本中间对其Btn1的end
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text1)

        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(barrier)
            end.linkTo(parent.end)
        }) {
            Text(text = "Button2")
        }
        //宽高1：1例子
        Text(
            text = "Square Text",
            modifier = Modifier.constrainAs(text2) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.ratio("1:1") // 设置宽高比为1:1
                height = Dimension.fillToConstraints // 高度填充到约束条件
            }
        )
    }
}

@Preview
@Composable
fun ConstrainLayoutContent3() {
    ConstraintLayout(modifier = Modifier.background(Color.White)) {
        val guideLine = createGuidelineFromStart(0.5f)
        val text = createRef()
        Text(
            modifier = Modifier.constrainAs(text) {
                start.linkTo(guideLine)
                end.linkTo(parent.end)
                width = Dimension.preferredWrapContent
                height = Dimension.preferredWrapContent
            },
            text = "这是一个很长很长很长的字符串这是一个很长很长很长的字符串这是一个很长很长很长的字符串这是一个很长很长很长的字符串这是一个很长很长很长的字符串这是"
        )
    }
}


/**
 * 解耦API
 * 约束条件解耦 将约束条件和和布局内容分开
 * */
@Preview
@Composable
fun ConstrainLayoutContent4() {
    BoxWithConstraints {
        val constraintSet = if (maxWidth < maxHeight) {
            //竖屏 返回16dp的布局 margin=16dp
            decoupledConstraints(16.dp)
        } else {
            //横屏 返回160dp的布局 margin=16d0p
            decoupledConstraints(160.dp)
        }
        ConstraintLayout(constraintSet = constraintSet, modifier = Modifier.background(Color.White)) {
            Button(onClick = { }, modifier = Modifier.layoutId("button")) {
                Text(text = "Button")
            }
            Text(text = "Text", modifier = Modifier.layoutId("text"))
        }
    }


}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")
        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
        }
    }
}