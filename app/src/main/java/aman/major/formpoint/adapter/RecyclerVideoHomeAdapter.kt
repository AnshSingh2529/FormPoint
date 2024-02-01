package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.modal.VideoModal
import aman.major.formpoint.ui.activity.VideoPlayActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerVideoHomeAdapter(private var videoModalArrayList : ArrayList<VideoModal>, var context : Context?,var path: String) : RecyclerView.Adapter<RecyclerVideoHomeAdapter.VideoVH>() {


    inner class VideoVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rvh_thumbnail = itemView.findViewById<ImageView>(R.id.rvh_thumbnail)!!
        val rvh_datetime = itemView.findViewById<TextView>(R.id.rvh_datetime)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
       return VideoVH(LayoutInflater.from(context).inflate(R.layout.recycler_video_home_lay,parent,false))
    }

    override fun getItemCount(): Int {
       return videoModalArrayList.size
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {
        val modal = videoModalArrayList[position]
        Glide.with(context!!).load(path+modal.thumbnail).into(holder.rvh_thumbnail)
        holder.rvh_datetime.text = modal.created_at

        holder.itemView.setOnClickListener{
            context!!.startActivity(Intent(context,VideoPlayActivity::class.java).putExtra("youtubeLink",modal.youtube_link))
        }
    }
}