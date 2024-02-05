package aman.major.formpoint.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerVideoFragmentAdapter
import aman.major.formpoint.helper.Helper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)


        val va_recycler = findViewById<RecyclerView>(R.id.va_recycler)
        val va_toolbar = findViewById<MaterialToolbar>(R.id.va_toolbar)

        va_toolbar.setNavigationOnClickListener {
            finish()
        }
        Helper.getVideoList(va_recycler,this)
    }

}