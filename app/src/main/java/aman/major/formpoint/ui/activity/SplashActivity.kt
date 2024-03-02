package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.helper.LocaleHelper
import aman.major.formpoint.helper.SharedPrefManager
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({
            if (SharedPrefManager.getInstance(this@SplashActivity)?.isLoggedIn == true) {

                val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
                val value = sharedPreferences.getString("my_string_key", "")
                if (value.equals("en", ignoreCase = true)) {
                    LocaleHelper.setLocale(this@SplashActivity, "en")
                } else if (value.equals("hi", ignoreCase = true)) {
                    LocaleHelper.setLocale(this@SplashActivity, "hi")
                } else {
                    LocaleHelper.setLocale(this@SplashActivity, "en")
                }
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            } else {
                startActivity(
                    Intent(
                        this@SplashActivity,
                        SelectLanguageActivity::class.java
                    ).putExtra("onLanguageActivity", 1)
                )
                finish()
            }
        }, 1000)


    }
}