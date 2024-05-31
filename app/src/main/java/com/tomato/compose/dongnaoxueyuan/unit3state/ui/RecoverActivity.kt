package com.tomato.compose.dongnaoxueyuan.unit3state.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.versionedparcelable.ParcelImpl
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme

/**
 * P40状态恢复 暂时跳过
 * 恢复activity
 * 可以使用rememberSaveable保存临时数据
 * 界面在重新创建后 仍然可以读取到原数据 例如旋转之后
 * **/
class RecoverActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatoComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CityScreen()
                }
            }
        }
    }


    @Preview
    @Composable
    fun CityScreen(){
        var city by remember {
            mutableStateOf(City("ChengDu","Chain"))
        }
        /*var city by rememberSaveable {
            mutableStateOf(City("ChengDu","Chain"))
        }*/

        Column {
            TextButton(onClick = {
                city = City("New Yourk","US")
            }) {
                Text(text = "Change City")
            }

            Text(text = city.name)
        }
    }




    data class City(val name:String,val country:String)
}
