package coading.champ.online_form_india.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import coading.champ.online_form_india.R
import coading.champ.online_form_india.helper.Helper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coading.champ.online_form_india.adapter.RecyclerVideoHomeAdapter
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.modal.VideoModal
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)


        val va_recycler = findViewById<RecyclerView>(R.id.va_recycler)
        val va_toolbar = findViewById<MaterialToolbar>(R.id.va_toolbar)
        val noDataFoundImg = findViewById<ImageView>(R.id.noDataFoundImg)

        va_toolbar.setNavigationOnClickListener {
            finish()
        }
       getVideoList(va_recycler,noDataFoundImg)
    }

    fun getVideoList(recyclerView: RecyclerView, imageView: ImageView) {
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
                                this@VideoActivity,
                                path.toString()
                            )
                        }else{
                            imageView.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
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