package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.modal.ImageDataModal
import aman.major.formpoint.ui.activity.FormDetailActivity
import aman.major.formpoint.ui.activity.OtpActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class RecyclerFormAdapter(
    var context: Context,
    var list: ArrayList<ImageDataModal>,
    var status: Int
) : RecyclerView.Adapter<RecyclerFormAdapter.WalletVH>() {
    class WalletVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleImg = itemView.findViewById<ImageView>(R.id.titleImage)!!
        val formTitle = itemView.findViewById<TextView>(R.id.formTitle)!!
        val formPostDate = itemView.findViewById<TextView>(R.id.formPostDate)!!
        val formLocation = itemView.findViewById<TextView>(R.id.formLocation)!!
        val formType = itemView.findViewById<TextView>(R.id.formType)!!
        val materialCardView3 = itemView.findViewById<MaterialCardView>(R.id.materialCardView3)!!
        val rootLayout = itemView.findViewById<MaterialCardView>(R.id.parent)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletVH {
        return WalletVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_online_form_lay, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: WalletVH, position: Int) {
        val modal = list[position]
        val imgModal = modal.formDataModal
        holder.titleImg.setImageResource(modal.titleImg)
        holder.formTitle.text = imgModal.name
        holder.formLocation.text = "level: " + imgModal.level
        holder.formPostDate.text = imgModal.created_at
        holder.formType.text = imgModal.status

        when (imgModal.status) {
            "upcoming" -> {
                val strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.yellow)
                holder.rootLayout.strokeColor = strokeColor
                val cardBackgroundColor =
                    ContextCompat.getColor(holder.itemView.context, R.color.yellow)
                holder.materialCardView3.setCardBackgroundColor(cardBackgroundColor)
            }

            "current" -> {
                val strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.green)
                holder.rootLayout.strokeColor = strokeColor
                val cardBackgroundColor =
                    ContextCompat.getColor(holder.itemView.context, R.color.green)
                holder.materialCardView3.setCardBackgroundColor(cardBackgroundColor)
            }

            "closed" -> {
                val strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.red)
                holder.rootLayout.strokeColor = strokeColor
                val cardBackgroundColor =
                    ContextCompat.getColor(holder.itemView.context, R.color.red)
                holder.materialCardView3.setCardBackgroundColor(cardBackgroundColor)
            }
        }

        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    FormDetailActivity::class.java
                ).putExtra("formId", imgModal.id)
            )
        }
    }

}
