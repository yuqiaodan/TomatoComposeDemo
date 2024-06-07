package com.tomato.compose.dongnaoxueyuan.unit4effect

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomato.compose.weijueye.UnitCard
import kotlinx.coroutines.delay

/**
 * P78
 * Effect Api ：副作用（这个副作用的解释范围很宽泛 对应到代码中进行理解）
 * Compose组合函数声明周期：  对应的Effect Api
 * 挂载到树上：Enter  -  LaunchedEffect (第一次调用compose时调用)
 * 重组刷新（组合n次）：Composition - SideEffect (每次重组时调用)
 * 从树上移除，不再显示：Leave - DisposableEffect  (内部有onDestroy()函数,移除时调用)
 *

 * */
class SampleEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageContent()
        }
    }

    @Composable
    fun PageContent() {
        LaunchedEffectSample()
        CoroutineScopeSample()
    }


    /**
     * androidx.compose.material3.Scaffold 的Scaffold才有状态管理
     *
     *
     * */
    @Composable
    fun CoroutineScopeSample() {

        rememberScaffoldState()

        Scaffold { paddingValues ->
        }


    }


    /***
     * 个人理解：
     *
     * LaunchedEffect(Unit)
     * compose重组时执行一次
     *
     * LaunchedEffect(key1:Any?,...)
     * compose重组时执行一次 后续只要LaunchedEffect的参数Key发生了改变，就会执行LaunchedEffect
     * 多次重复执行时 会将上一次取消 再重新执行
     *
     * */
    @Composable
    fun LaunchedEffectSample() {

        var isPlayingCountUp by remember {
            mutableStateOf(false)
        }
        var countUp by remember {
            mutableIntStateOf(0)
        }
        /***
         *  仅首次重组执行 启动一个协程 执行block
         *  Unit :只有一个值的类型：Unit 对象。此类型对应于 Java 中的 void 类型。
         *  可以用于异步请求数据 广告等业务 ，根据需求来完成逻辑，服务于为此compose准备一次性数据
         *  compose销毁后 自动cancel
         * **/
        LaunchedEffect(Unit) {
            delay(3000L)
            if (!isPlayingCountUp) {
                isPlayingCountUp = true
                Toast.makeText(this@SampleEffectActivity, "LaunchedEffectSample:首次组合3秒后自动开始倒计时", Toast.LENGTH_SHORT).show()
            }
        }

        /***
         * 首次重组和isPlayingCountUp改变 都会执行 启动一个协程 执行block
         * 连续触发执行 会先cancel掉上一个协程再执行block
         * compose销毁后 自动cancel
         * **/
        LaunchedEffect(key1 = isPlayingCountUp) {
            if (isPlayingCountUp) {
                while (true) {
                    delay(1000L)
                    countUp++
                }
            } else {
                countUp = 0
            }
            delay(1000L)
        }

        Column {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(text = countUp.toString(), color = Color.White, fontSize = 30.sp)
            }
            Button(onClick = { isPlayingCountUp = !isPlayingCountUp }) {
                Text(
                    text = if (isPlayingCountUp) {
                        "停止计时"
                    } else {
                        "开始计时"
                    }
                )
            }
        }
    }


}
