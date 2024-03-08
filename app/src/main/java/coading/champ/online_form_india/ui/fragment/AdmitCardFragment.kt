package coading.champ.online_form_india.ui.fragment

import coading.champ.online_form_india.databinding.FragmentAdmitCardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AdmitCardFragment : Fragment() {

    private lateinit var binding: FragmentAdmitCardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdmitCardBinding.inflate(inflater, container, false)


        return binding.root
    }
}