package com.yazidistiqlaladhyfadhillah607062430005.mobpro1_assesment2_gamecollection.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageStorage {
    fun saveToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "game_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
