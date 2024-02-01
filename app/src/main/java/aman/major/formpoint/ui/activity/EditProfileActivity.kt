package aman.major.formpoint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.epToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}