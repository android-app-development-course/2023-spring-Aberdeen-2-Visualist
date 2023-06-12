package com.android.forum.file

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.view.View
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Method
import java.net.HttpURLConnection
import java.net.URL


/**
 * uri转File
 */
@RequiresApi(api = Build.VERSION_CODES.Q)
fun uriToFile(uri: Uri, context: Context): File? {
    var file: File? = null
    //android10以上转换
    if (uri.scheme == ContentResolver.SCHEME_FILE) {
        file = File(uri.path)
    } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        //把文件复制到沙盒目录
        val contentResolver = context.contentResolver
        val displayName: String =
            (System.currentTimeMillis().toString() + Math.round((Math.random() + 1) * 1000).toString() + "." + MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(uri)))
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val cache = File(context.cacheDir.absolutePath, displayName)
            val fos = FileOutputStream(cache)
            FileUtils.copy(inputStream!!, fos)
            file = cache
            fos.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return file
}


// 分享图片方法
fun Context.shareImage(imageUri: Uri) {
    // 创建图片分享意图
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_TEXT, "分享图片")
    shareIntent.type = "image/*"


    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

    // 调起分享面板
    this.startActivity(Intent.createChooser(shareIntent, "分享图片"))
}



suspend fun downloadImage(url: String, file: File): Boolean = withContext(Dispatchers.IO) {
    var success = false
    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null

    try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.requestMethod = "GET"
        connection.connect()
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            inputStream = connection.inputStream
            outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }
            outputStream.flush()
            success = true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
        outputStream?.close()
    }
    success
}