package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityProfileBinding
import aman.major.formpoint.helper.SharedPrefManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileName.text = SharedPrefManager.getInstance(this@ProfileActivity)?.user?.username


    }

}