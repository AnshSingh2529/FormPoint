package aman.major.formpoint.ui.activity
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.databinding.ActivityHomeBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
        
        
    }

}