package coading.champ.online_form_india.ui.activity

import coading.champ.online_form_india.R
import coading.champ.online_form_india.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomTabs()

        


//        binding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                when(tab?.position){
//                    0 ->{
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.fragmentContainerView, HomeActivity())
//                            .commit()
//                    }
//                    1 ->{
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.fragmentContainerView, VideoFragment())
//                            .commit()
//                    }
//                    2 ->{
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.fragmentContainerView, WalletFragment())
//                            .commit()
//                    }
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })


    }

    private fun setCustomTabs() {
        val tabOne = LayoutInflater.from(this).inflate(R.layout.tab_item_layout, null,false)
        val tabImageViewOne = tabOne.findViewById<ImageView>(R.id.tabImageView)
        tabImageViewOne.setImageResource(R.drawable.tab_ic_home)

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.tab_item_layout, null,false)
        val tabImageViewTwo = tabTwo.findViewById<ImageView>(R.id.tabImageView)
        tabImageViewTwo.setImageResource(R.drawable.tab_ic_video)

        val tabThree = LayoutInflater.from(this).inflate(R.layout.tab_item_layout, null,false)
        val tabImageViewThree = tabThree.findViewById<ImageView>(R.id.tabImageView)
        tabImageViewThree.setImageResource(R.drawable.tab_ic_wallet)

        binding.tabLayout.getTabAt(0)?.customView = tabOne
        binding.tabLayout.getTabAt(1)?.customView = tabTwo
        binding.tabLayout.getTabAt(2)?.customView = tabThree
    }
}