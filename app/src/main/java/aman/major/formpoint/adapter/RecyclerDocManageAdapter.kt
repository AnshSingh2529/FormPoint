package aman.major.formpoint.adapter

import aman.major.formpoint.R
import aman.major.formpoint.ui.activity.DocumentUploadActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerDocManageAdapter(var context: Context,var list:ArrayList<String>) : RecyclerView.Adapter<RecyclerDocManageAdapter.DocVH>() {
    class DocVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocVH {
        return DocVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_doc_manage_lay, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DocVH, position: Int) {
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context,DocumentUploadActivity::class.java))
        }
    }
}