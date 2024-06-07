package com.tomato.compose.dongnaoxueyuan.unit2widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 * Intrinsics
 * Compose只测量子元素一次，测量两次会发生异常，但有时在测量子元素之前，我们需要一些关于子元素的信息
 * Intrinsics允许在实际测量之前查询子元素
 */
class P24IntrinsicsStudy {
    @Preview
    @Composable
    fun TowTexts(modifier: Modifier = Modifier) {
        //设置容器为最小值 包含内部组件高度
        //IntrinsicSize 可以获取到内部两个文本高度 然后决定Row的高度
        Row(modifier = modifier.background(Color.White).height(IntrinsicSize.Min)) {
            Text(
                text = "Hi", modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Divider(color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp))
            Text(
                text = "There", modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}