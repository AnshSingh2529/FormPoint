package aman.major.formpoint.ui.fragment
import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerVideoHomeAdapter
import aman.major.formpoint.databinding.FragmentHomeBinding
import aman.major.formpoint.ui.activity.DocumentManageActivity
import aman.major.formpoint.ui.activity.OnlineOpportunityActivity
import android.content.Intent
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
        
        binding.fhProfile.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, ProfileFragment())
                ?.addToBackStack("Profile")?.commit()
        }

        
        binding.fhDocManageVm.setOnClickListener{
            startActivity(Intent(context,DocumentManageActivity::class.java))
        }


        binding.fhGovernmentForm.setOnClickListener{
            startActivity(Intent(context,OnlineOpportunityActivity::class.java).putExtra("tabPosition",1))
        }
        binding.fhResult.setOnClickListener{
            startActivity(Intent(context,OnlineOpportunityActivity::class.java).putExtra("tabPosition",3))
        }

        binding.fhAdmissionForm.setOnClickListener{
            startActivity(Intent(context,OnlineOpportunityActivity::class.java).putExtra("tabPosition",2))
        }
        binding.fhAdmitCard.setOnClickListener{
            startActivity(Intent(context,OnlineOpportunityActivity::class.java).putExtra("tabPosition",0))
        }

        return binding.root

    }

}