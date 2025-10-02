package com.example.notesapp.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class ImageFileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val imagesDir: File = context.filesDir

    suspend fun copyImageToInternalStorage(url: String): String {
        val fileName = "IMG_${UUID.randomUUID()}.jpg"
        val file = File(imagesDir, fileName)

        withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(url.toUri())?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        return file.absolutePath
    }

    suspend fun deleteImage(path: String) {
        withContext(Dispatchers.IO) {
            val file = File(path)
            if (file.exists() && isInternal(file.absolutePath)) {
                file.delete()
            }
        }
    }

    fun isInternal(uri: String): Boolean {
        return uri.startsWith(imagesDir.absolutePath)
    }
}
