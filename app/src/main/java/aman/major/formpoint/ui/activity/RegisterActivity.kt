package aman.major.formpoint.ui.activity

import aman.major.formpoint.databinding.ActivityRegisterBinding
import aman.major.formpoint.helper.Helper
import aman.major.formpoint.helper.RetrofitClient
import aman.major.formpoint.helper.SharedPrefManager
import aman.major.formpoint.helper.Validation
import aman.major.formpoint.modal.UserModal
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toLogin.setOnClickListener{
            finish()
        }

        binding.eyeCnfPass.setOnClickListener{
            Helper.manageEyeIcon(binding.eyeCnfPass, binding.regCnfPass)
        }

        binding.eyePass.setOnClickListener{
            Helper.manageEyeIcon(binding.eyePass, binding.regPass)
        }

        binding.createAccBtn.setOnClickListener {


            val email = binding.regEmail.text.toString()
            val mob = binding.regMob.text.toString()
            val name = binding.regName.text.toString()
            val pass = binding.regPass.text.toString()
           // val cnfPass = binding.regCnfPass.text.toString()

            if(
                !Validation.validateMobile(binding.regMob.text.toString(),binding.regMobErr) or
                !Validation.validateName(binding.regName.text.toString(),binding.regNameErr) or
                !Validation.validatePass(binding.regPass.text.toString(),binding.regPassErr)
                ){
                return@setOnClickListener
            }
            registerUser(email,mob,name,pass,pass)
        }


    }

    private fun registerUser(
        email: String,
        mob: String,
        name: String,
        pass: String,
        cnfPass: String
    ) {
        var pd = ProgressDialog(this)
        pd.setMessage("Please wait...")
        pd.setCancelable(false)
        pd.show()
        val call = RetrofitClient.getClient().registration(
            name,
            email,
            mob,
            pass,
            cnfPass
        )


        call.enqueue(object :retrofit2.Callback<JsonObject>{

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val response : JsonObject? = response.body();
                val status = response?.get("status")?.asString

                if (status.equals("success")){
                    val data = response?.get("data")?.asJsonObject
                    //SharedPrefManager.getInstance(this@RegisterActivity)?.userLogin(Gson().fromJson(data,UserModal::class.java))
                    finish()
                }
                Toast.makeText(this@RegisterActivity, "${response?.get("msg")?.asString} Login Please...", Toast.LENGTH_SHORT).show()
                pd.dismiss()
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })
    }
}