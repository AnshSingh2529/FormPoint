package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.databinding.ActivityEditProfileBinding
import coading.champ.online_form_india.helper.PROFILE_IMG_LOC
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.SharedPrefManager
import coading.champ.online_form_india.helper.UriToFileConverter
import coading.champ.online_form_india.helper.Validation
import coading.champ.online_form_india.modal.UserModal
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coading.champ.online_form_india.helper.Helper
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding

    var imgUri: Uri = Uri.parse("")

    private val PICK_IMAGE_REQUEST = 1
    private val STORAGE_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.epToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.epName.setText(SharedPrefManager.getInstance(this)?.user?.username)
        binding.epMob.setText(SharedPrefManager.getInstance(this)?.user?.mobile)
        binding.epEmail.setText(SharedPrefManager.getInstance(this)?.user?.email)

        val profileImage =
            SharedPrefManager.getInstance(this@EditProfileActivity)?.user?.profile.toString()

        if (!profileImage.equals("null",true)) {
            Log.d("profileImage", "onCreate: proflie_emg: inside if")
            binding.epProfileImg.visibility = View.VISIBLE
            binding.epUpdateImg.visibility = View.INVISIBLE
            Glide.with(this@EditProfileActivity).load(PROFILE_IMG_LOC + profileImage)
                .into(binding.epProfileImg)
            Log.d("profileImage", "onCreate: status: ${profileImage.isNullOrEmpty()}")
        }else{
            binding.epProfileImg.visibility = View.VISIBLE
            binding.epUpdateImg.visibility = View.INVISIBLE
            binding.epProfileImg.setImageDrawable(resources.getDrawable(R.drawable.profile_default))
        }

        binding.epChooseImg.setOnClickListener {
            if (checkPermission()) {
                openImagePicker()
            } else {
                requestPermission()
            }
        }

        binding.epToolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener,
            androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {

                val id = item?.itemId

                when (id) {
                    R.id.logOutBtn -> {
                        openAlertDialog()
                    }

                    R.id.privacyPolicy -> {
                        startActivity(
                            Intent(
                                this@EditProfileActivity,
                                WebViewActivity::class.java
                            ).putExtra("flag", 1)
                        )
                    }

                    R.id.termsCondition -> {
                        startActivity(
                            Intent(
                                this@EditProfileActivity,
                                WebViewActivity::class.java
                            ).putExtra("flag", 2)
                        )
                    }

                    R.id.helpAndSupport -> {
                        startActivity(
                            Intent(
                                this@EditProfileActivity,
                                HelpAndSupportActivity::class.java
                            )
                        )
                    }
                }

                return true
            }
        })


        binding.epUpdateProfileBtn.setOnClickListener {
            if (imgUri.toString().isNotEmpty()) {
                if (!Validation.validateEmail(
                        binding.epEmail.text.toString(),
                        binding.epEmailErr
                    ) or
                    !Validation.validateName(binding.epName.text.toString(), binding.epNameErr)
                ) {
                    return@setOnClickListener
                }

                val file = UriToFileConverter.convertUriToFile(this@EditProfileActivity, imgUri)
                Log.d(
                    "updateProfile",
                    "updateProfile: button clicked imgUri: $imgUri"
                )
                updateProfile(binding.epName.text.toString(), binding.epEmail.text.toString(), file)
            } else {
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun openAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert!")
        alertDialog.setMessage("Are you sure to Log Out!")

        alertDialog.setPositiveButton("Yes") { dialog, which ->
            SharedPrefManager.getInstance(this@EditProfileActivity)?.logout()
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            dialog.dismiss()
        }

        alertDialog.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = alertDialog.create()
        dialog.show()


    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imgUri = data?.data!!
            binding.epProfileImg.visibility = View.INVISIBLE
            binding.epUpdateImg.visibility = View.VISIBLE
            binding.epUpdateImg.setImageURI(imgUri)
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            }
        }
    }

    private fun updateProfile(epName: String, epEmail: String, file: File?) {
        val dialog = Helper.customProgressDialog(this@EditProfileActivity)
        dialog.show();
        Log.d(
            "updateProfile",
            "updateProfile: file status: ${file?.exists()} file name ${file?.name}"
        )
        if (file != null && file.exists()) {
            val requestFile = RequestBody.create(
                contentResolver.getType(imgUri)?.let { it.toMediaTypeOrNull() },
                file
            )
            val profilePart = MultipartBody.Part.createFormData("profile", file.name, requestFile)

            val name = epName.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = epEmail.toRequestBody("text/plain".toMediaTypeOrNull())
            val user_id = SharedPrefManager.getInstance(this)?.user?.id.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d(
                "updateProfile",
                "content type ${requestFile.contentType()} request file ${requestFile.contentLength()} duplex ${requestFile.isDuplex()} "
            )
            val call = RetrofitClient.getClient().updateProfile(name, profilePart, email, user_id)
            call.enqueue(object : retrofit2.Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Log.d("updateProfile", "onResponse: " + response.body())
                        val jsonObject = response.body()
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val dataObject = jsonObject?.get("data")?.asJsonObject
                            val userModal = Gson().fromJson(dataObject, UserModal::class.java)
                            SharedPrefManager.getInstance(this@EditProfileActivity)
                                ?.userLogin(userModal)
                            finish()
                        }
                        Toast.makeText(
                            this@EditProfileActivity,
                            "${jsonObject?.get("message")?.asString}",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    } else {
                        Log.e("updateProfile", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("updateProfile", "Request failed: ${t.localizedMessage}")
                }
            })
        } else {
            Log.e(
                "updateProfile",
                "File is null or does not exist. Cannot proceed with the request."
            )
        }

    }


}