package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.modal.FormOnlineModal
import aman.major.formpoint.ui.activity.OnlineOpportunityActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class RecyclerFormOnlineAdapter(var context: Context, var list: ArrayList<FormOnlineModal>) : RecyclerView.Adapter<RecyclerFormOnlineAdapter.FormOnlineVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormOnlineVH {
         return FormOnlineVH(LayoutInflater.from(context).inflate(R.layout.recycler_doc_manage_lay,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FormOnlineVH, position: Int) {

        val modal = list[position]
        holder.cardTitle.text = modal.title

        holder.root.setOnClickListener{
            context.startActivity(Intent(context,OnlineOpportunityActivity::class.java).putExtra("tabPosition",holder.adapterPosition))
        }

        holder.cardImg.setImageDrawable(context.getDrawable(modal.imgRes))

        
    }
    class FormOnlineVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTitle = itemView.findViewById<TextView>(R.id.cardTitle)
        val root = itemView.findViewById<MaterialCardView>(R.id.doc_manage_root)
        val cardImg = itemView.findViewById<ImageView>(R.id.cardImg)
    }
}