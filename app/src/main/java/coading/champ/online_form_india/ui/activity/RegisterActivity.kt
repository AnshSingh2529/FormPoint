package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.databinding.ActivityRegisterBinding
import coading.champ.online_form_india.helper.Helper
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.Validation
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

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