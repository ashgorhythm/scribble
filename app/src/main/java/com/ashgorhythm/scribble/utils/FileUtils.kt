package com.ashgorhythm.scribble.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.net.URI

fun saveImage(context: Context, uri: Uri) : String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.filesDir, "notes_${System.currentTimeMillis()}.jpg")

        FileOutputStream(file).use { output ->
            inputStream.copyTo(output)
        }
        file.absolutePath
    } catch (e: Exception){
        e.printStackTrace()
        null
    }
}

fun deleteImage(path: String?) {
    path?.let {
        val file = File(it)
        if (file.exists()) file.delete()
    }
}
