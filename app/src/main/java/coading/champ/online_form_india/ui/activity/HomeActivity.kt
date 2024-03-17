package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.adapter.RecyclerFormOnlineAdapter
import coading.champ.online_form_india.databinding.ActivityHomeBinding
import coading.champ.online_form_india.helper.Helper
import coading.champ.online_form_india.helper.LocaleHelper
import coading.champ.online_form_india.helper.PROFILE_IMG_LOC
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.SharedPrefManager
import coading.champ.online_form_india.modal.FormOnlineModal
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coading.champ.online_form_india.adapter.RecyclerVideoHomeAdapter
import coading.champ.online_form_india.modal.VideoModal
import com.bumptech.glide.Glide
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    var imgList = ArrayList<ImageSlidesModel>()

    override fun onResume() {
        super.onResume()
        setProfileImages()
    }

    private fun setProfileImages() {
        Glide.with(this@HomeActivity)
            .load(PROFILE_IMG_LOC + SharedPrefManager.getInstance(this@HomeActivity)?.user?.profile)
            .placeholder(
                R.drawable.profile_default
            ).into(binding.ahProfile)
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (!isGranted) {
            askNotificationPermission()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val value = sharedPreferences.getString("my_string_key", "")

        setHomeRecycler()
        getTokenWhileLogin()
        askNotificationPermission()

        if (value.equals("en", ignoreCase = true)) {
            LocaleHelper.setLocale(this@HomeActivity, "en")
        } else if (value.equals("hi", ignoreCase = true)) {
            LocaleHelper.setLocale(this@HomeActivity, "hi")
        } else {
            LocaleHelper.setLocale(this@HomeActivity, "en")
        }

        getSliderImages()


        getVideoList(binding.ahVideoRecycler, this)

        binding.searchBar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java).putExtra("searchKey", 0))
        }
        binding.ahMic.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java).putExtra("searchKey", 1))
        }


        binding.ahProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }


        binding.ahVideoViewMore.setOnClickListener {
            startActivity(Intent(this, VideoPlayActivity::class.java))
        }

        binding.ahFormOnline.setOnClickListener {
            startActivity(Intent(this, FormOnlineActivity::class.java))
        }

        binding.ahDocUpload.setOnClickListener {
            startActivity(Intent(this, DocumentManageActivity::class.java))
        }

        binding.ahApplicationStatus.setOnClickListener {
            startActivity(
                Intent(this, OnlineOpportunityActivity::class.java)
                    .putExtra("tabPosition", 4)
            )
        }

        binding.ahOtpMenual.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    OnlineOpportunityActivity::class.java
                ).putExtra("tabPosition", 5)
            )
        }

        binding.ahWatchVideo.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }


        binding.ahNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }


        binding.languageChange.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SelectLanguageActivity::class.java
                ).putExtra("onLanguageActivity", 2)
            )
            finish()
        }

    }

    private fun setHomeRecycler() {
        val list = ArrayList<FormOnlineModal>()
        list.add(
            FormOnlineModal(
                R.drawable.ic_govt_form,
                resources.getString(R.string.goverment_form)
            )
        )
        list.add(
            FormOnlineModal(
                R.drawable.ic_form_online,
                resources.getString(R.string.addmission_form)
            )
        )
        list.add(FormOnlineModal(R.drawable.id_admit_card, resources.getString(R.string.admitCard)))
        list.add(FormOnlineModal(R.drawable.ic_result, resources.getString(R.string.result)))
        list.add(
            FormOnlineModal(
                R.drawable.ic_application_status,
                resources.getString(R.string.application_status)
            )
        )
        list.add(
            FormOnlineModal(
                R.drawable.ic_doc_upload,
                resources.getString(R.string.document_upload)
            )
        )
        list.add(
            FormOnlineModal(
                R.drawable.ic_otp_menual,
                resources.getString(R.string.otp_menual)
            )
        )
        list.add(
            FormOnlineModal(
                R.drawable.ic_watch_video,
                resources.getString(R.string.watch_video)
            )
        )
        binding.ahHomeRecycler.adapter = RecyclerFormOnlineAdapter(this, list)
    }


    private fun getSliderImages() {
        Log.d("getSliderImages", "getSliderImages: function call ")
        val call = RetrofitClient.getClient().slider()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d(
                            "getSliderImages",
                            "getSliderImages onResponse: function isSuccessful "
                        )
                        val jsonObject = response.body()
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val imgArray = jsonObject?.get("data")?.asJsonArray
                            Log.d(
                                "getSliderImages",
                                "getSliderImages onResponse: function isSuccessful " + imgArray.toString()
                            )
                            imgList.clear()
                            imgArray?.let {
                                for (element in it) {
                                    val imageUrl = element.asString
                                    imgList.add(
                                        ImageSlidesModel(
                                            imageUrl,
                                            ImageScaleType.CENTER_CROP
                                        )
                                    )
                                }
                            }
                            Log.d(
                                "getSliderImages",
                                "getSliderImages onResponse: function isSuccessful list size: " + imgArray?.size()
                            )
                            binding.autoImageSlider.setImageList(imgList)
                        } else {
                            Log.d(
                                "getSliderImages",
                                "getSliderImages onResponse: function res is not Successful ${
                                    response.body().toString()
                                }"
                            )
                        }
                    } else {
                        Log.d(
                            "getSliderImages",
                            "getSliderImages onResponse: function isSuccessful "
                        )
                    }
                } catch (e: Exception) {
                    Log.d(
                        "getSliderImages",
                        "getSliderImages onResponse: Exception " + e.localizedMessage
                    )
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getSliderImages", "getSliderImages onFailure: failed " + t.localizedMessage)
            }
        })


    }

    fun getVideoList(recyclerView: RecyclerView, context: Context) {
        val videoModalList = ArrayList<VideoModal>()
        val call = RetrofitClient.getClient().video();
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                try {
                    if (response.isSuccessful) {
                        val jsonObject = response.body();
                        val path = jsonObject?.get("path")?.asString
                        if (jsonObject?.get("status")?.asString.equals("success", false)) {
                            binding.videoHeading.visibility = View.GONE
                            binding.ahVideoViewMore.visibility = View.GONE
                            val data = jsonObject?.get("data")?.asJsonArray
                            for (i in 0 until data!!.size()) {
                                val dataObj = data.get(i).asJsonObject
                                val videoModal =
                                    Gson().fromJson(dataObj, VideoModal::class.java)
                                videoModalList.add(videoModal)
                            }
                            recyclerView.adapter = RecyclerVideoHomeAdapter(
                                videoModalList,
                                context,
                                path.toString()
                            )
                        }
                    }
                } catch (e: Exception) {
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })

    }

    private fun getTokenWhileLogin(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(
                    "getTokenWhileLogin",
                    "Fetching FCM registration token failed",
                    task.exception
                )
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.d("getTokenWhileLogin", "getTokenWhileLogin: token: $token")
            /* // Log and toast
             val msg = getString(R.string.msg_token_fmt, token)
             Log.d("TAG", msg)*/
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

}