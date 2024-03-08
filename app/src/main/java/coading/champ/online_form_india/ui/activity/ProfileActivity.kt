package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.databinding.ActivityProfileBinding
import coading.champ.online_form_india.helper.PROFILE_IMG_LOC
import coading.champ.online_form_india.helper.SharedPrefManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        getUpdateProfileDetail()

    }

    private fun getUpdateProfileDetail() {
        Glide.with(this@ProfileActivity).load(PROFILE_IMG_LOC+SharedPrefManager.getInstance(this@ProfileActivity)?.user?.profile).into(binding.profileImage)
        Toast.makeText(this, "${SharedPrefManager.getInstance(this@ProfileActivity)?.user?.profile}", Toast.LENGTH_SHORT).show()
        binding.profileName.text = SharedPrefManager.getInstance(this@ProfileActivity)?.user?.username
    }

    lateinit var binding: ActivityProfileBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUpdateProfileDetail()

        binding.apToEditProfile.setOnClickListener{
            startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java))
        }

    }

}