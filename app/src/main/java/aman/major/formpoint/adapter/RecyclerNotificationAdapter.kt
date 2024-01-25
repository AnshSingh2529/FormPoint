package aman.major.formpoint.adapter

import aman.major.formpoint.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerNotificationAdapter(var context: Context,var list: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerNotificationAdapter.NotificationVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        return NotificationVH(LayoutInflater.from(context).inflate(R.layout.recycler_notification_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
    }
    class NotificationVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}