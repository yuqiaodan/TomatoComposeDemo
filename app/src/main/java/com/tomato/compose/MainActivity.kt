package com.tomato.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tomato.compose.weijueye.EditFolderNamePopup
import com.tomato.compose.weijueye.SampleOne

class MainActivity : ComponentActivity() {
    var isShowInputDialog by  mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleOne(this){
                isShowInputDialog =true
            }
            InputDialog(isShowInputDialog,{isShowInputDialog=false})
        }

        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                log("MainActivity handleOnBackPressed")
                //onBackPressedDispatcher.addCallback()
                //isEnabled=false
                remove()
                onBackPressedDispatcher.onBackPressed()
            }})
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
        log("MainActivity onBackPressed")
    }*/




}

@Composable
fun InputDialog(isShow:Boolean,onDismissRequest:()->Unit) {
    if(isShow){
        Dialog(onDismissRequest = onDismissRequest,
            //usePlatformDefaultWidth = false 解除两边的宽度。
            //decorFitsSystemWindows= false 可以禁止强行装饰系统窗口，从而自动适配键盘，但黑色背景就没有了 最好是有输入框的时候进行设置
            DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
        ) {
            Box (modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f))
            ){
                EditFolderNamePopup(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding(), onCancel = onDismissRequest)
            }
        }

    }
}



