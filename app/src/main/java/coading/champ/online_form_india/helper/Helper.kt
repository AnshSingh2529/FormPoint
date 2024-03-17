package coading.champ.online_form_india.helper

import android.app.Dialog
import coading.champ.online_form_india.R
import coading.champ.online_form_india.adapter.RecyclerVideoHomeAdapter
import coading.champ.online_form_india.modal.VideoModal
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
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
import java.io.OutputStream
import java.util.Calendar


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


        fun customProgressDialog(context: Context) :Dialog{

            val  dialog = Dialog(context,R.style.main_dialog_style)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.progress_dialog_lay)
            dialog.window?.attributes?.windowAnimations = R.style.animation


            val progress_ring = dialog.findViewById<ImageView>(R.id.progress_ring)
            val app_logo = dialog.findViewById<ImageView>(R.id.app_logo)


            // Continuous rotation animation
            val rotate = RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotate.duration = 1000 // 1 second for each rotation
            rotate.repeatCount = Animation.INFINITE // Infinite rotation
            progress_ring.startAnimation(rotate)

            // Bounce animation
            val bounce = AnimationSet(true)
            val fadeIn = RotateAnimation(
                0f, 10f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            fadeIn.duration = 100 // Adjust duration as per requirement
            fadeIn.interpolator = AccelerateInterpolator()
            val fadeOut = RotateAnimation(
                10f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            fadeOut.duration = 100 // Adjust duration as per requirement
            fadeOut.interpolator = DecelerateInterpolator()
            bounce.addAnimation(fadeIn)
            bounce.addAnimation(fadeOut)
            bounce.repeatCount = Animation.INFINITE // Infinite bouncing
            app_logo.startAnimation(bounce)

            return dialog
        }

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

        fun saveBitmapToFile(bitmap: Bitmap?): File? {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "${Calendar.getInstance().timeInMillis}_Major.jpg"
            )
            return try {
                val os: OutputStream = FileOutputStream(file)
                bitmap?.compress(CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()
                file
            } catch (e: IOException) {
                //e.printStackTrace()
                null
            }
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