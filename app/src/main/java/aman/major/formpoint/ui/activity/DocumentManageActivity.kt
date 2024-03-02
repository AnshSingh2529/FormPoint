package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityDocumentManageBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.helper.UriToFileConverter
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocumentManageActivity : AppCompatActivity() {


    lateinit var binding: ActivityDocumentManageBinding

    var aadharUri: Uri = Uri.parse("")
    var photoUri: Uri = Uri.parse("")
    var signUri: Uri = Uri.parse("")
    var eightMarksUri: Uri = Uri.parse("")
    var tentUri: Uri = Uri.parse("")
    var twelUri: Uri = Uri.parse("")
    var gradUri: Uri = Uri.parse("")
    var pGradUri: Uri = Uri.parse("")
    var incmUri: Uri = Uri.parse("")
    var residence: Uri = Uri.parse("")
    var cast: Uri = Uri.parse("")
    var panCardUri: Uri = Uri.parse("")
    var bankPass: Uri = Uri.parse("")
    var ewsUri: Uri = Uri.parse("")
    var ncc: Uri = Uri.parse("")
    var sportUri: Uri = Uri.parse("")
    var nss: Uri = Uri.parse("")
    var affeDevitUri: Uri = Uri.parse("")
    var otherUri: Uri = Uri.parse("")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dmToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.photoLay.setOnClickListener {
            sendIntentToPick(101)
        }

        binding.adharLay.setOnClickListener {
            sendIntentToPick(102)
        }

        binding.signaturLay.setOnClickListener {
            sendIntentToPick(103)
        }
        binding.eightMarkLay.setOnClickListener {
            sendIntentToPick(104)
        }
        binding.tenthMarksLay.setOnClickListener {
            sendIntentToPick(105)
        }
        binding.twelMarksLay.setOnClickListener {
            sendIntentToPick(106)
        }
        binding.gradmarksLay.setOnClickListener {
            sendIntentToPick(107)
        }
        binding.pGradLay.setOnClickListener {
            sendIntentToPick(108)
        }
        binding.incmLay.setOnClickListener {
            sendIntentToPick(109)
        }
        binding.castLay.setOnClickListener {
            sendIntentToPick(110)
        }
        binding.pancardLay.setOnClickListener {
            sendIntentToPick(111)
        }
        binding.bankPassbookLay.setOnClickListener {
            sendIntentToPick(112)
        }
        binding.residencLay.setOnClickListener {
            sendIntentToPick(113)
        }
        binding.ewsLay.setOnClickListener {
            sendIntentToPick(114)
        }
        binding.ncclay.setOnClickListener {
            sendIntentToPick(115)
        }
        binding.sportsCerfLay.setOnClickListener {
            sendIntentToPick(116)
        }
        binding.nssLay.setOnClickListener {
            sendIntentToPick(117)
        }
        binding.affidevitLay.setOnClickListener {
            sendIntentToPick(118)
        }
        binding.otherLay.setOnClickListener {
            sendIntentToPick(119)
        }







        binding.dmUploadDocs.setOnClickListener {
            if(photoUri.toString().isNotEmpty()){
                uploadDocumentApiCall(
                    SharedPrefManager.getInstance(this@DocumentManageActivity)?.user?.id.toString()
                )
            }else{
                Toast.makeText(this, "empty field", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun sendIntentToPick(requestCode: Int) {
        
        if (checkPermission()) {
            val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(imageIntent, requestCode)
        } else {
            requestPermission()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val data = data?.data
        if (data != null) {
            when (requestCode) {
                101 -> {
                    binding.photoImg.setImageURI(data)
                    setInvisibleLottie(binding.photoJson)
                    photoUri = data
                }

                102 -> {
                    binding.adharImg.setImageURI(data)
                    aadharUri = data
                    setInvisibleLottie(binding.adharJson)
                }

                103 -> {
                    binding.signImg.setImageURI(data)
                    setInvisibleLottie(binding.signatureJson)
                    signUri = data
                }

                104 -> {
                    binding.eightmarksImg.setImageURI(data)
                    eightMarksUri = data
                    setInvisibleLottie(binding.eightMarkJson)
                }

                105 -> {
                    binding.tenthMarksImg.setImageURI(data)
                    setInvisibleLottie(binding.tenthMarksJson)
                    tentUri = data
                }

                106 -> {
                    binding.twelMarksImg.setImageURI(data)
                    twelUri = data
                    setInvisibleLottie(binding.twelMarksJson)
                }

                107 -> {
                    binding.gradMarksImg.setImageURI(data)
                    gradUri = data
                    setInvisibleLottie(binding.gradMarksJson)
                }

                108 -> {
                    binding.pGradImg.setImageURI(data)
                    pGradUri = data
                    setInvisibleLottie(binding.pGradJson)
                }

                109 -> {
                    binding.incmJson.setImageURI(data)
                    incmUri = data
                    setInvisibleLottie(binding.incmLayJson)
                }

                110 -> {
                    binding.castImg.setImageURI(data)
                    cast = data
                    setInvisibleLottie(binding.castJson)
                }

                111 -> {
                    binding.panCardImg.setImageURI(data)
                    panCardUri = data
                    setInvisibleLottie(binding.pancardJson)
                }

                112 -> {
                    binding.bankPassbookImg.setImageURI(data)
                    bankPass = data
                    setInvisibleLottie(binding.bankPassBookJson)
                }

                113 -> {
                    binding.residenceImg.setImageURI(data)
                    residence = data
                    setInvisibleLottie(binding.residenceJson)
                }

                114 -> {
                    binding.ewsImg.setImageURI(data)
                    ewsUri = data
                    setInvisibleLottie(binding.ewsJson)
                }

                115 -> {
                    binding.nccImg.setImageURI(data)
                    ncc= data
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
                    binding.affedevitImg.setImageURI(data)
                    affeDevitUri = data
                    setInvisibleLottie(binding.affedevitJson)
                }

                119 -> {
                    binding.otherImg.setImageURI(data)
                    otherUri = data
                    setInvisibleLottie(binding.otherJson)
                }
            }
        }
    }

    private fun setInvisibleLottie(photoJson: LottieAnimationView) {
        photoJson.visibility = View.INVISIBLE
    }


    fun uploadDocumentApiCall(
        userId: String,
    ) {
        val dialog = ProgressDialog(this@DocumentManageActivity)
        dialog.setCancelable(false)
        dialog.setMessage("Uploading Documents")
        dialog.show()

        Log.d("uploadDocumentApiCall", "uploadDocumentApiCall: function call")

        val userIdRequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val aadharPart = prepareFilePart("Aadhar", aadharUri.toString())
        val photoPart = prepareFilePart("Photo", photoUri.toString())
        val signaturePart = prepareFilePart("Signature", signUri.toString())
        val eightMarks = prepareFilePart("8th-Marksheet", eightMarksUri.toString())
        val tenthMarks = prepareFilePart("10th-Marksheet", tentUri.toString())
        val twel = prepareFilePart("12th-Marksheet", twelUri.toString())
        val grad = prepareFilePart("Graduation-Marksheet", gradUri.toString())
        val post = prepareFilePart("Post-Graduation", pGradUri.toString())
        val income = prepareFilePart("Income-Certificate", incmUri.toString())
        val residence = prepareFilePart("Residence-Certificate", residence.toString())
        val caste = prepareFilePart("Caste-Certificate", cast.toString())
        val pancard = prepareFilePart("Pancard", panCardUri.toString())
        val bank = prepareFilePart("Bank-Passbook", bankPass.toString())
        val ncc = prepareFilePart("NCC-Certificate", ncc.toString())
        val sports = prepareFilePart("Sports-Certificate", sportUri.toString())
        val nss = prepareFilePart("NSS-Certificate", nss.toString())
        val affedivit = prepareFilePart("Affidavit", affeDevitUri.toString())
        val other = prepareFilePart("Other", otherUri.toString())

        val call = RetrofitClient.getClient().uploadDocument(
            userIdRequestBody,
            aadharPart,
            photoPart,
            signaturePart,
            eightMarks,
            tenthMarks,
            twel,
            grad,
            post,
            income,
            residence,
            caste,
            pancard,
            bank,
            ncc,
            sports,
            nss,
            affedivit,
            residence,
            other
        )

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    Log.d(
                        "uploadDocumentApiCall",
                        "uploadDocumentApiCall: function ${response.body()}"
                    )
                    val jsonObject = response.body()
                    if (jsonObject?.get("status")?.asString.equals("success")) {
                        Toast.makeText(
                            this@DocumentManageActivity,
                            "${jsonObject?.get("message")?.asString}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                    //Toast.makeText(this@DocumentManageActivity, "Document Upload Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(
                    "uploadDocumentApiCall",
                    "uploadDocumentApiCall: onFailure ${t.localizedMessage}"
                )
            }
        })
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                20
            )
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                20
            )
        }
    }

    private fun prepareFilePart(key: String, filePath: String): MultipartBody.Part {

        Log.d("prepareFilePart", "prepareFilePart: key: $key uri: $filePath")

        val file =
            UriToFileConverter.convertUriToFile2(this@DocumentManageActivity, Uri.parse(filePath))
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
