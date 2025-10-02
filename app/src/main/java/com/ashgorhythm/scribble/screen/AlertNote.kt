package com.ashgorhythm.scribble.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AlertNote(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
){
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Info")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {onDismissRequest()}
            ) {
                Text("Close", color = MaterialTheme.colorScheme.tertiary)
            }
        },
        containerColor = MaterialTheme.colorScheme.primary
    )
}