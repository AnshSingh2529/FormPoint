package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerAppliedFormAdapter
import aman.major.formpoint.adapter.RecyclerFormAdapter
import aman.major.formpoint.databinding.ActivityOnlineOpportunityBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.modal.AppliedFormModal
import aman.major.formpoint.modal.FormDataModal
import aman.major.formpoint.modal.ImageDataModal
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    var appliedFormList : ArrayList<AppliedFormModal> = ArrayList()

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
        appliedFormList.clear()
        val call = RetrofitClient.getClient()
            .getAppliedFormData(SharedPrefManager.getInstance(this@OnlineOpportunityActivity)?.user?.id.toString())
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", false)) {
                            val jsonArray = jsonObject?.get("data")?.asJsonArray
                            for (i in 0 until jsonArray!!.size()) {
                                val jsonObject = jsonArray.get(i).asJsonObject
                                val modal = Gson().fromJson(jsonObject,AppliedFormModal::class.java)
                                appliedFormList.add(modal)
                            }
                            binding.ooRecycler.adapter = RecyclerAppliedFormAdapter(this@OnlineOpportunityActivity,appliedFormList,status)
                        }
                    }
                } catch (e: Exception) {

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })

    }

    private fun getAdmissionForm() {
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
                        }

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