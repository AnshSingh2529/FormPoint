package aman.major.formpoint.adapter

import aman.major.formpoint.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerFormAdapter(var list: ArrayList<String>) : RecyclerView.Adapter<RecyclerFormAdapter.WalletVH>() {
    class WalletVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletVH {

        return WalletVH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_online_form_lay,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WalletVH, position: Int) {
        
    }

}
