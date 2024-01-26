package aman.major.formpoint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivitySelectLanguageBinding
import android.content.Intent
import android.view.View

class SelectLanguageActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent.getIntExtra("onLanguageActivity",0)

        binding.language.setOnCheckedChangeListener{radio,id->
           binding.selecButton.visibility = View.VISIBLE
        }

        binding.selecButton.setOnClickListener {
            if (i == 1){
                startActivity(Intent(this,LoginActivity::class.java))
            }else{
                finish()
            }
        }

    }
}