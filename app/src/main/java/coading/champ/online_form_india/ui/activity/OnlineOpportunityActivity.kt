package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.adapter.RecyclerAppliedFormAdapter
import coading.champ.online_form_india.adapter.RecyclerFormAdapter
import coading.champ.online_form_india.databinding.ActivityOnlineOpportunityBinding
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.SharedPrefManager
import coading.champ.online_form_india.modal.FormDataModal
import coading.champ.online_form_india.modal.ImageDataModal
import coading.champ.online_form_india.modal.NewFormAppliedModal
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coading.champ.online_form_india.helper.Helper
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlineOpportunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnlineOpportunityBinding

    private var titleImg: Int = 0
    var type: String = ""

    private var status: Int = 0

    var formDataList: ArrayList<ImageDataModal> = ArrayList()

    var appliedFormList : ArrayList<NewFormAppliedModal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineOpportunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ooToolbar.setNavigationOnClickListener {
            finish()
        }
        status = intent.getIntExtra("tabPosition", 0)
        when (status) {
            0 -> {
                binding.ooToolbar.title = resources.getString(R.string.goverment_form)
                titleImg = R.drawable.ic_govt_form
                type = "government"
                getAdmissionForm()
            }

            1 -> {
                binding.ooToolbar.title = resources.getString(R.string.addmission_form)
                titleImg = R.drawable.ic_form_online
                type = "admission"
                getAdmissionForm()
            }

            2 -> {
                binding.ooToolbar.title = resources.getString(R.string.admitCard)
                titleImg = R.drawable.id_admit_card
                type = ""
                getAppliedForm()
            }

            3 -> {
                binding.ooToolbar.title = resources.getString(R.string.result)
                titleImg = R.drawable.ic_result
                type = ""
                getAppliedForm()
            }

            4 -> {
                binding.ooToolbar.title = resources.getString(R.string.appliedForms)
                titleImg = R.drawable.ic_application_status
                type = ""
                getAppliedForm()
            }

            6 -> {
                binding.ooToolbar.title = resources.getString(R.string.choose_form_for_otp)
                titleImg = R.drawable.ic_form_online
                type = ""
                getAppliedForm()
            }

        }


    }

    private fun getAppliedForm() {
        val dialog = Helper.customProgressDialog(this@OnlineOpportunityActivity)
        dialog.show()
        val userId = SharedPrefManager.getInstance(this@OnlineOpportunityActivity)?.user?.id.toString()
        Log.d("getAppliedForm", "getAppliedForm: UserId: $userId")
        appliedFormList.clear()
        val call = RetrofitClient.getClient()
            .getAppliedFormStatus(userId)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d("getAppliedForm", "onResponse: response success : ${response.body()}")
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", false)) {
                            val jsonArray = jsonObject?.get("data")?.asJsonArray
                            for (i in 0 until jsonArray!!.size()) {
                                val dataObject = jsonArray.get(i).asJsonObject
                                val modal = Gson().fromJson(dataObject,NewFormAppliedModal::class.java)
                                appliedFormList.add(modal)
                            }
                            Log.d("getAppliedForm", "onResponse: list size: "+appliedFormList.size)
                            binding.ooRecycler.adapter = RecyclerAppliedFormAdapter(this@OnlineOpportunityActivity,appliedFormList,status)
                        }else{
                            binding.ooRecycler.visibility = View.GONE
                            binding.noDataFoundImg.visibility = View.VISIBLE
                        }
                        dialog.dismiss()
                    }else{
                        Log.d("getAppliedForm", "onResponse: response is not success :${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.d("getAppliedForm", "onResponse: Exception ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getAppliedForm", "onFailure: "+t.localizedMessage)
            }
        })

    }

    private fun getAdmissionForm() {
        val dialog = Helper.customProgressDialog(this@OnlineOpportunityActivity)
        dialog.show()
        val call = RetrofitClient.getClient().getOnlineForms(type)
        Log.d("getAdmissionForm", "getAdmissionForm: function call: type $type")
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(
                            "getAdmissionForm",
                            "getAdmissionForm: function call is success ${
                                response.body().toString()
                            }"
                        )
                        val jsonObject: JsonObject? = response.body()
                        if (jsonObject?.get("status")?.asString.equals("success")) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            formDataList.clear()
                            for (i in 0 until dataArray!!.size()) {
                                val dataObj = dataArray.get(i).asJsonObject
                                val model = Gson().fromJson(dataObj, FormDataModal::class.java)
                                formDataList.add(ImageDataModal(titleImg, model))
                            }
                            Log.d(
                                "getAdmissionForm",
                                "getAdmissionForm: function call is list ${formDataList.size}"
                            )
                            binding.ooRecycler.adapter =
                                RecyclerFormAdapter(
                                    this@OnlineOpportunityActivity,
                                    formDataList,
                                    status
                                )
                        }else{
                            binding.ooRecycler.visibility = View.GONE
                            binding.noDataFoundImg.visibility = View.VISIBLE
                        }
                        dialog.dismiss()

                    } else {
                        Log.d(
                            "getAdmissionForm",
                            "getAdmissionForm: function call is not success ${
                                response.errorBody()?.string()
                            }"
                        )
                    }
                } catch (e: Exception) {
                    Log.d(
                        "getAdmissionForm",
                        "getAdmissionForm: Exception call is fail ${e.localizedMessage}"
                    )
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(
                    "getAdmissionForm",
                    "getAdmissionForm: Exception call is fail ${t.localizedMessage}"
                )
            }
        })
    }


}