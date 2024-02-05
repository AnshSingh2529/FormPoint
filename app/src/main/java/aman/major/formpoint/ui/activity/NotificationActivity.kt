package aman.major.formpoint.ui.activity

import aman.major.formpoint.adapter.RecyclerNotificationAdapter
import aman.major.formpoint.databinding.ActivityNotificationBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.modal.NotificationModal
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    var notificationList = ArrayList<NotificationModal>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.anToolbar.setNavigationOnClickListener {
            finish()
        }

        getNotification()

    }

    private fun getNotification() {
        Log.d("getNotification", "getNotification: fucntion call")

        val call = RetrofitClient.getClient().getNotifications();

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d("getNotification", "onResponse: success: "+response.body())
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success")) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            notificationList.clear()
                            for (i in 0 until dataArray!!.size()) {
                                val dataObj = dataArray.get(i).asJsonObject
                                val modal = Gson().fromJson(dataObj, NotificationModal::class.java)
                                notificationList.add(modal)
                            }
                            Log.d("getNotification", "onResponse: success: listSize: ${notificationList.size}")
                            binding.anRecycler.adapter =
                                RecyclerNotificationAdapter(this@NotificationActivity, notificationList)
                        }
                    }else{
                        Log.d("getNotification", "onResponse: not success: "+response.errorBody()?.string())
                    }
                } catch (e: Exception) {
                    Log.d("getNotification", "onResponse: exception: ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getNotification", "onResponse: onFailure: ${t.localizedMessage}")
            }
        })
    }
}