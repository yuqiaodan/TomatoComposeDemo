package com.tomato.compose.unit2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tomato.compose.R
/**
 * Created by Tomato on 2024/5/21.
 * Description：
 *标准布局组件 Row 横向布局  Colum纵向布局
 */
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colorScheme.surface)
        .clickable { }
        .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_header_1),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alferd Sysley", fontWeight = FontWeight.Bold)
            //隐式传参 CompositionLocalProvider内部所有参数提供LocalContentColor 字体改为80透明度黑色
            CompositionLocalProvider(LocalContentColor.provides(Color(0x80000000))) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}