package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.databinding.ActivityDocumentUploadBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DocumentUploadActivity : AppCompatActivity() {


    private lateinit var binding : ActivityDocumentUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.duToolbar.setNavigationOnClickListener {
            finish()
        }

    }
}