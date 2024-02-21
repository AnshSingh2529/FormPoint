package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivitySelectLanguageBinding
import aman.major.formpoint.helper.LocaleHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SelectLanguageActivity : AppCompatActivity() {

    private var myStringValue: String? = null
    lateinit var binding: ActivitySelectLanguageBinding
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this@SelectLanguageActivity
        val i = intent.getIntExtra("onLanguageActivity", 0)

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val value = sharedPreferences.getString("my_string_key", "")

        if (value.equals("en", ignoreCase = true)) {
            myStringValue = "en"
            binding.englishLang.setChecked(true)
            binding.hindiLang.setChecked(false)
        } else if (value.equals("hi", ignoreCase = true)) {
            myStringValue = "hi"
            binding.hindiLang.setChecked(true)
            binding.englishLang.setChecked(false)
        } else {
            myStringValue = "en"
            binding.englishLang.setChecked(true)
            binding.hindiLang.setChecked(false)
        }

        binding.language.setOnCheckedChangeListener { radio, id ->
            binding.selecButton.visibility = View.VISIBLE

            if (id == R.id.hindiLang) {
                myStringValue = "hi"
            } else if (id == R.id.englishLang) {
                myStringValue = "en"
            }
           // Toast.makeText(this, "$myStringValue", Toast.LENGTH_SHORT).show()
        }

        binding.selecButton.setOnClickListener {

            LocaleHelper.setLocale(this@SelectLanguageActivity, myStringValue)
            val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("my_string_key", myStringValue)
            editor.commit()

            if (i == 1) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }


    }
}