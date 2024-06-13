package com.tomato.compose.dongnaoxueyuan.unit4effect

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomato.compose.R
import com.tomato.compose.log
import com.tomato.compose.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        /**示例1：使用LaunchedEffect **/
        //LaunchedEffectSample()
        /**示例2：使用rememberCoroutineScope **/
        //RememberCoroutineScopeSample()
        /**示例3：使用rememberUpdatedState **/
        //RememberUpdatedStateStamp()
        /**示例4：使用 DisposeEffect **/
        //DisposeEffectSample(onBackPressedDispatcher)
        /**示例5：使用SlideEffect **/
        //SideEffectSample()
        /**示例6：使用produceState **/
        //ProduceStateSample()
        /**示例7：使用derivedStateOf **/
        DerivedStateOfSample()
    }

    /** ========== 示例7==========*
     * 如果某个状态是从另一个状态计算或衍生获得 可以使用derivedStateOf
     * 当任意一个条件状态更新时，结果状态都会重新计算
     *
     * eg:例如 我这里有一个todo列表 item列表包含高优先级关键词是置顶
     */
    @Composable
    fun DerivedStateOfSample(highKeyWord: List<String> = listOf("review", "aaa")) {
        val todoTask = remember {
            arrayListOf<String>()
        }
        //todoTask或highKeyWord改变后 重新生成highTask
        val highTask = remember(todoTask.size, highKeyWord) {
            derivedStateOf {
                todoTask.filter {task->
                    var isHigh = false
                    highKeyWord.forEach { key ->
                        if (task.contains(key)) {
                            isHigh = true
                        }
                    }
                    isHigh
                }
            }
        }

        var editText by remember {
            mutableStateOf("")
        }



        Column {
            Row {
                TextField(value = editText, onValueChange = {
                    editText = it
                })
                Button(onClick = {
                    todoTask.add(editText)
                    editText = ""
                }) {
                    Text(text = "Add")
                }
            }
            LazyColumn {
                items(highTask.value) {
                    Text(
                        text = it, modifier = Modifier
                            .height(30.dp)
                            .fillMaxHeight(),
                        color = Color.Red
                    )
                }
                items(todoTask) {
                    Text(
                        text = it, modifier = Modifier
                            .height(30.dp)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }


    /** ========== 示例6 ==========*
     *
     * produceState:可以将非Compose状态转换为Compose状态
     * produceState的作用比较抽象，可以主要用于将一些非状态对象 转为状态对象，特别是一些需要请求的内容，参考以下代码：
     * 作用：produceState可以实现compose内部发起一些请求 直接以状态的形式返回
     * **/

    @Composable
    fun ProduceStateSample() {

        val listImgRes = listOf(R.mipmap.eg_listitem, R.mipmap.eg_hor_arrangement_type, R.mipmap.eg_ver_arrangement_type, -1)
        var index by remember {
            mutableStateOf(0)
        }
        //获取当前context
        LocalContext.current

        /***例如我这里有一个状态 index 每个Index对应了一个ImageBean 用于显示图片
         * 但是ImageBean我无法直接创建 因为需要进行网络请求来生成
         * Index变化是 我希望发起请求 重新生成ImageBean ，ImageBean变化后我希望UI根据请求的结果刷新
         * 所以就需要ImageBean是一个状态，这时就可以通过produceState来进行请求，将生成的ImageBean转为状态
         * produceState 不一定非得进行异步动作 也可以是同步 根据业务来
         *
         * index修改后 需要动态地去发起网络请求 来获取图片
         * 这样就可以把ImageBean转为可观察的状态 不过这状态需要一些时间来加载
         * key1，key2:值发生变化后执行produceState的作用域内容 生成新的状态对象
         * ***/
        val imageSimpleResult = produceState(initialValue = ImageBean(-1), index) {
            withContext(Dispatchers.IO) {
                value = ImageBean(-1)
                delay(1000L)
                value = ImageBean(listImgRes[index])
            }
        }
        Column {
            Button(onClick = {
                if (index == listImgRes.size - 1) {
                    index = 0
                } else {
                    index++
                }
            }) {
                Text(text = "加载图片")
            }
            when (imageSimpleResult.value.res) {
                -1 -> {
                    Text(text = "加载中")
                }

                else -> {
                    Text(text = "加载成功")
                    Image(painter = painterResource(id = imageSimpleResult.value.res), contentDescription = null)
                }
            }
        }

        //以下为使用密封类完成 不太理解
        /*val imageResult = LoadNetworkImage(res = listImgRes[index])
        Column {
            Button(onClick = {
                if (index == listImgRes.size - 1) {
                    index = 0
                } else {
                    index++
                }
            }) {
                Text(text = "加载图片")
            }

            when (imageResult.value) {
                is Result.Error -> {
                    Text(text = "加载失败")
                }

                is Result.Loading -> {
                    Text(text = "加载中")
                }

                is Result.Success -> {
                    Text(text = "加载成功")
                    Image(painter = painterResource(id = (imageResult.value as Result.Success).image.res), contentDescription = null)
                }
            }
        }*/
    }


    data class ImageBean(val res: Int)

    //密封类知识
    sealed class Result<T>() {
        object Loading : Result<ImageBean>()
        object Error : Result<ImageBean>()
        data class Success(val image: ImageBean) : Result<ImageBean>()
    }

    @Composable
    fun LoadNetworkImage(res: Int): State<Result<ImageBean>> {
        return produceState(initialValue = Result.Loading as Result<ImageBean>, res) {
            withContext(Dispatchers.IO) {
                delay(1000L)
                value = if (res == -1) {
                    Result.Error
                } else {
                    Result.Success(ImageBean(res))
                }
            }
        }
    }

    /**
     * ========== 示例5 ==========*
     * SideEffect不接受任何Key
     * - 可组合函数每次重组都会执行一次
     * - 在可组合函数被创建并且载入视图后才会被调用
     * - 重要作用：可用于与非Compose管理的对象共享Compose状态
     * （解释一下：因为SideEffect特性是每次重组都执行，状态改变会触发重组，所以重组后可以将状态的最新值共享到其他对象）
     * ps: @Composable注解可组合函数表示 在此函数内可以使用compose各种相关特性，不仅仅是UI组件，还包括各种功能组件
     * **/
    @Composable
    fun SideEffectSample() {
        SideEffect {

        }


    }


    /**
     * ========== 示例4 ==========
     * DisposableEffect:自带销毁方法的一次性的影响
     * DisposableEffect是用于执行需要在Composable生命周期结束时进行清理操作的副作用，
     * 它不仅在进入Composition时执行初始化代码，还确保在Composable离开Composition时通过onDispose块执行清理工作
     * DisposableEffect 在其key变化或者组合函数离开组件树时，会取消之前的协程，并取消协程前调用其回收方法(onDispose)，进行资源回收等相关操作
     *   DisposableEffect(Unit) {
     *   //在组件首次被添加到组件树 或者Key值改变后执行 执行一些初始化的方法 或者动作
     *             log("action 1")
     *             onDispose {
     *              //组件从组件树移除后执行
     *                  log("action 2")
     *             }
     *         }
     * 个人理解：
     * 总结来说，虽然两者都涉及到生命周期管理，但LaunchedEffect侧重于异步任务的执行与基于键的重启，
     * 而DisposableEffect更专注于提供一个明确的生命周期回调onDispose来进行资源的清理工作。
     * 如果你需要在Composable生命周期结束时执行特定的清理操作，DisposableEffect是更合适的选择。
     * */
    @Composable
    fun DisposeEffectSample(backDispatcher: OnBackPressedDispatcher) {
        var keyTest = remember {
            1
        }
        DisposableEffect(key1 = keyTest) {
            //在组件首次被添加到组件树 或者Key值改变后执行
            log("action 1 当前key: $keyTest")
            onDispose {
                //在组件从组件树移除后执行
                log("action 2 onDispose 当前key:${keyTest}")
            }
        }

        var isAddBackCallback by remember {
            mutableStateOf(false)
        }

        Row {
            Switch(
                checked = isAddBackCallback,
                onCheckedChange = {
                    keyTest++
                    isAddBackCallback = !isAddBackCallback
                })

            Text(
                text = if (isAddBackCallback) {
                    "拦截返回Add Back Callback"
                } else {
                    "不拦截返回Not Add Back Callback"
                }
            )
        }

        /***
         * 当isAddBackCallback为true时BackHandler加入到组件树
         * 当isAddBackCallback为false时BackHandler从组件树移除
         **/

        if (isAddBackCallback) {
            BackHandler(backDispatcher) {
                toast("拦截返回后执行自己的back动作")
            }
        }
    }

    @Composable
    fun BackHandler(backDispatcher: OnBackPressedDispatcher, onBack: () -> Unit) {
        log("BackHandler重组")

        //可以使用rememberUpdatedState记录onBack动作 这样OnBack替换后 也可以执行最新的值
        val currentOnBack by rememberUpdatedState(newValue = onBack)

        //remember就没必要每次都创建一个OnBackPressedCallback的object
        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentOnBack()
                }
            }
        }
        DisposableEffect(Unit) {
            //在BackHandler首次被添加到组件树时执行
            log("BackHandler DisposableEffect 1")
            backDispatcher.addCallback(backCallback)
            onDispose {
                //在BackHandler被移除组件树时执行
                log("BackHandler DisposableEffect 2 onDispose")
                //如果不取消 则会导致组件被移除 仍然拦截返回
                backCallback.remove()
            }
        }
    }


    /**
     * ========== 示例3 ==========
     * 为什么要使用rememberUpdatedState
     * 1.LaunchEffect在重组时会被重新执行，但有时候LaunchEffect中需要使用最新的参数，但又不想从头开始重新执行携程
     * 举例：LaunchEffect的协程 总共执行需要10秒，在第9秒的时候需要执行某个函数，但此时在第5秒的时候key发生变化，
     *      正常情况下LaunchEffect会被cancel然后重启。如果我们不希望协程重新启动重启从头开始，此时就可以用到rememberUpdatedState
     * 2.rememberUpdatedState的作用就是给某参数创建一个引用，来跟踪这些参数，并保证其值被使用时是最新的值，参数被改变是不重启协程
     *
     *
     * 本示例：执行一个LaunchEffect 10秒后自动执行onTimeOut这个动作，不过在这期间
     * 点击按钮修改第10秒时希望执行的具体是onTimeOut1还是onTimeOut2
     * */
    @Composable
    fun RememberUpdatedStateStamp() {
        val onTimeOut1 = { log("landing timeout 1") }
        val onTimeOut2 = { log("landing timeout 2") }
        val changeOnTimeOutState = remember {
            mutableStateOf(onTimeOut1)
        }
        Column {
            Button(onClick = {
                //点击切换超时函数
                if (changeOnTimeOutState.value == onTimeOut1) {
                    changeOnTimeOutState.value = onTimeOut2
                } else {
                    changeOnTimeOutState.value = onTimeOut1
                }
            }) {
                Text(
                    text = "choose onTimeOut ${
                        if (changeOnTimeOutState.value == onTimeOut1) {
                            "执行onTimeOut1"
                        } else {
                            "执行onTimeOut2"
                        }
                    }"
                )
            }
            LandingScreen(changeOnTimeOutState.value)
        }
    }

    @Composable
    fun LandingScreen(onTimeOut: () -> Unit) {
        /**
         * 点击按钮后 会触发LandingScreen重组 但此时LaunchedEffect(Unit)在第一次重组时已经启动，onTimeOut的值即使变了 也接收不到
         * rememberUpdatedState可以创建一个新的引用 保证这个值永远是最新的 被使用时是最新的
         * */
        log("LandingScreen 重组")
        val currentOnTimeout by rememberUpdatedState(newValue = onTimeOut)
        LaunchedEffect(Unit) {
            log("LaunchedEffect start")
            repeat(10) {
                delay(1000L)
                log("delay ${it + 1}s")
            }
            currentOnTimeout.invoke()
        }
    }

    /**
     * ========== 示例2 ==========
     * 为什么要使用rememberCoroutineScope
     * 1.LaunchedEffect只能在指定可组合项内使用 ， rememberCoroutineScope可以在可组合函数任意位置使用
     * 2.想要在组合函数中任意位置调用协程，且在可组合函数退出组合后自动取消，可以使用rememberCoroutineScope
     * 3.可以看到rememberCoroutineScope 可以在可组合函数的子组件中调用 任意位置调用 这和LaunchedEffect 只能在可组合函数项本身中调用不同
     *
     * 补充：
     * androidx.compose.material.Scaffold  的Scaffold才有状态管理
     * androidx.compose.material3.Scaffold 的Scaffold没有状态管理 不太理解为什么 后期调查原因
     * */
    @Composable
    fun RememberCoroutineScopeSample() {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        Scaffold(scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(text = "rememberCoroutineScope示例", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = {
                            //点击菜单 弹出抽屉
                            /**
                             * 可以看到rememberCoroutineScope 可以在可组合函数的子组件中调用 任意位置调用
                             * 和LaunchedEffect 只能在可组合函数项本身中调用不同
                             * */
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }


                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                        }
                    }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "屏幕内容区域")

                }
            },
            // 悬浮按钮
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("点击了悬浮按钮")
                        }
                    },
                    text = {
                        Text(text = "悬浮按钮")
                    })
            },
            // 侧边栏抽屉
            drawerContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "抽屉内容")

                }
            }
        )
    }


    /***
     * ========== 示例1 ==========
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
