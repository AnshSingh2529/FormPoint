package aman.major.formpoint.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aman.major.formpoint.R
import aman.major.formpoint.adapter.RecyclerVideoFragmentAdapter
import androidx.recyclerview.widget.RecyclerView

class VideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_video,container,false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.fv_recycler)

        val list : ArrayList<String> = ArrayList()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        recyclerView.adapter = RecyclerVideoFragmentAdapter(list,context)

        return view




    }

}