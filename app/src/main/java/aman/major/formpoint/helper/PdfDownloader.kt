package aman.major.formpoint.helper

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

class PdfDownloader(private val context: Context) {

    private var downloadId: Long = -1

    private val onCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Open the PDF file after download completion
            val pdfFile = getDownloadedPdfFile()
            openPdf(pdfFile)
        }
    }

    @SuppressLint("Range")
    private fun getDownloadedPdfFile(): Uri? {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        if (cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                val uri = Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                cursor.close()
                return uri
            }
        }
        cursor.close()
        return null
    }

    private fun openPdf(pdfFile: Uri?) {
        if (pdfFile != null) {
            try {

                val file = File(Environment.getExternalStorageDirectory(),pdfFile.path)

                // Generate a content URI using FileProvider
                val contentUri = FileProvider.getUriForFile(
                    context,
                    "aman.major.formpoint.provider2",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW)
                Log.d("openPdf", "openPdf: Pdf File Uri: ${pdfFile.toString()}")
                intent.setDataAndType(contentUri, "application/pdf")
                context.startActivity(Intent.createChooser(intent, "Open PDF using"))
            } catch (e: Exception) {
                Log.d("openPdf", "openPdf: Exception"+e.localizedMessage)
                Toast.makeText(context, "No PDF viewer found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }

    fun downloadAndOpenPdf(pdfUrl: String, title: String) {
        // Register the BroadcastReceiver for download completion
        context.registerReceiver(onCompleteReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        // Create a DownloadManager instance
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // Parse the PDF URL
        val uri = Uri.parse(pdfUrl)

        // Create a DownloadManager.Request with the PDF URL
        val request = DownloadManager.Request(uri)

        // Set the title for the PDF file
        request.setTitle(title)

        // Set the destination directory for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.pdf")

        // Enqueue the download and get the download ID
        downloadId = downloadManager.enqueue(request)
    }

    fun unregisterReceiver() {
        // Unregister the BroadcastReceiver when it's no longer needed
        context.unregisterReceiver(onCompleteReceiver)
    }
}
