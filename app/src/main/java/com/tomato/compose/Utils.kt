package com.tomato.compose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 */
fun log(msg:String){
    Log.w("DemoTag",msg)
}

fun toast(msg:String){
    Toast.makeText(App.instance,msg, Toast.LENGTH_SHORT).show()
}


/**
 * 无点击反馈 无点击水波纹效果的点击监听
 * */
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier {
    return composed {
        this.clickable(
            interactionSource = remember { MutableInteractionSource() }, // 创建一个新的无反馈的交互源
            indication = null, // 禁用点击指示器
            onClick = onClick
        )
    }
}
