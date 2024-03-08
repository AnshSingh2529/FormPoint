package coading.champ.online_form_india.adapter

import coading.champ.online_form_india.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerVideoFragmentAdapter(var list:ArrayList<String>,var context: Context?) :
    RecyclerView.Adapter<RecyclerVideoFragmentAdapter.VideoFragmentVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoFragmentVH {
        return VideoFragmentVH(LayoutInflater.from(context).inflate(R.layout.recycler_video_fragment_lay,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VideoFragmentVH, position: Int) {
    }

    class VideoFragmentVH(itemview: View) : RecyclerView.ViewHolder(itemview)
}