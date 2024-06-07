package com.tomato.compose.dongnaoxueyuan.unit2widget

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme

/**
 * Created by Tomato on 2024/5/21.
 * Description：
 */

/**
 * 自定义布局1
 * 可以自定义拓展方法 添加新的侧量方式
 * P18 不理解 没听懂 先放一下
 * */
fun Modifier.firstBaselineToTop(
    firstBaseLine: Dp
) = this.then(
    layout { measurable, constraints ->
        //可以在此处修改元素的测量方式
        val placeable = measurable.measure(constraints)
        val baseline = placeable[FirstBaseline]
        layout(0, 0) {}
    }
)

@Composable
fun TextWithPaddingToBaseline() {
    TomatoComposeDemoTheme {
        Text(text = "Hi there", modifier = Modifier
            .firstBaselineToTop(10.dp)
            .background(Color.Yellow))
    }
}