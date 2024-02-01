package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityFormDetailBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.modal.FormDataModal
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormDetailActivity : AppCompatActivity() {

    private val TAG: String = "getFormDetails"
    lateinit var binding: ActivityFormDetailBinding

    var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.afdToolbar.setNavigationOnClickListener { finish() }
        id = intent.getStringExtra("formId").toString()
        getFormDetails()

    }

    private fun getFormDetails() {
        Log.d(TAG, "getFormDetails: function call: formId: $id")
        val call = RetrofitClient.getClient().getSingleFormData(id);
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: response success: ${response.body()}")
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            val dataObj = dataArray?.get(0)?.asJsonObject
                            val modal = Gson().fromJson(dataObj,FormDataModal::class.java)
                            binding.afdFormName.text = modal.name
                            binding.afdFormLevel.text = "Level: ${modal.level}"
                            binding.afdGovtPrice.text = "₹${modal.charges}"
                            binding.afdExtraCharges.text = "₹${modal.extra_charges}"
                            val totalPrice = modal.charges.toInt() + modal.extra_charges.toInt()
                            binding.afdTotalPrice.text = "₹${totalPrice}"
                            setEligiblityList(modal.eligibility)
                            setRequiredDocsList(modal.requirements)

                        }
                    } else {
                        Log.d(TAG, "onResponse: response not success: ${response.errorBody()?.string()} error code: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "onResponse: Exception found ${e.localizedMessage}")
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
    }

    private fun setRequiredDocsList(requirements: List<String>) {
        val arrayAdapter = ArrayAdapter(this, R.layout.list_view_lay,requirements)
        binding.afdRequiredList.adapter = arrayAdapter
    }

    private fun setEligiblityList(eligibility: List<String>) {
        val arrayAdapter = ArrayAdapter(this, R.layout.list_view_lay,eligibility)
        binding.afdEligibleList.adapter = arrayAdapter
    }
}