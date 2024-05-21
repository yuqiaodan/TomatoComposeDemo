package com.tomato.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tomato.compose.bean.Message
import com.tomato.compose.ui.theme.TomatoComposeDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TomatoComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Conversation(messages = SimpleData.listData)
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) {
                MessageCard(msg = it)
            }
        }
    }

    @Composable
    fun MessageCard(msg: Message) {


        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.mipmap.ic_header_default),
                contentDescription = null,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.width(5.dp))
            var isExpanded by remember {
                mutableStateOf(false)
            }
            val surfaceColor: Color by animateColorAsState(
                if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            )
            Log.d("aaaaaa", "MessageCard: item:${msg.author} isExpanded:${isExpanded}")
            Column(modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }) {
                Text(
                    text = "${msg.author}",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = "${msg.body}",
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        maxLines = if (isExpanded) {
                            Int.MAX_VALUE
                        } else {
                            1
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        Conversation(messages = SimpleData.listData)
    }


}

