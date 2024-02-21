package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.modal.FormOnlineModal
import aman.major.formpoint.ui.activity.DocumentManageActivity
import aman.major.formpoint.ui.activity.DocumentUploadActivity
import aman.major.formpoint.ui.activity.OnlineOpportunityActivity
import aman.major.formpoint.ui.activity.VideoPlayActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class RecyclerFormOnlineAdapter(var context: Context, var list: ArrayList<FormOnlineModal>) :
    RecyclerView.Adapter<RecyclerFormOnlineAdapter.FormOnlineVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormOnlineVH {
        return FormOnlineVH(
            LayoutInflater.from(context).inflate(R.layout.recycler_doc_manage_lay, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FormOnlineVH, position: Int) {

        val modal = list[position]
        holder.cardTitle.text = modal.title

        holder.root.setOnClickListener {
            when (position) {
                7 -> {
                    context.startActivity(Intent(context,VideoPlayActivity::class.java))
                }
                5 -> {
                    context.startActivity(Intent(context,DocumentManageActivity::class.java))
                }
                else -> {
                    context.startActivity(Intent(context,OnlineOpportunityActivity::class.java).putExtra("tabPosition",position))
                }
            }
        }

        holder.cardImg.setImageDrawable(context.getDrawable(modal.imgRes))


    }

    class FormOnlineVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTitle = itemView.findViewById<TextView>(R.id.cardTitle)
        val root = itemView.findViewById<MaterialCardView>(R.id.doc_manage_root)
        val cardImg = itemView.findViewById<ImageView>(R.id.cardImg)
    }
}