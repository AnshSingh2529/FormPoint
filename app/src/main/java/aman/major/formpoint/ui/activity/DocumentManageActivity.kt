package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityDocumentManageBinding
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.helper.UriToFileConverter
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    var photoUri: Uri = Uri.parse("")
    var aadharUri: Uri = Uri.parse("")
    var signUri: Uri = Uri.parse("")
    var eightMarksUri: Uri = Uri.parse("")
    var incmUri: Uri = Uri.parse("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dmToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.dmPhotoLay.setOnClickListener {
            if (checkPermission()) {
                val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(imageIntent, 101)
            }else{
                requestPermission()
            }
        }

        binding.dmAadharLay.setOnClickListener {

            if (checkPermission()) {
                val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(imageIntent, 102)
            }else{
                requestPermission()
            }
        }

        binding.eightMarksheetLay.setOnClickListener {
            if (checkPermission()) {
                val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(imageIntent, 103)
            }else{
                requestPermission()
            }
        }
        binding.dmSignatureLay.setOnClickListener {
            if (checkPermission()) {
                val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(imageIntent, 104)
            }else{
                requestPermission()
            }
        }
        binding.dmIncmLay.setOnClickListener {

            if (checkPermission()) {
                val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(imageIntent, 105)
            }else{
                requestPermission()
            }
        }


        binding.dmUploadDocs.setOnClickListener {
            uploadDocumentApiCall(SharedPrefManager.getInstance(this@DocumentManageActivity)?.user?.id.toString(),photoUri.toString(),aadharUri.toString(),signUri.toString(),eightMarksUri.toString(), incmUri.toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val data = data?.data
        if (data != null) {
            when (requestCode) {
                101 -> {
                    binding.dmPhotoImgView.setImageURI(data)
                    photoUri = data
                }

                102 -> {
                    binding.dmAadharCardImg.setImageURI(data)
                    aadharUri = data
                }

                103 -> {
                    binding.dm8thMarksheet.setImageURI(data)
                    eightMarksUri = data
                }

                104 -> {
                    binding.dmSignImg.setImageURI(data)
                    signUri = data
                }

                105 -> {
                    binding.dmIncmImg.setImageURI(data)
                    incmUri = data
                }
            }
        }
    }


    fun uploadDocumentApiCall(
        userId: String,
        photoPath: String,
        aadharPath: String,
        signaturePath: String,
        marksheetPath: String,
        incomeCertificatePath: String
    ) {

        Log.d("uploadDocumentApiCall", "uploadDocumentApiCall: function call")

        val userIdRequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val photoPart = prepareFilePart("Photo", photoPath)
        val aadharPart = prepareFilePart("Aadhar", aadharPath)
        val signaturePart = prepareFilePart("Signature", signaturePath)
        val marksheetPart = prepareFilePart("8th-Marksheet", marksheetPath)
        val incomCertificate = prepareFilePart("Income-Certificate", incomeCertificatePath)

        val call = RetrofitClient.getClient().uploadDocument(
            userIdRequestBody,
            photoPart,
            aadharPart,
            signaturePart,
            marksheetPart,
            incomCertificate
        )

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    Log.d("uploadDocumentApiCall", "uploadDocumentApiCall: function ${response.body()}")
                    val jsonObject = response.body()
                    if (jsonObject?.get("status")?.asString.equals("success")) {
                        Toast.makeText(
                            this@DocumentManageActivity,
                            "${jsonObject?.get("message")?.asString}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            20
        )
    }

    private fun prepareFilePart(key: String, filePath: String) =
        MultipartBody.Part.createFormData(
            key,
            UriToFileConverter.convertUriToFile(
                this@DocumentManageActivity,
                Uri.parse(filePath)
            ).name,
            RequestBody.create(
                contentResolver.getType(Uri.parse(filePath))?.let { it.toMediaTypeOrNull() },
                UriToFileConverter.convertUriToFile(
                    this@DocumentManageActivity,
                    Uri.parse(filePath)
                )
            )
        )
}
