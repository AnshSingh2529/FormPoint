package coading.champ.online_form_india.helper

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.core.content.FileProvider
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class DownloadPdfTask(private val context: Context, private val pdfUrl: String) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        try {
            val url = URL(pdfUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val input = BufferedInputStream(url.openStream())
            val outputFile = context.filesDir.path + "/downloaded_file.pdf"
            val output = FileOutputStream(outputFile)

            val data = ByteArray(1024)
            var count: Int
            while (input.read(data, 0, 1024).also { count = it } != -1) {
                output.write(data, 0, count)
            }

            output.flush()
            output.close()
            input.close()

            return outputFile
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        if (result.isNotEmpty()) {
            openPdf(result)
        } else {
            // Handle download error
        }
    }

    private fun openPdf(filePath: String) {
        Log.d("openPdf", "openPdf: call: $filePath")
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File(filePath)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.d("openPdf", "openPdf: Excepition ${e.localizedMessage}")
        }
    }
}
