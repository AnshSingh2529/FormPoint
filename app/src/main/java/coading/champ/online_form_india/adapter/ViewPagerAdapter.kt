package coading.champ.online_form_india.adapter

import coading.champ.online_form_india.ui.fragment.AdmissionFormFragment
import coading.champ.online_form_india.ui.fragment.AdmitCardFragment
import coading.champ.online_form_india.ui.fragment.GovernmentFormFragment
import coading.champ.online_form_india.ui.fragment.JobFragment
import coading.champ.online_form_india.ui.fragment.ResultFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AdmitCardFragment()
            1 -> GovernmentFormFragment()
            2 -> AdmissionFormFragment()
            3 -> ResultFragment()
            4 -> JobFragment()
            else -> {
                Fragment()
            }
        }

    }
}