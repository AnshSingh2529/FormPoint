package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityLoginBinding
import aman.major.formpoint.helper.Helper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toCreateAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.lgPassEye.setOnClickListener{
            Helper.manageEyeIcon(binding.lgPassEye, binding.lgPass)
        }


    }
}