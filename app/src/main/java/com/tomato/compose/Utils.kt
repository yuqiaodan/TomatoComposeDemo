package com.tomato.compose

import android.util.Log
import android.widget.Toast

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