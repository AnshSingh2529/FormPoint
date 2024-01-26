package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.helper.SharedPrefManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


       val handler = Handler()
       handler.postDelayed({
           if(SharedPrefManager.getInstance(this@SplashActivity)?.isLoggedIn == true){
               startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
               finish()
           }else{
               startActivity(Intent(this@SplashActivity,SelectLanguageActivity::class.java).putExtra("onLanguageActivity",1))
               finish()
           }
       },1000)


    }
}