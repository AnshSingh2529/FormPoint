package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.modal.ImageDataModal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerFormAdapter(var list: ArrayList<ImageDataModal>) : RecyclerView.Adapter<RecyclerFormAdapter.WalletVH>() {
    class WalletVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleImg = itemView.findViewById<ImageView>(R.id.titleImage)
        val formTitle = itemView.findViewById<TextView>(R.id.formTitle)
        val formPostDate = itemView.findViewById<TextView>(R.id.formPostDate)
        val formLocation = itemView.findViewById<TextView>(R.id.formLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletVH {
        return WalletVH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_online_form_lay,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WalletVH, position: Int) {
        val modal = list[position]
        val imgModal = modal.formDataModal
        holder.titleImg.setImageResource(modal.titleImg)
        holder.formTitle.text = imgModal.name
        holder.formLocation.text = imgModal.level
        holder.formPostDate.text = imgModal.created_at
    }

}
