package aman.major.formpoint.ui.fragment
import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.databinding.FragmentHomeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        

        var list :ArrayList<String> = ArrayList()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        binding.fhVideoRecycler.adapter = RecyclerVideoHomeAdapter(list,context)
        return binding.root

    }

}