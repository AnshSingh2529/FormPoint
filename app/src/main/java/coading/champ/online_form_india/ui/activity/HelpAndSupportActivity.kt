package coading.champ.online_form_india.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coading.champ.online_form_india.databinding.ActivityHelpAndSupportBinding
import android.content.Intent
import android.net.Uri

class HelpAndSupportActivity : AppCompatActivity() {

    lateinit var binding:ActivityHelpAndSupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityHelpAndSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailLayout.setOnClickListener{
            sendEmail(binding.email.text.toString())
        }
        binding.contactNumberLay.setOnClickListener{
            makePhoneCall(binding.contactNumber.text.toString())
        }


        binding.awToolbar.setNavigationOnClickListener { finish() }

    }

    private fun makePhoneCall(phoneNumber: String) {
        // Create the intent
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        // Verify if there is an app to handle the intent
        if (callIntent.resolveActivity(packageManager) != null) {
            startActivity(callIntent)
        } else {
            // Handle the case where there's no app installed to handle phone calls
            // or the device is unable to handle the intent
            // You can display an error message or take alternative action.
        }
    }

    private fun sendEmail(emailAddress: String) {
        // Create the intent
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
            // You can add subject and body as well if needed
            putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
            putExtra(Intent.EXTRA_TEXT, "Body of the email")
        }

        // Verify if there is an app to handle the intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            // Handle the case where there's no email app installed
            // or the device is unable to handle the intent
            // (e.g., no email account set up on the device)
            // You can display an error message or take alternative action.
        }
    }
}