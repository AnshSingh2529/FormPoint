package coading.champ.online_form_india.adapter

import coading.champ.online_form_india.R
import coading.champ.online_form_india.modal.NotificationModal
import coading.champ.online_form_india.ui.activity.FormDetailActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerNotificationAdapter(var context: Context,var list: ArrayList<NotificationModal>) :
    RecyclerView.Adapter<RecyclerNotificationAdapter.NotificationVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        return NotificationVH(LayoutInflater.from(context).inflate(R.layout.recycler_notification_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        val modal = list[position]
        holder.rnl_title.text = modal.title
        holder.rnl_msg.text = modal.notification
        holder.rnl_time.text = modal.created_at

        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context,FormDetailActivity::class.java).putExtra("formId",modal.form_id))
        }

    }
    class NotificationVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rnl_time = itemView.findViewById<TextView>(R.id.rnl_time)
        val rnl_title = itemView.findViewById<TextView>(R.id.rnl_title)
        val rnl_msg = itemView.findViewById<TextView>(R.id.rnl_msg)
    }
}