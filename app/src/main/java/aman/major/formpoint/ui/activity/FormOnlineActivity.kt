package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerFormOnlineAdapter
import aman.major.formpoint.databinding.ActivityFormOnlineBinding
import aman.major.formpoint.modal.FormOnlineModal
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FormOnlineActivity : AppCompatActivity() {

    lateinit var binding: ActivityFormOnlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormOnlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.foToolbar.setNavigationOnClickListener {
            finish()
        }

        val list = ArrayList<FormOnlineModal>()
        list.add(FormOnlineModal(R.drawable.id_admit_card, "Admit Card"))
        list.add(FormOnlineModal(R.drawable.ic_govt_form, "Government Form"))
        list.add(FormOnlineModal(R.drawable.ic_form_online, "Admission Form"))
        list.add(FormOnlineModal(R.drawable.ic_result, "Result"))
        binding.foRecyclerView.adapter = RecyclerFormOnlineAdapter(this, list)
    }
}