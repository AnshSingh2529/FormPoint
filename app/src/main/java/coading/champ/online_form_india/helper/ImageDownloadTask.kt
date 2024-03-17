package coading.champ.online_form_india.helper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class ImageDownloadTask(private val context: Context) : AsyncTask<String, Void, Bitmap?>() {

    val pd = Helper.customProgressDialog(context)
    init {
        pd.show()
    }

    override fun doInBackground(vararg params: String): Bitmap? {
        val imageUrl = params[0]
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            Log.e("ImageDownloadTask", "Error downloading image: ${e.message}")
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        if (result != null) {
            val imagePath = saveImageToGallery(result)
            openImageInGallery(imagePath)
        } else {
            Log.e("ImageDownloadTask", "Failed to download image.")
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap): String {
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val imageFile = File(storageDir, fileName)
        try {
            val stream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            Log.e("ImageDownloadTask", "Error saving image to gallery: ${e.message}")
        }

        // Add the image to the gallery so that it can be viewed in the default gallery app
        MediaStore.Images.Media.insertImage(
            context.contentResolver,
            imageFile.absolutePath,
            imageFile.name,
            null
        )

        return imageFile.absolutePath
    }

    private fun openImageInGallery(imagePath: String) {
       /* val intent = Intent(Intent.ACTION_VIEW)
        val uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        intent.setDataAndType(android.net.Uri.parse("file://$imagePath"), "image/*")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION

        context.startActivity(intent)*/
        */

        val intent = Intent(Intent.ACTION_VIEW)

        // Get the content URI using FileProvider
        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",  // Replace with your package name
            File(imagePath)
        )

        intent.setDataAndType(contentUri, "image/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        try {
            pd.dismiss()
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle the exception if no activity is found to handle the intent
            Log.e("ImageDownloadTask", "No activity found to handle the intent."+e.message)
        }
    }


}
