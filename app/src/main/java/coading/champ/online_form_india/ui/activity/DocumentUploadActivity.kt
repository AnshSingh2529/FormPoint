package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.databinding.ActivityDocumentUploadBinding
import coading.champ.online_form_india.helper.BitmapToMultipart
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.SharedPrefManager
import coading.champ.online_form_india.helper.Validation
import coading.champ.online_form_india.modal.FormDataModal
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coading.champ.online_form_india.helper.Helper
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.skydoves.elasticviews.ElasticButton
import com.skydoves.elasticviews.ElasticCardView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DocumentUploadActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDocumentUploadBinding

    var aadharBitmap: Bitmap? = null
    var photoBitmap: Bitmap? = null
    var signUri: Bitmap? = null
    var eightMarksUri: Bitmap? = null
    var tentUri: Bitmap? = null
    var twelUri: Bitmap? = null
    var gradUri: Bitmap? = null
    var pGradUri: Bitmap? = null
    var incmUri: Bitmap? = null
    var residence: Bitmap? = null
    var cast: Bitmap? = null
    var panCardUri: Bitmap? = null
    var bankPass: Bitmap? = null
    var ewsUri: Bitmap? = null
    var ncc: Bitmap? = null
    var sportUri: Bitmap? = null
    var nss: Bitmap? = null
    var affeDevitUri: Bitmap? = null
    var otherUri: Bitmap? = null
    var screenShotBit: Bitmap? = null
    var formId: String? = null

    lateinit var btsImageView: ImageView
    lateinit var btsScreenshotAnim: LottieAnimationView


    private val REQUEST_IMAGE_CAPTURE = 366
    private val REQUEST_PICK_IMAGE = 466

    var flag: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        formId = intent.getStringExtra("formId")

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
            binding.twelMarksLay,
            binding.gradMarksLay,
            binding.pGradLay,
            binding.incmLay,
            binding.residenceLay,
            binding.castLay,
            binding.pancardLay,
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
            openAlertDialog(101)
        }

        binding.aadharUpload.setOnClickListener {
            openAlertDialog(102)
        }

        binding.uploadSign.setOnClickListener {
            openAlertDialog(103)
        }

        binding.uploadEightMarks.setOnClickListener {
            openAlertDialog(104)
        }
        binding.uploadtenth.setOnClickListener {
            openAlertDialog(105)
        }
        binding.uploadTwelth.setOnClickListener {
            openAlertDialog(106)
        }
        binding.uploadGrad.setOnClickListener {
            openAlertDialog(107)
        }
        binding.uploadpGrad.setOnClickListener {
            openAlertDialog(108)
        }
        binding.uploadIncm.setOnClickListener {
            openAlertDialog(109)
        }
        binding.uploadCast.setOnClickListener {
            openAlertDialog(110)
        }
        binding.uploadResidence.setOnClickListener {
            openAlertDialog(111)
        }
        binding.uploadPancard.setOnClickListener {
            openAlertDialog(112)
        }
        binding.uploadBankPass.setOnClickListener {
            openAlertDialog(113)
        }
        binding.uploadEws.setOnClickListener {
            openAlertDialog(114)
        }
        binding.uploadNcc.setOnClickListener {
            openAlertDialog(115)
        }
        binding.uploadSports.setOnClickListener {
            openAlertDialog(116)
        }
        binding.uploadNss.setOnClickListener {
            openAlertDialog(117)
        }
        binding.uploadAffidevit.setOnClickListener {
            openAlertDialog(118)
        }
        binding.uploadOther.setOnClickListener {
            openAlertDialog(119)
        }

        binding.duUploadButton.setOnClickListener {


           /* if (binding.userName.text.toString().isNotEmpty()
                and binding.userEmail.text.toString().isNotEmpty()
                and binding.userEmail.text.toString().isNotEmpty()
                and binding.userMobile.text.toString().isNotEmpty()
            //and binding.userTxnId.text.toString().isNotEmpty()
            ) {
                handlePaymentBottomSheet()
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }*/

            if(
                !Validation.validateName(binding.userName.text.toString(),binding.userNameErr) or
                !Validation.validateMandatoryEmail(binding.userEmail.text.toString(),binding.emailError) or
                !Validation.validateMobile(binding.userMobile.text.toString(),binding.mobileErr) or
                /*!Validation.validateAddress(binding.userAddress.text.toString(),binding.addressErr) or*/
                !Validation.validateTheImage(binding.addharLay,aadharBitmap,binding.aadharErr) or
                !Validation.validateTheImage(binding.photoLay,photoBitmap,binding.photoError) or
                !Validation.validateTheImage(binding.signaturLay,signUri,binding.signerr) or
                !Validation.validateTheImage(binding.eightMarksLay,eightMarksUri,binding.eightError) or
                !Validation.validateTheImage(binding.tenthMarksLay,tentUri,binding.tenthErr) or
                !Validation.validateTheImage(binding.twelMarksLay,twelUri,binding.twelerr) or
                !Validation.validateTheImage(binding.gradMarksLay,gradUri,binding.gradErr) or
                !Validation.validateTheImage(binding.pGradLay,pGradUri,binding.pGradErr) or
                !Validation.validateTheImage(binding.incmLay,incmUri,binding.incmErr) or
                !Validation.validateTheImage(binding.residenceLay,residence,binding.residenceerr) or
                !Validation.validateTheImage(binding.castLay,cast,binding.castErr) or
                !Validation.validateTheImage(binding.pancardLay,panCardUri,binding.panerr) or
                !Validation.validateTheImage(binding.bankPassbookLay,bankPass,binding.bankPasserr) or
                !Validation.validateTheImage(binding.ewsLay,ewsUri,binding.ewserr) or
                !Validation.validateTheImage(binding.ncclay,ncc,binding.nccerr) or
                !Validation.validateTheImage(binding.sportsCerfLay,sportUri,binding.sportserr) or
                !Validation.validateTheImage(binding.nssLay,nss,binding.nsserr) or
                !Validation.validateTheImage(binding.aafidevitLay,affeDevitUri,binding.affedevitErr) or
                !Validation.validateTheImage(binding.otherLay,otherUri,binding.othersErr)
                ){
                return@setOnClickListener
            }
            handlePaymentBottomSheet()

        }


    }

    private fun handlePaymentBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.payment_bottom_sheet, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.show()
        val qrCodeImages = bottomSheetDialog.findViewById<ImageView>(R.id.qrCodeImages)
        val extraCharge = bottomSheetDialog.findViewById<TextView>(R.id.extraCharge)
        val scCharge = bottomSheetDialog.findViewById<TextView>(R.id.scCharge)
        val generalCharge = bottomSheetDialog.findViewById<TextView>(R.id.generalCharge)
        val obcCharges = bottomSheetDialog.findViewById<TextView>(R.id.obcCharges)
        val userTxnId = bottomSheetDialog.findViewById<EditText>(R.id.userTxnId)
        val screenShotImg = bottomSheetDialog.findViewById<ImageView>(R.id.screenShotImg)
        val screenshotUploadBtn =
            bottomSheetDialog.findViewById<ElasticButton>(R.id.screenshotUploadBtn)
        val screenShotJson =
            bottomSheetDialog.findViewById<LottieAnimationView>(R.id.screenShotJson)
        val submitButton = bottomSheetDialog.findViewById<ElasticCardView>(R.id.submitButton)

        btsImageView = screenShotImg!!
        btsScreenshotAnim = screenShotJson!!


        getFormsPaymentData(
            qrCodeImages,
            extraCharge,
            scCharge,
            obcCharges,
            generalCharge
        )


        screenshotUploadBtn?.setOnClickListener {
            openAlertDialog(120)
        }




        submitButton?.setOnClickListener {
           /* if (userTxnId?.text.toString().isEmpty()) {
                Toast.makeText(this, "Fill the Txn Id", Toast.LENGTH_SHORT).show()
            } else if (userTxnId?.text.toString().length != 12) {
                Toast.makeText(this, "txn Id is of must be 12 digits", Toast.LENGTH_SHORT).show()
            }  else if (screenShotBit == null){
                Toast.makeText(this, "Upload Screenshot", Toast.LENGTH_SHORT).show()
            }
            else {


            }*/
            uploadFormInApi(
                SharedPrefManager.getInstance(this)?.user?.id.toString(),
                binding.userEmail.text.toString(),
                binding.userMobile.text.toString(),
                formId.toString(),
                binding.userName.text.toString(),
                binding.userAddress.text.toString(),
                userTxnId?.text.toString()
            )
            bottomSheetDialog.dismiss()

        }
    }

    private fun getFormsPaymentData(
        qrCodeImages: ImageView?,
        extraCharge: TextView?,
        scCharge: TextView?,
        obcCharges : TextView?,
        generalCharge: TextView?
    ) {
        Log.d("getFormDetails", "getFormDetails: function call: formId: $formId")
        val call = RetrofitClient.getClient()
            .getSingleFormData(
                formId.toString(),
                SharedPrefManager.getInstance(this@DocumentUploadActivity)?.user?.id.toString()
            );
        call.enqueue(object : Callback<JsonObject> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d("getFormDetails", "onResponse: response success: ${response.body()}")
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            val dataObj = dataArray?.get(0)?.asJsonObject
                            val modal = Gson().fromJson(dataObj, FormDataModal::class.java)

                            extraCharge?.text = "₹" + modal.extra_charges
                            generalCharge?.text = "₹" + modal.charge_general
                            scCharge?.text = "₹" + modal.charge_sc_st
                            obcCharges?.text = "₹" + modal.charge_obc


                            Glide.with(this@DocumentUploadActivity)
                                .load(jsonObject?.get("qr_code_path")?.asString).into(
                                    qrCodeImages!!
                                )

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

    private fun uploadFormInApi(
        userId: String,
        email: String,
        phone: String,
        formId: String,
        userName: String,
        address: String,
        txnId: String
    ) {
        val progressDialog = Helper.customProgressDialog(this)
        progressDialog.show()
        Log.d(
            "uploadFormInApi",
            "uploadFormInApi: function call: userId: $userId formId: $formId phone $phone"
        )
        Log.d("uploadFormInApi", "photo -> $photoBitmap")

        val userIdRequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val formIdRequestBody = formId.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneRequestBody = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val useNameRequestBody = userName.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressRequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val txnIdRequestBody = txnId.toRequestBody("text/plain".toMediaTypeOrNull())
        val photoPart =
            BitmapToMultipart.bitmapToMultipart("Photo", photoBitmap, this@DocumentUploadActivity)
        val aadharPart =
            BitmapToMultipart.bitmapToMultipart("Aadhar", aadharBitmap, this@DocumentUploadActivity)
        val signaturePart =
            BitmapToMultipart.bitmapToMultipart("Signature", signUri, this@DocumentUploadActivity)
        val eightMarksPart = BitmapToMultipart.bitmapToMultipart(
            "8th-Marksheet",
            eightMarksUri,
            this@DocumentUploadActivity
        )
        val tenthMarksPart = BitmapToMultipart.bitmapToMultipart(
            "10th-Marksheet",
            tentUri,
            this@DocumentUploadActivity
        )
        val twelthMarksPart = BitmapToMultipart.bitmapToMultipart(
            "12th-Marksheet",
            twelUri,
            this@DocumentUploadActivity
        )
        val gradMarksPart = BitmapToMultipart.bitmapToMultipart(
            "Graduation-Marksheet",
            gradUri,
            this@DocumentUploadActivity
        )
        val postgradMarksPart = BitmapToMultipart.bitmapToMultipart(
            "Post-Graduation",
            pGradUri,
            this@DocumentUploadActivity
        )
        val incmPart = BitmapToMultipart.bitmapToMultipart(
            "Income-Certificate",
            incmUri,
            this@DocumentUploadActivity
        )
        val residencePart = BitmapToMultipart.bitmapToMultipart(
            "Residence-Certificate",
            residence,
            this@DocumentUploadActivity
        )
        val castPart = BitmapToMultipart.bitmapToMultipart(
            "Caste-Certificate",
            cast,
            this@DocumentUploadActivity
        )
        val pancardPart =
            BitmapToMultipart.bitmapToMultipart("Pancard", panCardUri, this@DocumentUploadActivity)
        val bankPart = BitmapToMultipart.bitmapToMultipart(
            "Bank-Passbook",
            bankPass,
            this@DocumentUploadActivity
        )
        val ewsPart =
            BitmapToMultipart.bitmapToMultipart("EWS", bankPass, this@DocumentUploadActivity)
        val nccPart =
            BitmapToMultipart.bitmapToMultipart("NCC-Certificate", ncc, this@DocumentUploadActivity)
        val sportsPart = BitmapToMultipart.bitmapToMultipart(
            "Sports-Certificate",
            sportUri,
            this@DocumentUploadActivity
        )
        val nssPart =
            BitmapToMultipart.bitmapToMultipart("NSS-Certificate", nss, this@DocumentUploadActivity)
        val affedivitPart = BitmapToMultipart.bitmapToMultipart(
            "Affidavit",
            affeDevitUri,
            this@DocumentUploadActivity
        )
        val otherPart =
            BitmapToMultipart.bitmapToMultipart("Other", otherUri, this@DocumentUploadActivity)

        val screenshotPart = BitmapToMultipart.bitmapToMultipart("Screenshot",screenShotBit,this@DocumentUploadActivity)


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
            otherPart,
            screenshotPart
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
                            handleSuccessBottomSheet()
                        }
                        Toast.makeText(
                            this@DocumentUploadActivity,
                            "${responseBody?.get("message")?.asString}",
                            Toast.LENGTH_SHORT
                        ).show()
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

    private fun openAlertDialog(requestCode: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Select Action")
        builder.setPositiveButton(
            "Take Photo",
            DialogInterface.OnClickListener { dialog, id ->
                flag = requestCode
                dispatchTakePictureIntent()
            })
        builder.setNegativeButton("Choose from Gallery",
            DialogInterface.OnClickListener { dialog, id -> // User clicked Choose from Gallery
                flag = requestCode
                dispatchPickImageIntent()
            })

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun dispatchPickImageIntent() {
        if (checkPermission() && checkCameraPermission()) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_PICK_IMAGE)
        } else {
            requestPermission()
        }
    }

    private fun dispatchTakePictureIntent() {
        if (checkPermission() && checkCameraPermission()) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
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

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.CAMERA
                ),
                20
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                ),
                20
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            val imageUri = data?.data!!
            val galleryBitmap =
                MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri)
            if (galleryBitmap != null) {
                Log.d("gettingPhotoUri", "onActivityResult: photoUri")
                when (flag) {
                    101 -> {
                        binding.photoImg.setImageBitmap(galleryBitmap)
                        setInvisibleLottie(binding.photoJson)
                        photoBitmap = galleryBitmap
                    }

                    102 -> {
                        binding.aadharImg.setImageBitmap(galleryBitmap)
                        aadharBitmap = galleryBitmap
                        setInvisibleLottie(binding.aadharJson)
                    }

                    103 -> {
                        binding.signaturImg.setImageBitmap(galleryBitmap)
                        setInvisibleLottie(binding.signatureJson)
                        signUri = galleryBitmap
                    }

                    104 -> {
                        binding.eightMarkImg.setImageBitmap(galleryBitmap)
                        eightMarksUri = galleryBitmap
                        setInvisibleLottie(binding.eightMarkJson)
                    }

                    105 -> {
                        binding.tenthMarksImg.setImageBitmap(galleryBitmap)
                        setInvisibleLottie(binding.tenthJson)
                        tentUri = galleryBitmap
                    }

                    106 -> {
                        binding.twelMarksImg.setImageBitmap(galleryBitmap)
                        twelUri = galleryBitmap
                        setInvisibleLottie(binding.twelthMarksJson)
                    }

                    107 -> {
                        binding.gradImg.setImageBitmap(galleryBitmap)
                        gradUri = galleryBitmap
                        setInvisibleLottie(binding.gradMarksJson)
                    }

                    108 -> {
                        binding.pGradImg.setImageBitmap(galleryBitmap)
                        pGradUri = galleryBitmap
                        setInvisibleLottie(binding.pGradJson)
                    }

                    109 -> {
                        binding.incmImg.setImageBitmap(galleryBitmap)
                        incmUri = galleryBitmap
                        setInvisibleLottie(binding.incmJson)
                    }

                    110 -> {
                        binding.castImg.setImageBitmap(galleryBitmap)
                        cast = galleryBitmap
                        setInvisibleLottie(binding.castJson)
                    }

                    111 -> {
                        binding.residenceImg.setImageBitmap(galleryBitmap)
                        residence = galleryBitmap
                        setInvisibleLottie(binding.residenceJson)
                    }

                    112 -> {
                        binding.panCardImg.setImageBitmap(galleryBitmap)
                        panCardUri = galleryBitmap
                        setInvisibleLottie(binding.pancardJson)
                    }

                    113 -> {
                        binding.bankPassbookImg.setImageBitmap(galleryBitmap)
                        bankPass = galleryBitmap
                        setInvisibleLottie(binding.bankPassBookJson)
                    }


                    114 -> {
                        binding.ewsImg.setImageBitmap(galleryBitmap)
                        ewsUri = galleryBitmap
                        setInvisibleLottie(binding.ewsJson)
                    }

                    115 -> {
                        binding.nccImg.setImageBitmap(galleryBitmap)
                        ncc = galleryBitmap
                        setInvisibleLottie(binding.nccjson)
                    }

                    116 -> {
                        binding.sportsCerfImg.setImageBitmap(galleryBitmap)
                        sportUri = galleryBitmap
                        setInvisibleLottie(binding.sportsCerfJson)
                    }

                    117 -> {
                        binding.nssImg.setImageBitmap(galleryBitmap)
                        nss = galleryBitmap
                        setInvisibleLottie(binding.nssJson)
                    }

                    118 -> {
                        binding.affedivitImg.setImageBitmap(galleryBitmap)
                        affeDevitUri = galleryBitmap
                        setInvisibleLottie(binding.affedivitJson)
                    }

                    119 -> {
                        binding.otherIMg.setImageBitmap(galleryBitmap)
                        otherUri = galleryBitmap
                        setInvisibleLottie(binding.otherJson)
                    }

                    120 -> {
                        btsImageView?.setImageBitmap(galleryBitmap)
                        screenShotBit = galleryBitmap
                        setInvisibleLottie(btsScreenshotAnim)
                    }
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val cameraBitmap = data?.extras?.get("data") as Bitmap
            if (cameraBitmap != null) {
                Log.d("gettingPhotoUri", "onActivityResult: photoUri")
                when (flag) {
                    101 -> {
                        binding.photoImg.setImageBitmap(cameraBitmap)
                        setInvisibleLottie(binding.photoJson)
                        photoBitmap = cameraBitmap
                    }

                    102 -> {
                        binding.aadharImg.setImageBitmap(cameraBitmap)
                        aadharBitmap = cameraBitmap
                        setInvisibleLottie(binding.aadharJson)
                    }

                    103 -> {
                        binding.signaturImg.setImageBitmap(cameraBitmap)
                        setInvisibleLottie(binding.signatureJson)
                        signUri = cameraBitmap
                    }

                    104 -> {
                        binding.eightMarkImg.setImageBitmap(cameraBitmap)
                        eightMarksUri = cameraBitmap
                        setInvisibleLottie(binding.eightMarkJson)
                    }

                    105 -> {
                        binding.tenthMarksImg.setImageBitmap(cameraBitmap)
                        setInvisibleLottie(binding.tenthJson)
                        tentUri = cameraBitmap
                    }

                    106 -> {
                        binding.twelMarksImg.setImageBitmap(cameraBitmap)
                        twelUri = cameraBitmap
                        setInvisibleLottie(binding.twelthMarksJson)
                    }

                    107 -> {
                        binding.gradImg.setImageBitmap(cameraBitmap)
                        gradUri = cameraBitmap
                        setInvisibleLottie(binding.gradMarksJson)
                    }

                    108 -> {
                        binding.pGradImg.setImageBitmap(cameraBitmap)
                        pGradUri = cameraBitmap
                        setInvisibleLottie(binding.pGradJson)
                    }

                    109 -> {
                        binding.incmImg.setImageBitmap(cameraBitmap)
                        incmUri = cameraBitmap
                        setInvisibleLottie(binding.incmJson)
                    }

                    110 -> {
                        binding.castImg.setImageBitmap(cameraBitmap)
                        cast = cameraBitmap
                        setInvisibleLottie(binding.castJson)
                    }

                    111 -> {
                        binding.residenceImg.setImageBitmap(cameraBitmap)
                        residence = cameraBitmap
                        setInvisibleLottie(binding.residenceJson)
                    }

                    112 -> {
                        binding.panCardImg.setImageBitmap(cameraBitmap)
                        panCardUri = cameraBitmap
                        setInvisibleLottie(binding.pancardJson)
                    }

                    113 -> {
                        binding.bankPassbookImg.setImageBitmap(cameraBitmap)
                        bankPass = cameraBitmap
                        setInvisibleLottie(binding.bankPassBookJson)
                    }


                    114 -> {
                        binding.ewsImg.setImageBitmap(cameraBitmap)
                        ewsUri = cameraBitmap
                        setInvisibleLottie(binding.ewsJson)
                    }

                    115 -> {
                        binding.nccImg.setImageBitmap(cameraBitmap)
                        ncc = cameraBitmap
                        setInvisibleLottie(binding.nccjson)
                    }

                    116 -> {
                        binding.sportsCerfImg.setImageBitmap(cameraBitmap)
                        sportUri = cameraBitmap
                        setInvisibleLottie(binding.sportsCerfJson)
                    }

                    117 -> {
                        binding.nssImg.setImageBitmap(cameraBitmap)
                        nss = cameraBitmap
                        setInvisibleLottie(binding.nssJson)
                    }

                    118 -> {
                        binding.affedivitImg.setImageBitmap(cameraBitmap)
                        affeDevitUri = cameraBitmap
                        setInvisibleLottie(binding.affedivitJson)
                    }

                    119 -> {
                        binding.otherIMg.setImageBitmap(cameraBitmap)
                        otherUri = cameraBitmap
                        setInvisibleLottie(binding.otherJson)
                    }

                    120 -> {
                        btsImageView.setImageBitmap(cameraBitmap)
                        screenShotBit = cameraBitmap
                        setInvisibleLottie(btsScreenshotAnim)
                    }
                }
            }
        }
    }

    private fun setInvisibleLottie(photoJson: LottieAnimationView) {
        photoJson.visibility = View.INVISIBLE
    }

    /*private fun prepareFilePart(key: String, filePath: Bitmap?): MultipartBody.Part {
        try {
            Log.d("prepareFilePart", "prepareFilePart: key: $key uri: $filePath")

            //val file = UriToFileConverter.convertUriToFile2(this@DocumentUploadActivity, Uri.parse(filePath))
            val file = Helper.saveBitmapToFile(filePath)
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file!!)


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
        }catch (e:Exception){

        }
    }*/

}
     */
}