package coading.champ.online_form_india.ui.activity

import android.os.Bundle
import coading.champ.online_form_india.R
import coading.champ.online_form_india.helper.Helper
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