package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityFormDetailBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.modal.FormDataModal
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormDetailActivity : AppCompatActivity() {

    private val TAG: String = "getFormDetails"
    lateinit var binding: ActivityFormDetailBinding

    companion object {
        var requiredDocs: List<String> = ArrayList<String>()
    }

    var id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.afdToolbar.setNavigationOnClickListener { finish() }
        id = intent.getStringExtra("formId").toString()
        getFormDetails()

        binding.afdApplyNow.setOnClickListener {
            startActivity(Intent(this, DocumentUploadActivity::class.java).putExtra("formId", id))
        }

    }

    private fun getFormDetails() {
        Log.d(TAG, "getFormDetails: function call: formId: $id")
        val call = RetrofitClient.getClient()
            .getSingleFormData(id, SharedPrefManager.getInstance(this)?.user?.id.toString())
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: response success: ${response.body()}")
                        val jsonObject = response.body()
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            val dataObj = dataArray?.get(0)?.asJsonObject
                            val modal = Gson().fromJson(dataObj, FormDataModal::class.java)
                            binding.afdFormName.text = modal.name
                            binding.afdFormLevel.text = "Level: ${modal.level}"
                            binding.afdGovtPrice.text = "₹${modal.charges}"
                            binding.afdExtraCharges.text = "₹${modal.extra_charges}"
                            binding.afdResultPrice.text = "₹${modal.result_charges}"
                            binding.afdAdmitCharges.text = "₹${modal.admit_card_charges}"
                            binding.formStatus.text = "Status: ${modal.status}"
                            val totalPrice =
                                modal.charges.toInt() + modal.extra_charges.toInt() + modal.result_charges.toInt() + modal.admit_card_charges.toInt()
                            binding.afdTotalPrice.text = "₹${totalPrice}"
                            binding.formType.text = modal.type
                            requiredDocs = modal.requirements
                            setEligibilityList(modal.eligibility)
                            setRequiredDocsList(modal.requirements)

                        }
                    } else {
                        Log.d(
                            TAG,
                            "onResponse: response not success: ${
                                response.errorBody()?.string()
                            } error code: ${response.code()}"
                        )
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
        /*  val arrayAdapter = ArrayAdapter(this, R.layout.list_view_lay,requirements)
          binding.afdRequiredList.adapter = arrayAdapter*/

        val backgroundDrawable = resources.getDrawable(R.drawable.text_type_background, null)

        for (item in requirements) {
            val textView = getTextView(backgroundDrawable, "$item")
            binding.afdRequiredLay.addView(textView)
        }


    }

    private fun setEligibilityList(eligibility: List<String>) {
        /* val arrayAdapter = ArrayAdapter(this, R.layout.list_view_lay,eligibility)
         binding.afdEligibleList.adapter = arrayAdapter*/

        val backgroundDrawable = resources.getDrawable(R.drawable.text_type_background, null)

        for (item in eligibility) {
            val textView = getTextView(backgroundDrawable, "$item")
            binding.afdEligibleLay.addView(textView)
        }


    }

    private fun getTextView(background: Drawable, text: String): TextView {
        val textView = TextView(this@FormDetailActivity)
        textView.text = text
        textView.background = background
        val marginParams = MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,  // Width
            LayoutParams.WRAP_CONTENT // Height
        )
        marginParams.setMargins(0, 8, 12, 0)
        textView.layoutParams = marginParams
        textView.setTypeface(null, Typeface.BOLD)
        textView.setTextColor(ContextCompat.getColor(this@FormDetailActivity, R.color.black))
        return textView
    }


}