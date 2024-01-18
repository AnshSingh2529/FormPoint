package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityRegisterBinding
import aman.major.formpoint.helper.Helper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.eyeCnfPass.setOnClickListener{
            Helper.manageEyeIcon(binding.eyeCnfPass, binding.regCnfPass)
        }

        binding.eyePass.setOnClickListener{
            Helper.manageEyeIcon(binding.eyePass, binding.regPass)
        }


    }
}