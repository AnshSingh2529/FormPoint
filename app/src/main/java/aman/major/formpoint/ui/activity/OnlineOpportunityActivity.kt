package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerFormAdapter
import aman.major.formpoint.adapter.ViewPagerAdapter
import aman.major.formpoint.databinding.ActivityDocumentManageBinding
import aman.major.formpoint.databinding.ActivityOnlineOpportunityBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class OnlineOpportunityActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityOnlineOpportunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineOpportunityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setUpTabLayoutWithViewPager()

        when(intent.getIntExtra("tabPosition",0)){
            0 -> {
                binding.ooToolbar.title = "Admit Card"
            }
            1 -> {
                binding.ooToolbar.title = "Government Form"
            }
            2 -> {
                binding.ooToolbar.title = "Admission Form"
            }
            3 -> {
                binding.ooToolbar.title = "Result"
            }
            4 -> {
                binding.ooToolbar.title = "Job"
            }

        }

        var list = ArrayList<String>()

        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        binding.ooRecycler.adapter = RecyclerFormAdapter(list)

    }

//    private fun setUpTabLayoutWithViewPager() {
//
//        binding.ooTabLayout.addTab(binding.ooTabLayout.newTab().setText("Admit Card"))
//        binding.ooTabLayout.addTab(binding.ooTabLayout.newTab().setText("Government Form"))
//        binding.ooTabLayout.addTab(binding.ooTabLayout.newTab().setText("Admission Form"))
//        binding.ooTabLayout.addTab(binding.ooTabLayout.newTab().setText("Result"))
//        binding.ooTabLayout.addTab(binding.ooTabLayout.newTab().setText("Job"))
//
//        binding.ooViewpager2.adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
//
//        binding.ooTabLayout.setOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                binding.ooViewpager2.currentItem = tab!!.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })
//
//        binding.ooViewpager2.registerOnPageChangeCallback(object :
//            ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                binding.ooTabLayout.selectTab(binding.ooTabLayout.getTabAt(position))
//            }
//        })
//    }
}