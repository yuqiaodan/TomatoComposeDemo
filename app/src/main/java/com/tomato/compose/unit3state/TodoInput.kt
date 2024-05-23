package com.tomato.compose.unit3state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tomato.compose.unit3state.bean.TodoIcon
import com.tomato.compose.unit3state.bean.TodoItem

/**
 * Created by Tomato on 2024/5/23.
 * Description：
 */

/**
 * 输入框模块 背景
 * content 允许传入任意组件
 * @param elevate 是否显示阴影
 * */
@Composable
fun InputBackground(modifier: Modifier = Modifier, elevate: Boolean, content: @Composable RowScope.() -> Unit) {
    val animElevate by animateDpAsState(if (elevate) 1.dp else 0.dp, TweenSpec(300))
    //添加阴影动画
    Surface(
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
        shadowElevation = animElevate,
        shape = RectangleShape
    ) {
        Row(
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }
}

/**
 * todo列表中点击后 弹出这个输入框 用于编辑TodoItem的内容
 * @param item 编辑的原值
 * @param onEditItemChange 编辑过程
 * @param onEditDone 编辑完成
 * @param onRemoveItem 删除item
 * */
@Composable
fun TodoItemInlineEditor(
    item: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) {
    TodoItemInputStateProve(
        text = item.task,
        onTextChange = {
            //使用copy保证uuid不改变
            onEditItemChange(item.copy(task = it))
        },
        icon = item.icon,
        onIconChange = {
            onEditItemChange(item.copy(icon = it))
        },
        submit = onEditDone,
        buttonSlot = {
            //传入保存和删除两个图标
            Row {
                val shrinkButtons = Modifier.widthIn(min=20.dp)
                TextButton(onClick = onEditDone, modifier = shrinkButtons) {
                    //这里试试用emoji符号：软盘
                    Text(
                        text = "💾",
                        textAlign = TextAlign.End
                    )
                }
                TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
                    //这里试试用emoji符号：软盘
                    Text(
                        text = "❌",
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    )
}


@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    val (text, setText) = remember {
        mutableStateOf("")
    }
    var (icon, setIcon) = remember {
        mutableStateOf(TodoIcon.Default)
    }
    //提交生成的TodoItem
    val submit = {
        onItemComplete.invoke(TodoItem(text, icon))
        setText("")
        icon = TodoIcon.Default
    }

    TodoItemInputStateProve(
        text = text,
        onTextChange = setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit
    ) {
        //传入操作按钮
        TodoEditButton(
            onClick = submit,
            text = "Add",
            //按钮垂直居中
            //modifier = Modifier.align(Alignment.CenterVertically),
            //根据输入的内容是否为空 决定按钮是否可用
            enable = text.isNotEmpty()
        )
    }
}


/**
 * TodoItemInput 内部包含的很多状态了 做一个状态提升 将状态改为由外部传入
 * 所有传入的状态变量必须包含以下两部分，才能让子组件根据状态变化改变UI和修改状态
 * value: 状态原值
 * setValue: (value)->Unit
 * 不一定是直接传入这两个参数 比如传入一个state对象 其内部也包含了value和setValue
 * */
@Composable
fun TodoItemInputStateProve(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit
) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)

        ) {
            //输入框
            TodoEditText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .padding(end = 8.dp),
                //点击软键盘上的完成
                onImeAction = submit
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                buttonSlot()
            }


        }
        //根据输入的内容是否为空 决定图标选择列表是否显示
        val iconRowVisible = text.isNotEmpty()
        EditIconRow(
            visible = iconRowVisible, icon = icon, onIconChange = onIconChange, modifier = Modifier.padding(top = 8.dp)
        )
    }
}


