package com.tomato.compose.unit2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/**
 * Created by Tomato on 2024/5/21.
 * Description：
 *  * 自定义布局2 P19
 *  MyOwnColum 模仿Colum 进行布局
 *
 *
 *
 */

@Composable
fun MyOwnColum(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurable, constraints ->
        //测量元素
        val placeables = measurable.map { m ->
            m.measure(constraints)
        }

        var yPosition =0

        //布局大小
        layout(constraints.maxWidth, constraints.maxHeight) {
            //排列元素 和自定义ViewGroup很像
            placeables.forEach {placeable ->
                //依次排列 x都为0 y为之前的元素高度累加
                placeable.placeRelative(x=0,y= yPosition)
                yPosition+=placeable.height
            }

        }
    }
}


@Composable
fun MyOwnColumnSimple() {
    MyOwnColum{
        Text(text = "1")
        Text(text = "2")
        Text(text = "3")
        Text(text = "4")
    }


}