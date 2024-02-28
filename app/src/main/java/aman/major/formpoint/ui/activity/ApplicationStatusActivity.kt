package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityApplicationStatusBinding
import aman.major.formpoint.helper.ADMIT_CARD_LOC
import aman.major.formpoint.helper.ImageDownloadTask
import aman.major.formpoint.helper.RECEIPT_LOC
import aman.major.formpoint.helper.RESULT_LOC
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.helper.UriToFileConverter
import aman.major.formpoint.modal.AppliedFormModal
import aman.major.formpoint.modal.FormDataModal
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
class ApplicationStatusActivity : AppCompatActivity() {

    var formId: String? = null
    lateinit var binding: ActivityApplicationStatusBinding
    private var flag: String? = null
    var uri: Uri? = null
    var modal: AppliedFormModal? = null
    var id: String? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        formId = intent.getStringExtra("formId")
        id = intent.getStringExtra("id")
        flag = intent.getStringExtra("flag")

        getSingleAppliedForm()

        val task = ImageDownloadTask(this@ApplicationStatusActivity)


        binding.uploadFileButton.setOnClickListener {
            sendIntentToPick(201)
        }

        when (flag) {
            "applicationStatus" -> {
                binding.asToolbar.title = "Application Status"
            }

            "result" -> {
                binding.asToolbar.title = "Result"
            }

            "admitCard" -> {
                binding.asToolbar.title = "Admit Card"
            }
        }

        binding.asToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.asDownloadReciept.setOnClickListener {
            if (flag.equals("applicationStatus", true)) {
                Log.d("checkingDoc", "onCreate: Button Clicked: Reciept: " + modal?.reciept)

                if (modal?.reciept.isNullOrBlank()) {
                    Toast.makeText(
                        this,
                        "Your Form Receipt Not Uploaded Yet...",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    task.execute(RECEIPT_LOC+modal?.reciept)
                }
            } else if (modal?.reciept.isNullOrBlank() && flag.equals(
                    "admitCard",
                    true
                )
            ) {
                if (uri.toString().isNotEmpty() && binding.transactionId.text.toString()
                        .isNotEmpty()
                ) {
                    val file =
                        UriToFileConverter.convertUriToFile(this@ApplicationStatusActivity, uri)
                    uploadDocs(
                        SharedPrefManager.getInstance(this@ApplicationStatusActivity)?.user?.id.toString(),
                        formId.toString(),
                        binding.transactionId.text.toString(),
                        "result",
                        file
                    )
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }
            } else if (modal?.result.isNullOrBlank() && flag.equals("Result", true)) {
                if (uri.toString().isNotEmpty() && binding.transactionId.text.toString()
                        .isNotEmpty()) {
                    val file = UriToFileConverter.convertUriToFile2(this@ApplicationStatusActivity, uri)
                        uploadDocs(
                        SharedPrefManager.getInstance(this@ApplicationStatusActivity)?.user?.id.toString(),
                        formId.toString(),
                        binding.transactionId.text.toString(),
                        "admit_card",
                        file
                    )
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                }
            }else if (modal?.admit_card.toString().isNotEmpty() && flag.equals("admitCard", true)) {
                task.execute(ADMIT_CARD_LOC+modal?.admit_card)
            }
            else if (modal?.result.toString().isNotEmpty() && flag.equals("Result", true)) {
                task.execute(RESULT_LOC+modal?.result)
            }
        }

    }

