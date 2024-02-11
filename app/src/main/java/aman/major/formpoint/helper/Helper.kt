package aman.major.formpoint.helper

import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.modal.VideoModal
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class Helper {


    companion object {
        var passwordVisible = true

        fun manageEyeIcon(password: ImageView, passEditText: EditText) {
            if (passwordVisible) {
                password.setImageResource(R.drawable.ic_closed_eye)
                passEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
            } else {
                password.setImageResource(R.drawable.ic_open_eye)
                passEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordVisible = true
            }
        }

        fun getVideoList(recyclerView: RecyclerView, context: Context) {
            val videoModalList = ArrayList<VideoModal>()
            val call = RetrofitClient.getClient().video();
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    try {
                        if (response.isSuccessful) {
                            val jsonObject = response.body();
                            val path = jsonObject?.get("path")?.asString
                            if (jsonObject?.get("status")?.asString.equals("success", false)) {
                                val data = jsonObject?.get("data")?.asJsonArray
                                for (i in 0 until data!!.size()) {
                                    val dataObj = data.get(i).asJsonObject
                                    val videoModal =
                                        Gson().fromJson(dataObj, VideoModal::class.java)
                                    videoModalList.add(videoModal)
                                }
                                recyclerView.adapter = RecyclerVideoHomeAdapter(
                                    videoModalList,
                                    context,
                                    path.toString()
                                )
                            }
                        }
                    } catch (e: Exception) {
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }
            })

        }

//        fun createFile(context: Context,imgUri: Uri,): File? {
//            val inputStream: InputStream? = context.contentResolver.openInputStream(imgUri)
//            inputStream?.let {
//                try {
//                   // val file = createImageFile(context)
//                    val outputStream = FileOutputStream(file)
//                    inputStream.use { input ->
//                        outputStream.use { fileOut ->
//                            input.copyTo(fileOut)
//                        }
//                    }
//                    return file
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//            return null
//
//        }

        fun createFileFromUri(context: Context, uri: Uri): File? {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            inputStream?.let {
                try {
                    val fileName = getFileName(context, uri)
                    val file = createImageFile(context, fileName)
                    val outputStream = FileOutputStream(file)
                    inputStream.use { input ->
                        outputStream.use { fileOut ->
                            input.copyTo(fileOut)
                        }
                    }
                    return file
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        private fun createImageFile(context: Context, fileName: String): File {
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File(storageDir, fileName)
        }

        private fun getFileName(context: Context, uri: Uri): String {
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                it.moveToFirst()
                return it.getString(nameIndex)
            }
            // If unable to retrieve the name, create a unique name
            return "file_${System.currentTimeMillis()}"
        }


        fun getRealPathFromURI(context: Context,contentUri: Uri): String? {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(context, contentUri, proj, null, null, null)
            val cursor = loader.loadInBackground()
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val result = cursor.getString(column_index)
            cursor.close()
            return result
        }


    }
}