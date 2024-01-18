package aman.major.formpoint.adapter

import aman.major.formpoint.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class RecyclerVideoHomeAdapter(var stringList : ArrayList<String>,var context : Context?) : RecyclerView.Adapter<RecyclerVideoHomeAdapter.VideoVH>() {


    inner class VideoVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
       return VideoVH(LayoutInflater.from(context).inflate(R.layout.recycler_video_home_lay,parent,false))
    }

    override fun getItemCount(): Int {
       return stringList.size
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {

    }
}