    private fun sendIntentToPick(requestCode: Int) {
        if (checkPermission()) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, requestCode)
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                20
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                20
            )
        }

    }


    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 201 && resultCode == RESULT_OK) {
            val data = data?.data!!
            uri = data
            binding.uploadImg.setImageURI(data)
        }

    }

    private fun getSingleAppliedForm() {
        Log.d("getSingleAppliedForm", "getAppliedForm: function call: formId: $formId")
        val call = RetrofitClient.getClient()
            .getAppliedFormStatusInDetail(SharedPrefManager.getInstance(this@ApplicationStatusActivity)?.user?.id.toString(),formId,id)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(
                            "getSingleAppliedForm",
                            "onResponse: response success: ${response.body()}"
                        )
                       val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", false)) {
                            val dataObject = jsonObject?.get("data")?.asJsonObject
                            val dataModal = Gson().fromJson(dataObject,AppliedFormModal::class.java)

                            setTheDataOfForm(dataModal)

                            modal = dataModal

                            Log.d("checkingDoc", "onResponse: datamodal Reciept ${dataModal.reciept}")

                           /* // binding.ooRecycler.adapter = RecyclerAppliedFormAdapter(this@OnlineOpportunityActivity,appliedFormList,status)*/
                       }
                    } else {
                        Log.d(
                            "getSingleAppliedForm",
                            "onResponse: resposne not success: ${response.errorBody()?.string()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.d("getSingleAppliedForm", "onResponse: Exception ${e.localizedMessage}")

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getSingleAppliedForm", "onFailure: failed: ${t.localizedMessage}")
            }
        })

    }

    private fun setTheDataOfForm(modal: AppliedFormModal) {
        getFormDetails(modal.form_id, this@ApplicationStatusActivity)
        val applicantsDetail = modal.applicant_details
        binding.asName.text = applicantsDetail.applicant_name
        binding.asEmail.text = applicantsDetail.email
        binding.asPhone.text = applicantsDetail.mobile
        binding.asAddress.text = applicantsDetail.address
        binding.asTxnId.text = modal.txn_id
        binding.asFormStatus.text = modal.status
        binding.afdFormName.text = modal.form_details.name
        binding.asApplyNow.text = "Applied On: ${modal.created_at}"
        handleTheButtonText(modal)
    }

    private fun handleTheButtonText(modal: AppliedFormModal) {
        Log.d("handleTheButtonText", "handleTheButtonText: flag: $flag -> application status")
        if (flag.equals("applicationStatus", true)) {
            binding.downloadText.text = resources.getString(R.string.download_reciept)
            binding.uploadRecievingLay.visibility = View.GONE
        } else if (modal.admit_card.isNullOrBlank() && flag.equals("admitCard",
                true)
        ) {
            binding.downloadText.text = resources.getString(R.string.upload_reciept)
            binding.uploadRecievingLay.visibility = View.VISIBLE
        } else if (modal.result.isNullOrBlank() and flag.equals("Result", true)) {
            binding.downloadText.text = resources.getString(R.string.upload_admit_card)
            binding.uploadRecievingLay.visibility = View.VISIBLE
        }else if (modal.admit_card.isNotEmpty() and flag.equals("admitCard", true)) {
            binding.downloadText.text = resources.getString(R.string.download_admit_card)
            binding.uploadRecievingLay.visibility = View.GONE
        }
        else if (modal.result.isNotEmpty() and flag.equals("Result", true)) {
            binding.downloadText.text = resources.getString(R.string.download_result)
            binding.uploadRecievingLay.visibility = View.GONE
        }

    }


    private fun getFormDetails(id: String, context: Context) {
        Log.d("getFormDetails", "getFormDetails: function call: formId: $id")
        val call = RetrofitClient.getClient()
            .getSingleFormData(id, SharedPrefManager.getInstance(context)?.user?.id.toString());
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
                            binding.formType.text = modal.type
//                            binding.formLocation.text = "Level: ${modal.level}"
//                            binding.afdFormName.text = modal.name
//                            binding.afdFormLevel.text = "Level: ${modal.level}"
//                            binding.afdGovtPrice.text = "₹${modal.charges}"
//                            binding.afdExtraCharges.text = "₹${modal.extra_charges}"
//                            val totalPrice = modal.charges.toInt() + modal.extra_charges.toInt()
//                            binding.afdTotalPrice.text = "₹${totalPrice}"
//                            FormDetailActivity.requiredDocs = modal.requirements
//                            setEligibilityList(modal.eligibility)
//                            setRequiredDocsList(modal.requirements)

                        }
                    } else {
                        Log.d(
                            "getFormDetails",
                            "onResponse: response not success: ${
                                response.errorBody()?.string()
                            } error code: ${response.code()}"
                        )
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

    private fun uploadDocs(
        uId: String,
        fId: String,
        transactionId: String,
        typeDocs: String,
        imageFile: File
    ) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Uploading $typeDocs...")
        progressDialog.show()
        Log.d(
            "uploadDocs",
            "uploadDocs: UserId: $uId formId: $fId transactionId $transactionId type $typeDocs img status ${imageFile.exists()} file name ${imageFile.name}"
        )


        val userId = uId.toRequestBody("text/plain".toMediaTypeOrNull())
        val formId = fId.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = prepareFilePart("reciept", uri.toString())
        val type = typeDocs.toRequestBody("text/plain".toMediaTypeOrNull())
        val txnId = transactionId.toRequestBody("text/plain".toMediaTypeOrNull())

        val call = RetrofitClient.getClient().uploadDocs(userId, formId, imagePart, type, txnId)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                try {
                    if (response.isSuccessful) {
                        Log.d("uploadDocs", "onResponse: response success: ${response.body()}")
                        val jsonObject = response.body()
                        Log.d(
                            "uploadDocs",
                            "onResponse: messages: ${jsonObject?.get("message")?.asString}"
                        )
                        Toast.makeText(
                            this@ApplicationStatusActivity,
                            "${jsonObject?.get("message")?.asString}",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressDialog.dismiss()
                        // Handle the successful response
                    } else {
                        // Handle the error response
                        Log.d(
                            "uploadDocs",
                            "onResponse: respones not success ${
                                response.errorBody()?.string()
                            } code ${response.code()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.d("uploadDocs", "onResponse: Exception ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle the failure
                Log.d("uploadDocs", "onFailure: failed: ${t.localizedMessage}")
            }
        })


    }


    private fun prepareFilePart(key: String, filePath: String): MultipartBody.Part {

        Log.d("prepareFilePart", "prepareFilePart: key: $key uri: $filePath")

        val file =
            UriToFileConverter.convertUriToFile(
                this@ApplicationStatusActivity,
                Uri.parse(filePath)
            )
        val requestBody = RequestBody.create(
            contentResolver.getType(Uri.parse(filePath))?.let { it.toMediaTypeOrNull() }, file
        )
        Log.d(
            "prepareFilePart",
            "prepareFilePart: file exist status ${file.exists()} name ${file.name}"
        )
        Log.d(
            "prepareFilePart",
            "content type ${requestBody.contentType()} request file ${requestBody.contentLength()} duplex ${requestBody.isDuplex()} "
        )
        val multipartBody = MultipartBody.Part.createFormData(key, file.name, requestBody)

        return multipartBody
    }


    /*private fun removeQuotesAndUnescape(uncleanJson: String): String? {
        val noQuotes = uncleanJson.replace("^\"|\"$".toRegex(), "")
        return StringEs.unescapeJava(noQuotes)
    }*/

}