package aman.major.formpoint.adapter

import aman.major.formpoint.ui.fragment.HomeFragment
import aman.major.formpoint.ui.fragment.ProfileFragment
import aman.major.formpoint.ui.fragment.SearchFragment
import aman.major.formpoint.ui.fragment.VideoFragment
import aman.major.formpoint.ui.fragment.WalletFragment
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
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> VideoFragment()
            3 -> WalletFragment()
            4 -> ProfileFragment()
            else -> {
                Fragment()
            }
        }

    }
}