package com.tomato.compose.unit2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Tomato on 2024/5/21.
 * Description：
 */

@Composable
fun LayoutStudy() {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "LayoutStudy") },
                actions = {
                    
                    IconButton(onClick = {  }) {

                        Icon(imageVector = Icons.Default.Favorite,contentDescription = null)
                    }
                }

            )
        }


    ) {innerPadding->
        BodyContent(Modifier.padding(innerPadding))
    }
}


@Composable
fun BodyContent(modifier: Modifier =Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi there")
        Text(text = "Thanks for going through the LayoutStudy")
    }
}