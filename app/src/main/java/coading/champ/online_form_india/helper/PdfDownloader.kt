package coading.champ.online_form_india.helper

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
            //openPdf(context!!, pdfFile?.path.toString())
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

    /*private fun openPdf(pdfFile: Uri?) {
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
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                Log.d("openPdf", "openPdf: Pdf File Uri: ${pdfFile.toString()} : content uri : $contentUri")
                intent.setDataAndType(contentUri, "application/pdf")
                context.startActivity(Intent.createChooser(intent, "Open PDF using"))
            } catch (e: Exception) {
                Log.d("openPdf", "openPdf: Exception"+e.localizedMessage)
                Toast.makeText(context, "No PDF viewer found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }*/

    /*private fun openPdf(pdfFile: Uri?) {
        if (pdfFile != null) {
            try {
                val file = File(pdfFile.path)

                // Generate a content URI using FileProvider
                val contentUri = FileProvider.getUriForFile(
                    context,
                    "aman.major.formpoint.provider2",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(contentUri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)// Ensure read permissions
                context.startActivity(Intent.createChooser(intent, "Open PDF using"))
                Log.d("openPdf", "openPdf: Pdf File Uri: ${pdfFile.toString()} : content uri : $contentUri")
                Log.d("openPdf", "Intent flags: ${intent.flags}")
            } catch (e: Exception) {
                Log.d("openPdf", "openPdf: Exception" + e.localizedMessage)
                Toast.makeText(context, "Error opening PDF", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun openPdf(pdfFile: Uri?) {
        if (pdfFile != null) {
            try {

                val file = File(Environment.getExternalStorageDirectory(),pdfFile.path)
                // Generate a content URI using FileProvider
                val contentUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",File(file.absolutePath))

                val pdfUri = Uri.fromFile(file)


                Log.d("openPdf", "openPdf: pdfFileUri $pdfFile")
                Log.d("openPdf", "openPdf: pdfFilePath ${pdfFile.path}")
                Log.d("openPdf", "openPdf: encodedPath ${pdfFile.encodedPath}")
                Log.d("openPdf", "openPdf: pdfFileUri $contentUri")
                Log.d("openPdf", "openPdf: pdfUri $pdfUri")



                // Create an intent with the content URI and MIME type
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(contentUri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                // Check if there are apps that can handle the intent
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    // Handle the case where no suitable app is found
                    Toast.makeText(context, "No PDF viewer found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("openPdf", "Exception: ${e.localizedMessage}")
                Toast.makeText(context, "Error opening PDF", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openPdf(context: Context, filePath: String) {
        val file = File(filePath)
        val pdfFileUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        Log.d("openPdf", "openPdf: ${file.exists()} name ${file.name} content uri $pdfFileUri")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(pdfFileUri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Handle exceptions, such as if there is no PDF viewer installed
            // You can prompt the user to install a PDF viewer app in this case
            // or provide alternative actions
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
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${title}_${System.currentTimeMillis()}.pdf")

        // Enqueue the download and get the download ID
        downloadId = downloadManager.enqueue(request)
    }

    fun unregisterReceiver() {
        // Unregister the BroadcastReceiver when it's no longer needed
        context.unregisterReceiver(onCompleteReceiver)
    }
}
