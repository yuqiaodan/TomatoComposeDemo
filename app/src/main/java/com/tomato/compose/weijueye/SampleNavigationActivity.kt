package com.tomato.compose.weijueye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class SampleNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationContent()
        }
    }

    @Composable
    fun NavigationContent() {
        /***
         * navController 导航控制器
         * startDestination 起始页描述
         * buildr : NavGraphBuilder.() -> Unit  创建导航内容
         * NavHost 可以理解为一个导航视窗 它也是一个组件 不一定占满全屏 例如可以作为一个Pager 通过tab来控制导航视窗显示什么内容
         */
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "First") {
            composable(route = "First") {
                FirstScreen {
                    /**
                     * navController.navigate 导航到指定页面
                     * */
                    navController.navigate("Second/番茄/18")
                }
            }
            /**
             * route 路由名称 后边添加/ 可以设置接受参数
             * 直接添加弹出默认就是string
             * 如果想设置参数类型，参数默认值，参数是否可为空 则需要通过arguments进行设置
             * */
            composable(route = "Second/{name}/{age}", arguments = listOf(
                navArgument("age") {
                    type = NavType.IntType
                    defaultValue = 22
                }
            )) {
                //
                val name = it.arguments?.getString("name")
                val age = it.arguments?.getInt("age")
                SecondScreen(name, age) { navController.navigate("Third") }
            }
            composable(route = "Third") {
                ThirdScreen {
                    /**
                     * //返回一页
                     * navController.popBackStack()
                     *
                     * //返回到指定页面
                     * route -> 指定路由
                     * inclusive -> 在返回的过程中是否包括销毁指定路由 大概这样理解
                     * navController.popBackStack("First",false)
                     * */
                    navController.popBackStack("First", false)
                }
            }
        }
    }

    @Composable
    fun FirstScreen(gotoSecondPage: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red.copy(alpha = 0.1f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "第一个页面")
            Button(onClick = gotoSecondPage) {
                Text(text = "跳转到第二页")
            }
        }
    }

    @Composable
    fun SecondScreen(name: String?, age: Int?, gotoThirdPage: () -> Unit) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green.copy(alpha = 0.1f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "第二个页面 \nname->${name} \nage->${age}")
            Button(onClick = gotoThirdPage) {
                Text(text = "跳转到第三页")
            }
        }
    }

    @Composable
    fun ThirdScreen(onBack: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue.copy(alpha = 0.1f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "第三个页面")
            Button(onClick = onBack) {
                Text(text = "回到第一页")
            }
        }
    }

}

