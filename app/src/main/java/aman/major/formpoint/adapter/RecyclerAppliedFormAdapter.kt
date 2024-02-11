package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.modal.AppliedFormModal
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAppliedFormAdapter(
    var context: Context,
    var list: ArrayList<AppliedFormModal>,
    var status: Int
) : RecyclerView.Adapter<RecyclerAppliedFormAdapter.AppliedFormVH>() {
    class AppliedFormVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleImg = itemView.findViewById<ImageView>(R.id.titleImage)!!
        val formTitle = itemView.findViewById<TextView>(R.id.formTitle)!!
        val formPostDate = itemView.findViewById<TextView>(R.id.formPostDate)!!
        val formLocation = itemView.findViewById<TextView>(R.id.formLocation)!!
        val formType = itemView.findViewById<TextView>(R.id.formType)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedFormVH {
        return AppliedFormVH(
            LayoutInflater.from(context).inflate(R.layout.recycler_online_form_lay, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AppliedFormVH, position: Int) {
        val modal = list[position]

        holder.formTitle.text = modal.formname
        holder.formPostDate.text = modal.created_at

        //holder.titleImg.setImageResource(R.drawable.)

    }
}