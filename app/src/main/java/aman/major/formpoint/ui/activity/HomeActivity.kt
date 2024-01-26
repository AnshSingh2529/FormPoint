package aman.major.formpoint.ui.activity
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.databinding.ActivityHomeBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codebyashish.autoimageslider.Enums.ImageScaleType
import com.codebyashish.autoimageslider.Models.ImageSlidesModel

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        binding.ahVideoRecycler.adapter = RecyclerVideoHomeAdapter(list,this@HomeActivity)

        var imgList = ArrayList<ImageSlidesModel>()
        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/pmyoga3.jpg",ImageScaleType.FIT))
        imgList.add(ImageSlidesModel("https://i.pinimg.com/564x/5b/5c/fd/5b5cfde191a43c7c0084eb1de3828194.jpg",ImageScaleType.FIT))
        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/Train18.jpg",ImageScaleType.FIT))
        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/pmyoga3.jpg",ImageScaleType.FIT))
        imgList.add(ImageSlidesModel("https://i.pinimg.com/564x/5b/5c/fd/5b5cfde191a43c7c0084eb1de3828194.jpg",ImageScaleType.FIT))
        imgList.add(ImageSlidesModel("https://rdso.indianrailways.gov.in/template/site1/images/Train18.jpg",ImageScaleType.FIT))
        binding.autoImageSlider.setImageList(imgList)


        binding.languageChange.setOnClickListener {
            startActivity(Intent(this, SelectLanguageActivity::class.java).putExtra("onLanguageActivity",2))
        }



    }

}