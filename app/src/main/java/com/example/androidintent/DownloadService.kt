package com.example.androidintent

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL


class DownloadService : IntentService("DownloadService") {
    private var result = Activity.RESULT_CANCELED

    // Will be called asynchronously by OS.
    override fun onHandleIntent(intent: Intent?) {
        val urlPath = intent!!.getStringExtra(URL)
        val fileName = intent.getStringExtra(FILENAME)
        val output = File(
            Environment.getExternalStorageDirectory(),
            fileName.orEmpty()
        )
        if (output.exists()) {
            output.delete()
        }
        var stream: InputStream? = null
        var fos: FileOutputStream? = null
        try {
            val url = URL(urlPath)
            stream = url.openConnection().getInputStream()
            val reader = InputStreamReader(stream)
            fos = FileOutputStream(output.path)
            var next = -1
            while (reader.read().also { next = it } != -1) {
                fos.write(next)
            }
            // Successful finished
            result = Activity.RESULT_OK
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        publishResults(output.absolutePath, result)
    }

    private fun publishResults(outputPath: String, result: Int) {
        val intent = Intent(NOTIFICATION)
        intent.putExtra(FILEPATH, outputPath)
        intent.putExtra(RESULT, result)
        sendBroadcast(intent)
    }

    companion object {
        const val URL = "urlpath"
        const val FILENAME = "filename"
        const val FILEPATH = "filepath"
        const val RESULT = "result"
        const val NOTIFICATION = "service receiver"
    }
}


