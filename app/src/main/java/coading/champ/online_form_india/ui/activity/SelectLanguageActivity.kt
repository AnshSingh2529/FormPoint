package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.databinding.ActivitySelectLanguageBinding
import coading.champ.online_form_india.helper.LocaleHelper
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
            binding.englishLang.isChecked = true
            binding.hindiLang.isChecked = false
        } else if (value.equals("hi", ignoreCase = true)) {
            myStringValue = "hi"
            binding.hindiLang.isChecked = true
            binding.englishLang.isChecked = false
        } else {
            myStringValue = "en"
            binding.englishLang.isChecked = true
            binding.hindiLang.isChecked = false
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
            if(binding.language.checkedRadioButtonId > -1){
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
            }else{
                Toast.makeText(this, "Please Select Language", Toast.LENGTH_SHORT).show()
            }
        }


    }
}