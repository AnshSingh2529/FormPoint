package aman.major.formpoint.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerFormOnlineAdapter
import aman.major.formpoint.databinding.ActivityFormOnlineBinding

class FormOnlineActivity : AppCompatActivity() {

    lateinit var binding: ActivityFormOnlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormOnlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.foToolbar.setNavigationOnClickListener {
            finish()
        }

        val list = ArrayList<String>()
        list.add("Admit Card")
        list.add("Government Form")
        list.add("Admission Form")
        list.add("Result")
        list.add("Job")
        binding.foRecyclerView.adapter = RecyclerFormOnlineAdapter(this,list)
    }
}