/**
 * val (text, setText) = remember {
 *         mutableStateOf("")
 *     }
 *是kotlin中结构声明的语法 其效果就是把一个对象中的多个成员变量同时赋值到多个变量去，和以下代码同理
 *     val state = remember {
 *         mutableStateOf("")
 *     }
 *     val text = state.component1()
 *     val setText = state.component2()
 *
 * 通过MutableState可以有三种方式创建state对象
 * -  val state = remember { mutableStateOf("default") }
 * -  var value:String by remember { mutableStateOf("default") } //推荐用这个
 * -  val (text, setText) = remember { mutableStateOf("") }
 *
 * 在组合中务必使用remember来记录对象 否则它会在每次重组时重新创建
 * MutableState<T> 类似于MutableLiveData<T> ,于Compose运行是集成，它是可观察的，会在更新时通知Compose进行重组
 * */
@Preview
@Composable
fun TodoItemInput(onItemComplete: (todo: TodoItem) -> Unit = {}) {
    val (text, setText) = remember {
        mutableStateOf("")
    }
    var selectIcon by remember {
        mutableStateOf(TodoIcon.Default)
    }
    //提交生成的TodoItem
    val submit = {
        onItemComplete.invoke(TodoItem(text, selectIcon))
        setText("")
        selectIcon = TodoIcon.Default
    }

    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)

        ) {
            //输入框
            TodoEditText(
                text = text,
                onTextChange = setText,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .padding(end = 8.dp),
                //点击软键盘上的完成
                onImeAction = submit
            )

            //按钮
            TodoEditButton(
                onClick = submit,
                text = "Add",
                //按钮垂直居中
                modifier = Modifier.align(Alignment.CenterVertically),
                enable = text.isNotEmpty()
            )
        }
        val iconRowVisible = text.isNotEmpty()
        EditIconRow(visible = iconRowVisible, icon = selectIcon, onIconChange = {
            selectIcon = it
        }, modifier = Modifier.padding(top = 8.dp))
    }
}


/**
 * 输入框 对于输入框的状态是text onTextChange
 * */
@Composable
fun TodoEditText(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val kc = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.colors(focusedTextColor = Color.Black),
        maxLines = 1,
        //配置软键盘 完成动作
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            kc?.hide()
        }),
        modifier = modifier.background(Color.White)
    )
}


/**
 * 输入框选择图标 有动画效果的一行图标
 * 根据文本框是否有内容自动修改可见状态 有动画
 * */
@Composable
fun EditIconRow(
    modifier: Modifier = Modifier,
    visible: Boolean,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit
) {
    val enterAnim = remember {
        fadeIn(animationSpec = TweenSpec(300, 0, FastOutLinearInEasing))
    }
    val outAnim = remember {
        fadeOut(animationSpec = TweenSpec(300, 0, FastOutSlowInEasing))
    }
    Box(modifier = modifier.defaultMinSize(minHeight = 16.dp)) {
        AnimatedVisibility(visible = visible, enter = enterAnim, exit = outAnim) {
            IconRow(icon = icon, onIconChange = onIconChange)
        }
    }
}

@Composable
fun IconRow(modifier: Modifier = Modifier, icon: TodoIcon, onIconChange: (TodoIcon) -> Unit) {
    Row(modifier) {
        TodoIcon.entries.forEach { todoIcon ->
            SelectableIconButton(icon = todoIcon.imageVector, iconDesc = todoIcon.contentDescription, isSelect = (todoIcon == icon), onIconSelected = { onIconChange(todoIcon) })
        }
    }
}

@Composable
fun SelectableIconButton(modifier: Modifier = Modifier, icon: ImageVector, iconDesc: String, isSelect: Boolean, onIconSelected: () -> Unit) {
    val tint = if (isSelect) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }
    TextButton(onClick = { onIconSelected() }, shape = CircleShape, modifier = modifier) {
        Column {
            Icon(
                imageVector = icon,
                tint = tint,
                //stringResource(id = R.string.sss)
                contentDescription = iconDesc,
            )
            if (isSelect) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }


}


/**
 * 输入框按钮 根据文本框是否有内容自动修改是否可点击状态
 * */
@Composable
fun TodoEditButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier, enable: Boolean = true) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, disabledContainerColor = Color.Gray),
        modifier = modifier,
        enabled = enable
    ) {
        Text(text = text)
    }
}
