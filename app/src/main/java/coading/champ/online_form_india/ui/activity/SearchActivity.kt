package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.adapter.RecyclerSearchAdapter
import coading.champ.online_form_india.databinding.ActivitySearchBinding
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.modal.FormSearchModal
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale
import java.util.Objects


class SearchActivity : AppCompatActivity() {


    private val REQUEST_CODE_SPEECH_INPUT: Int = 1001

    lateinit var binding : ActivitySearchBinding;

    val searchList = ArrayList<FormSearchModal>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val key = intent.getIntExtra("searchKey", 0)
        getSearchListFromApi()

        binding.asBack.setOnClickListener {
            finish()
        }

        binding.asMic.setOnClickListener {
            sendVoiceIntent()
        }

        when (key) {
            0->{
                binding.seachText.requestFocus()
            }
            1->{
                sendVoiceIntent()
            }

        }

        binding.seachText.addTextChangedListener {text: Editable? ->
            if (text.toString().isNullOrEmpty()){
                binding.asEmptySearchImg.visibility = View.VISIBLE
                binding.searchResultRecycler.visibility = View.INVISIBLE
                binding.asInstructionText.visibility = View.VISIBLE
                binding.asInstructionText.text = "Search For Result"
                binding.asEmptySearchImg.setImageResource(R.drawable.search_for_result)
            }else{
                binding.asEmptySearchImg.visibility = View.INVISIBLE
                binding.asInstructionText.visibility = View.INVISIBLE
                binding.searchResultRecycler.visibility = View.VISIBLE
                performFilteringData(text.toString().toLowerCase().trim())
            }
        }
    }

    private fun performFilteringData(text: String) {
        if (searchList.size > 0){
            val filterList = ArrayList<FormSearchModal>()
            for (i in 0..<searchList.size){
                val modal = searchList[i]
                if (modal.form_name.trim().toLowerCase().contains(text)){
                    filterList.add(modal)
                }
            }
            if (filterList.size > 0){
                binding.asEmptySearchImg.visibility = View.INVISIBLE
                binding.asInstructionText.visibility = View.INVISIBLE
                binding.searchResultRecycler.visibility = View.VISIBLE
                binding.searchResultRecycler.adapter = RecyclerSearchAdapter(this@SearchActivity,filterList)
            }else{
                binding.asEmptySearchImg.visibility = View.VISIBLE
                binding.searchResultRecycler.visibility = View.INVISIBLE
                binding.asInstructionText.visibility = View.VISIBLE
                binding.asInstructionText.text = "No Result Found"
                binding.asEmptySearchImg.setImageResource(R.drawable.no_reord_found)
            }
        }
    }

    private fun getSearchListFromApi() {
        Log.d("getSearchListFromApi", "getSearchListFromApi: function call")
        val call = RetrofitClient.getClient().getAllFormsName()
        call.enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(
                            "getSearchListFromApi",
                            "getSearchListFromApi: onResponse is Successful ${response.body()}"
                        )

                        val jsonObject = response.body()

                        if (jsonObject?.get("status")?.asString.equals("success", false)) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            searchList.clear()
                            for (i in 0 until dataArray!!.size()) {
                                val dataObject = dataArray.get(i).asJsonObject
                                val model = Gson().fromJson(dataObject,FormSearchModal::class.java)
                                searchList.add(model)
                            }

                            Log.d("getSearchListFromApi", "onResponse: searchListSize: ${searchList.size}")
                        }

                    } else {
                        Log.d(
                            "getSearchListFromApi",
                            "getSearchListFromApi: onResponse is not Successful ${
                                response.errorBody()?.string()
                            } code ${response.code()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.d("getSearchListFromApi", "onResponse: Exception : ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getSearchListFromApi", "onResponse: Exception : ${t.localizedMessage}")
            }
        })

    }

    private fun sendVoiceIntent() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast
                .makeText(
                    this@SearchActivity, " " + e.message,
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                binding.seachText.setText(
                    Objects.requireNonNull<ArrayList<String>?>(result)[0]
                )
            }
        }
    }
}