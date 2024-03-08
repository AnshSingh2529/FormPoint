package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.adapter.RecyclerFormOnlineAdapter
import coading.champ.online_form_india.databinding.ActivityFormOnlineBinding
import coading.champ.online_form_india.modal.FormOnlineModal
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
        list.add(FormOnlineModal(R.drawable.id_admit_card, resources.getString(R.string.admitCard)))
        list.add(FormOnlineModal(R.drawable.ic_govt_form, resources.getString(R.string.goverment_form)))
        list.add(FormOnlineModal(R.drawable.ic_form_online, resources.getString(R.string.addmission_form)))
        list.add(FormOnlineModal(R.drawable.ic_result, resources.getString(R.string.result)))
        binding.foRecyclerView.adapter = RecyclerFormOnlineAdapter(this, list)
    }
}