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

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
        } else {
            askNotificationPermission()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



       val handler = Handler()
       handler.postDelayed({
           if(SharedPrefManager.getInstance(this@SplashActivity)?.isLoggedIn == true){

               val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
               val value = sharedPreferences.getString("my_string_key", "")
               if (value.equals("en", ignoreCase = true)) {
                   LocaleHelper.setLocale(this@SplashActivity, "en")
               } else if (value.equals("hi", ignoreCase = true)) {
                   LocaleHelper.setLocale(this@SplashActivity, "hi")
               } else {
                   LocaleHelper.setLocale(this@SplashActivity, "en")
               }
               startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
               finish()
           }else{
               startActivity(Intent(this@SplashActivity,SelectLanguageActivity::class.java).putExtra("onLanguageActivity",1))
               finish()
           }
       },1000)


    }
}