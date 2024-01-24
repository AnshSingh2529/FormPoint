package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerDocManageAdapter
import aman.major.formpoint.databinding.ActivityDocumentManageBinding
import aman.major.formpoint.databinding.ActivityDocumentUploadBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DocumentManageActivity : AppCompatActivity() {


    lateinit var binding: ActivityDocumentManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dmToolbar.setNavigationOnClickListener {
            finish()
        }

        val list = ArrayList<String>()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        binding.dmRecyclerView.adapter = RecyclerDocManageAdapter(this,list)

    }
}