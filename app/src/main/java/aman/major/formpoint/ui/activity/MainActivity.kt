package aman.major.formpoint.ui.activity

import aman.major.formpoint.R
import aman.major.formpoint.adapter.ViewPagerAdapter
import aman.major.formpoint.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.tab_ic_home))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.tab_ic_search))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.tab_ic_video))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.ic_closed_eye))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.ic_closed_eye))

        binding.viewPager2.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)


        val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                binding.viewPager2.setCurrentItem(position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Tab unselected, do something
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Tab reselected, do something
            }
        }


        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                when(position){
                    0-> {
                        binding.amToolbar.visibility = View.VISIBLE
                        binding.amToolbar.setTitle("Form Online")
                    }
                    1-> {
                        binding.amToolbar.visibility = View.GONE
                    }
                    2-> {
                        binding.amToolbar.visibility = View.VISIBLE
                        binding.amToolbar.setTitle("All Videos")
                    }
                    3-> {

                    }
                    4-> {

                    }
                }

            }
        })

    }
}