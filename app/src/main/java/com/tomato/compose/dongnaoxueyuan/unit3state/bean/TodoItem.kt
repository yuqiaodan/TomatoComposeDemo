package com.tomato.compose.dongnaoxueyuan.unit3state.bean

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

/**
 * Created by Tomato on 2024/5/22.
 * Description：
 */
data class TodoItem(
    val task: String,
    var icon: TodoIcon,
    val id: UUID = UUID.randomUUID()
)

enum class TodoIcon(val imageVector: ImageVector, val contentDescription: String) {
    Square(Icons.Default.CropSquare, "Expand"),
    Done(Icons.Default.Done, "Done"),
    Event(Icons.Default.Event, "Event"),
    Privacy(Icons.Default.PrivacyTip, "Privacy"),
    TrashDone(Icons.Default.RestoreFromTrash, "Restore");

    companion object {
        val Default = Square
    }
}