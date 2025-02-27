package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.databinding.ActivityFormDetailBinding
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.SharedPrefManager
import coading.champ.online_form_india.modal.FormDataModal
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coading.champ.online_form_india.helper.Helper
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
    var youtube_link :String? = null


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

        binding.demoVideo.setOnClickListener{
            if (!youtube_link.equals(null)){
                startActivity(Intent(this,VideoPlayActivity::class.java).putExtra("youtubeLink",youtube_link))
            }else{
                Toast.makeText(this, "Video Not Available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getFormDetails() {



        binding.rootLayout.visibility = View.INVISIBLE
        val dialog = Helper.customProgressDialog(this@FormDetailActivity)
        dialog.show()

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
                            binding.afdGenPrice.text = "₹${modal.charge_general}"
                            binding.afdExtraCharges.text = "₹${modal.extra_charges}"
                           // binding.afdResultPrice.text = "₹${modal.result_charges}"
                            binding.afdScCharges.text = "₹${modal.charge_sc_st}"
                            binding.afdObcPrice.text = "₹${modal.charge_obc}"
                            binding.formStatus.text = "Status: ${modal.status}"
                            binding.formType.text = modal.type
                            requiredDocs = modal.requirements

                            youtube_link = modal.url

                            if (modal.status.equals("closed", true)) {
                                binding.afdApplyNow.visibility = View.INVISIBLE
                            }

                            setEligibilityList(modal.eligibility)
                            setRequiredDocsList(modal.requirements)
                            binding.rootLayout.visibility = View.VISIBLE


                            dialog.dismiss()
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