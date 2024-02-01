package aman.major.formpoint.helper

import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.modal.VideoModal
import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Helper {


    companion object{
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

        fun getVideoList(recyclerView: RecyclerView,context: Context) {
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
                                    val videoModal = Gson().fromJson(dataObj, VideoModal::class.java)
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

    }

}