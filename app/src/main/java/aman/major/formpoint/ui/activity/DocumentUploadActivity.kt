package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityDocumentUploadBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.helper.UriToFileConverter
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.skydoves.elasticviews.ElasticCardView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocumentUploadActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDocumentUploadBinding

    var aadharUri: Uri? = null
    var photoUri: Uri? = null
    var signUri: Uri? = null
    var eightMarksUri: Uri? = null
    var tentUri: Uri? = null
    var twelUri: Uri? = null
    var gradUri: Uri? = null
    var pGradUri: Uri? = null
    var incmUri: Uri? = null
    var residence: Uri? = null
    var cast: Uri? = null
    var panCardUri: Uri? = null
    var bankPass: Uri? = null
    var ewsUri: Uri? = null
    var ncc: Uri? = null
    var sportUri: Uri? = null
    var nss: Uri? = null
    var affeDevitUri: Uri? = null
    var otherUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formId = intent.getStringExtra("formId")

        val docsArray = arrayOf(
            "Aadhar",
            "Photo",
            "Signature",
            "8th-Marksheet",
            "10th-Marksheet",
            "12th-Marksheet",
            "Graduation-Marksheet",
            "Post-Graduation",
            "Income-Certificate",
            "Residence-Certificate",
            "Caste-Certificate",
            "Pancard",
            "Bank-Passbook",
            "EWS",
            "NCC-Certificate",
            "Sports-Certificate",
            "NSS-Certificate",
            "Affidavit",
            "Other"
        )

        val layoutArray = arrayOf(
            binding.addharLay,
            binding.photoLay,
            binding.signaturLay,
            binding.eightMarksLay,
            binding.tenthMarksLay,
            binding.tenthMarksLay,
            binding.twelMarksLay,
            binding.gradMarksLay,
            binding.pGradLay,
            binding.incmLay,
            binding.residenceLay,
            binding.castLay,
            binding.bankPassbookLay,
            binding.ewsLay,
            binding.ncclay,
            binding.sportsCerfLay,
            binding.nssLay,
            binding.aafidevitLay,
            binding.otherLay
        )


        for (i in FormDetailActivity.requiredDocs.indices) {
            val str = FormDetailActivity.requiredDocs[i]
            val pst = docsArray.indexOf(str)
            val layout = layoutArray[pst] as LinearLayout
            layout.visibility = View.VISIBLE
        }


        binding.duToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.uploadPhoto.setOnClickListener {
            sendIntentToPick(101)
        }

        binding.aadharUpload.setOnClickListener {
            sendIntentToPick(102)
        }

        binding.uploadSign.setOnClickListener {
            sendIntentToPick(103)
        }

        binding.uploadEightMarks.setOnClickListener {
            sendIntentToPick(104)
        }
        binding.uploadtenth.setOnClickListener {
            sendIntentToPick(105)
        }
        binding.uploadTwelth.setOnClickListener {
            sendIntentToPick(106)
        }
        binding.uploadGrad.setOnClickListener {
            sendIntentToPick(107)
        }
        binding.uploadpGrad.setOnClickListener {
            sendIntentToPick(108)
        }
        binding.uploadIncm.setOnClickListener {
            sendIntentToPick(109)
        }
        binding.uploadCast.setOnClickListener {
            sendIntentToPick(110)
        }
        binding.uploadResidence.setOnClickListener {
            sendIntentToPick(111)
        }
        binding.uploadPancard.setOnClickListener {
            sendIntentToPick(112)
        }
        binding.uploadBankPass.setOnClickListener {
            sendIntentToPick(113)
        }
        binding.uploadEws.setOnClickListener {
            sendIntentToPick(114)
        }
        binding.uploadNcc.setOnClickListener {
            sendIntentToPick(115)
        }
        binding.uploadSports.setOnClickListener {
            sendIntentToPick(116)
        }
        binding.uploadNss.setOnClickListener {
            sendIntentToPick(117)
        }
        binding.uploadAffidevit.setOnClickListener {
            sendIntentToPick(118)
        }
        binding.uploadOther.setOnClickListener {
            sendIntentToPick(119)
        }

        binding.duUploadButton.setOnClickListener {


            if (binding.userName.text.toString().isNotEmpty()
                and binding.userEmail.text.toString().isNotEmpty()
                and binding.userEmail.text.toString().isNotEmpty()
                and binding.userMobile.text.toString().isNotEmpty()
                and binding.userTxnId.text.toString().isNotEmpty()
            ) {
                uploadFormInApi(
                    SharedPrefManager.getInstance(this)?.user?.id.toString(),
                    binding.userEmail.text.toString(),
                    binding.userMobile.text.toString(),
                    formId.toString(),
                    binding.userName.text.toString(),
                    binding.userAddress.text.toString(),
                    binding.userTxnId.text.toString()
                )
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun uploadFormInApi(
        userId: String,
        email: String,
        phone: String,
        formId: String,
        userName: String,
        address: String,
        txnId: String
    ) {
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Applying Form")
        progressDialog.show()
        Log.d(
            "uploadFormInApi",
            "uploadFormInApi: function call: userId: $userId formId: $formId phone $phone"
        )
        Log.d("uploadFormInApi", "photo -> $photoUri")
        val userIdRequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val formIdRequestBody = formId.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneRequestBody = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val useNameRequestBody = userName.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressRequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val txnIdRequestBody = txnId.toRequestBody("text/plain".toMediaTypeOrNull())
        val photoPart = prepareFilePart("Photo", photoUri.toString())
        val aadharPart = prepareFilePart("Aadhar", photoUri.toString())
        val signaturePart = prepareFilePart("Signature", signUri.toString())
        val eightMarksPart = prepareFilePart("8th-Marksheet", eightMarksUri.toString())
        val tenthMarksPart = prepareFilePart("10th-Marksheet", tentUri.toString())
        val twelthMarksPart = prepareFilePart("12th-Marksheet", twelUri.toString())
        val gradMarksPart = prepareFilePart("Graduation-Marksheet", gradUri.toString())
        val postgradMarksPart = prepareFilePart("Post-Graduation", pGradUri.toString())
        val incmPart = prepareFilePart("Income-Certificate", incmUri.toString())
        val residencePart = prepareFilePart("Residence-Certificate", residence.toString())
        val castPart = prepareFilePart("Caste-Certificate", cast.toString())
        val pancardPart = prepareFilePart("Pancard", panCardUri.toString())
        val bankPart = prepareFilePart("Bank-Passbook", bankPass.toString())
        val ewsPart = prepareFilePart("EWS", bankPass.toString())
        val nccPart = prepareFilePart("NCC-Certificate", ncc.toString())
        val sportsPart = prepareFilePart("Sports-Certificate", sportUri.toString())
        val nssPart = prepareFilePart("NSS-Certificate", nss.toString())
        val affedivitPart = prepareFilePart("Affidavit", affeDevitUri.toString())
        val otherPart = prepareFilePart("Other", otherUri.toString())


        val call = RetrofitClient.getClient().saveForm(
            useNameRequestBody,
            phoneRequestBody,
            addressRequestBody,
            userIdRequestBody,
            emailRequestBody,
            formIdRequestBody,
            txnIdRequestBody,
            aadharPart,
            photoPart,
            signaturePart,
            eightMarksPart,
            tenthMarksPart,
            twelthMarksPart,
            gradMarksPart,
            postgradMarksPart,
            incmPart,
            residencePart,
            castPart,
            pancardPart,
            bankPart,
            ewsPart,
            nccPart,
            sportsPart,
            nssPart,
            affedivitPart,
            otherPart
        )

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(
                            "uploadFormInApi",
                            "onResponse: response is success: ${response.body()}"
                        )
                        val responseBody = response.body();
                        if (responseBody?.get("res")?.asString.equals("success", false)) {
                            Toast.makeText(
                                this@DocumentUploadActivity,
                                "${responseBody?.get("message")?.asString}",
                                Toast.LENGTH_SHORT
                            ).show()
                            handleSuccessBottomSheet()
                        }
                    } else {
                        Log.d(
                            "uploadFormInApi",
                            "onResponse: response is success: ${response.errorBody()?.string()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.d(
                        "uploadFormInApi",
                        "onResponse: response is Exception: ${e.localizedMessage}}"
                    )
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("uploadFormInApi", "onFailure: ${t.localizedMessage}}")
                progressDialog.dismiss()
                Toast.makeText(
                    this@DocumentUploadActivity,
                    "Something Went Wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun handleSuccessBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_form, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.setCancelable(false)

        val backtohome = bottomSheetView.findViewById<ElasticCardView>(R.id.backToHome)
        backtohome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun sendIntentToPick(requestCode: Int) {
        if (checkPermission()) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, requestCode)
        } else {
            requestPermission()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val data = data?.data!!
        if (data != null) {
            Log.d("gettingPhotoUri", "onActivityResult: photoUri")
            when (requestCode) {
                101 -> {
                    binding.photoImg.setImageURI(data)
                    setInvisibleLottie(binding.photoJson)
                    photoUri = data
                }

                102 -> {
                    binding.aadharImg.setImageURI(data)
                    aadharUri = data
                    setInvisibleLottie(binding.aadharJson)
                }

                103 -> {
                    binding.signaturImg.setImageURI(data)
                    setInvisibleLottie(binding.signatureJson)
                    signUri = data
                }

                104 -> {
                    binding.eightMarkImg.setImageURI(data)
                    eightMarksUri = data
                    setInvisibleLottie(binding.eightMarkJson)
                }

                105 -> {
                    binding.tenthMarksImg.setImageURI(data)
                    setInvisibleLottie(binding.tenthJson)
                    tentUri = data
                }

                106 -> {
                    binding.twelMarksImg.setImageURI(data)
                    twelUri = data
                    setInvisibleLottie(binding.twelthMarksJson)
                }

                107 -> {
                    binding.gradImg.setImageURI(data)
                    gradUri = data
                    setInvisibleLottie(binding.gradMarksJson)
                }

                108 -> {
                    binding.pGradImg.setImageURI(data)
                    pGradUri = data
                    setInvisibleLottie(binding.pGradJson)
                }

                109 -> {
                    binding.incmImg.setImageURI(data)
                    incmUri = data
                    setInvisibleLottie(binding.incmJson)
                }

                110 -> {
                    binding.castImg.setImageURI(data)
                    cast = data
                    setInvisibleLottie(binding.castJson)
                }

                111 -> {
                    binding.residenceImg.setImageURI(data)
                    residence = data
                    setInvisibleLottie(binding.residenceJson)
                }

                112 -> {
                    binding.panCardImg.setImageURI(data)
                    panCardUri = data
                    setInvisibleLottie(binding.pancardJson)
                }

                113 -> {
                    binding.bankPassbookImg.setImageURI(data)
                    bankPass = data
                    setInvisibleLottie(binding.bankPassBookJson)
                }


                114 -> {
                    binding.ewsImg.setImageURI(data)
                    ewsUri = data
                    setInvisibleLottie(binding.ewsJson)
                }

                115 -> {
                    binding.nccImg.setImageURI(data)
                    ncc = data
                    setInvisibleLottie(binding.nccjson)
                }

                116 -> {
                    binding.sportsCerfImg.setImageURI(data)
                    sportUri = data
                    setInvisibleLottie(binding.sportsCerfJson)
                }

                117 -> {
                    binding.nssImg.setImageURI(data)
                    nss = data
                    setInvisibleLottie(binding.nssJson)
                }

                118 -> {
                    binding.affedivitImg.setImageURI(data)
                    affeDevitUri = data
                    setInvisibleLottie(binding.affedivitJson)
                }

                119 -> {
                    binding.otherIMg.setImageURI(data)
                    otherUri = data
                    setInvisibleLottie(binding.otherJson)
                }
            }
        }
    }

    private fun setInvisibleLottie(photoJson: LottieAnimationView) {
        photoJson.visibility = View.INVISIBLE
    }

    private fun prepareFilePart(key: String, filePath: String): MultipartBody.Part {

        Log.d("prepareFilePart", "prepareFilePart: key: $key uri: $filePath")

        val file =
            UriToFileConverter.convertUriToFile2(this@DocumentUploadActivity, Uri.parse(filePath))
        val requestBody = RequestBody.create(contentResolver.getType(Uri.parse(filePath))?.let { it.toMediaTypeOrNull() },file)
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


}