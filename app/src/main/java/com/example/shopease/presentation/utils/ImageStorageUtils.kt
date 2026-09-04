package com.example.shopease.presentation.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageStorageUtils {
    fun copyImageToInternalStorage(context: Context, uri: Uri, fileName: String): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, fileName)
            FileOutputStream(file).use { output -> inputStream.copyTo(output) }
            inputStream.close()
            Uri.fromFile(file).toString()
        } catch (e: Exception) {
            null
        }
    }
}