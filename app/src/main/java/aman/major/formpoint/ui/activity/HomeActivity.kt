package aman.major.formpoint.ui.activity
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.databinding.ActivityHomeBinding
import aman.major.formpoint.helper.RetrofitClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    var imgList = ArrayList<ImageSlidesModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getSliderImages()

        binding.ahProfile.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }


        binding.ahFormOnline.setOnClickListener{
            startActivity(Intent(this,FormOnlineActivity::class.java))
        }

        binding.ahDocUpload.setOnClickListener{
            startActivity(Intent(this,DocumentManageActivity::class.java))
        }


        
        binding.ahWatchVideo.setOnClickListener{
            startActivity(Intent(this,VideoActivity::class.java))
        }


        binding.ahNotification.setOnClickListener{
            startActivity(Intent(this,NotificationActivity::class.java))
        }

        var list = ArrayList<String>()

        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")

//        binding.ahVideoRecycler.adapter = RecyclerVideoHomeAdapter(list,this@HomeActivity)
//
//
//        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/pmyoga3.jpg",ImageScaleType.FIT))
//        imgList.add(ImageSlidesModel("https://i.pinimg.com/564x/5b/5c/fd/5b5cfde191a43c7c0084eb1de3828194.jpg",ImageScaleType.FIT))
//        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/Train18.jpg",ImageScaleType.FIT))
//        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/pmyoga3.jpg",ImageScaleType.FIT))
//        imgList.add(ImageSlidesModel("https://i.pinimg.com/564x/5b/5c/fd/5b5cfde191a43c7c0084eb1de3828194.jpg",ImageScaleType.FIT))
//        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/Train18.jpg",ImageScaleType.FIT))
//        binding.autoImageSlider.setImageList(imgList)


        binding.languageChange.setOnClickListener {
            startActivity(Intent(this, SelectLanguageActivity::class.java).putExtra("onLanguageActivity",2))
        }



    }

    private fun getSliderImages() {
        Log.d("getSliderImages", "getSliderImages: function call ")
        val call = RetrofitClient.getClient().slider()
        call.enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful){
                        Log.d("getSliderImages", "getSliderImages onResponse: function isSuccessful ")
                        val jsonObject  = response.body()
                        if(jsonObject?.get("status")?.asString.equals("success",true)){
                            val imgArray = jsonObject?.get("data")?.asJsonArray
                            Log.d("getSliderImages", "getSliderImages onResponse: function isSuccessful "+imgArray.toString())
                            imgList.clear()
                            imgArray?.let {
                                for (element in it) {
                                    val imageUrl = element.asString
                                    imgList.add(ImageSlidesModel(imageUrl,ImageScaleType.FIT))
                                }
                            }
                            Log.d("getSliderImages", "getSliderImages onResponse: function isSuccessful list size: "+imgArray?.size())
                            binding.autoImageSlider.setImageList(imgList)
                        }else{
                            Log.d("getSliderImages", "getSliderImages onResponse: function res is not Successful ${response.body().toString()}")
                        }
                    }else{
                        Log.d("getSliderImages", "getSliderImages onResponse: function isSuccessful ")
                    }
                }catch (e:Exception){
                    Log.d("getSliderImages", "getSliderImages onResponse: Exception "+e.localizedMessage)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getSliderImages", "getSliderImages onFailure: failed "+t.localizedMessage)
            }
        })

    }

}