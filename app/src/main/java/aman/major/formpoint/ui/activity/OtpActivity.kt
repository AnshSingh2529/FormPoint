package aman.major.formpoint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityOtpBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.modal.FormDataModal
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OtpActivity : AppCompatActivity() {

    var formId : String? = null

    lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        formId = intent.getStringExtra("formId")

        binding.aoToolbar.setNavigationOnClickListener { finish() }

        getFormDetails()


        binding.btnVerify.setOnClickListener {
            if (binding.otpEditText.text.toString().isNotEmpty()){
                sendOtpToServer()
            }else{
                Toast.makeText(this, "Please Fill OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun sendOtpToServer() {
        Log.d("sendOtpToServer", "sendOtpToServer: formId: $formId userId: ${SharedPrefManager.getInstance(this@OtpActivity)?.user?.id.toString()}")
        val call = RetrofitClient.getClient().sendOtpToRecieve(formId,SharedPrefManager.getInstance(this@OtpActivity)?.user?.id.toString(),binding.otpEditText.text.toString())
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d("sendOtpToServer", "onResponse: response is success: ${response.body()}")
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            Toast.makeText(
                                this@OtpActivity,
                                "${jsonObject?.get("message")?.asString}",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("sendOtpToServer", "onResponse: exception ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("sendOtpToServer", "onFailure: failure: ${t.localizedMessage}")
            }
        })
    }

    private fun getFormDetails() {
        Log.d("getFormDetails", "getFormDetails: function call: formId: $formId")
        val call = RetrofitClient.getClient().getSingleFormData(formId.toString(),SharedPrefManager.getInstance(this)?.user?.id.toString());
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d("getFormDetails", "onResponse: response success: ${response.body()}")
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            val dataObj = dataArray?.get(0)?.asJsonObject
                            val modal = Gson().fromJson(dataObj, FormDataModal::class.java)
                            binding.aoFormTitle.text = modal.name
                            binding.formType.text = modal.type
                            binding.textView5.text = "Level: ${modal.level}"
//                            binding.afdGovtPrice.text = "₹${modal.charges}"
//                            binding.afdExtraCharges.text = "₹${modal.extra_charges}"
//                            val totalPrice = modal.charges.toInt() + modal.extra_charges.toInt()
//                            binding.afdTotalPrice.text = "₹${totalPrice}"
//                            FormDetailActivity.requiredDocs = modal.requirements
//                            setEligibilityList(modal.eligibility)
//                            setRequiredDocsList(modal.requirements)

                        }
                    } else {
                        Log.d("getFormDetails", "onResponse: response not success: ${response.errorBody()?.string()} error code: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.d("getFormDetails", "onResponse: Exception found ${e.localizedMessage}")
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getFormDetails", "onFailure: ${t.localizedMessage}")
            }
        })
    }
    
}