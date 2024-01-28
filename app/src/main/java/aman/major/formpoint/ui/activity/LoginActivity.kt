package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityLoginBinding
import aman.major.formpoint.helper.Helper
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.helper.Validation
import aman.major.formpoint.modal.UserModal
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            if (!Validation.validateMobile(binding.lgMob.text.toString(), binding.lgMobErr) or
                !Validation.validatePass(binding.lgPass.text.toString(), binding.lgPassErr)
            ) {
                return@setOnClickListener
            }

            loginUser(binding.lgMob.text.toString(), binding.lgPass.text.toString())
        }

        binding.lgPassEye.setOnClickListener {
            Helper.manageEyeIcon(binding.lgPassEye, binding.lgPass)
        }


    }

    private fun loginUser(mob: String, pass: String) {
        Log.d("loginUser", "loginUser: function Call: MobNo. ${mob} Password ${pass}")
        val pd = ProgressDialog(this)
        pd.setMessage("Please wait...")
        pd.setCancelable(false)
        pd.show()
        val call = RetrofitClient.getClient().login(
            mob,
            pass,
        )

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                   if(response.isSuccessful){
                       Log.d("loginUser", "loginUser onResponse: success "+response.body())

                       val response: JsonObject? = response.body()
                       val status = response?.get("status")?.asString
                       if (status.equals("success")) {
                           val data = response?.get("data")?.asJsonObject
                           SharedPrefManager.getInstance(this@LoginActivity)?.userLogin(
                               Gson().fromJson(
                                   data,
                                   UserModal::class.java
                               )
                           )
                           startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                           finish()
                       }
                       Toast.makeText(
                           this@LoginActivity,
                           "${response?.get("msg")?.asString}",
                           Toast.LENGTH_SHORT
                       ).show()
                       pd.dismiss()
                   }else{
                       Log.d("loginUser", "loginUser onResponse: not success "+response.errorBody()?.string())
                   }
                }catch (e:Exception){
                    Log.d("loginUser", "loginUser: Exception  ${e.localizedMessage}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("loginUser", "loginUser: onFailure failed ${t.localizedMessage}")
            }
        })
    }
}