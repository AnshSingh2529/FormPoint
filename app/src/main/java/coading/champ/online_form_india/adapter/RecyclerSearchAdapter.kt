package coading.champ.online_form_india.adapter

import coading.champ.online_form_india.R
import coading.champ.online_form_india.modal.FormSearchModal
import coading.champ.online_form_india.ui.activity.FormDetailActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class RecyclerSearchAdapter(var context: Context, var list : ArrayList<FormSearchModal>) : RecyclerView.Adapter<RecyclerSearchAdapter.SearchVH>() {
    class SearchVH(itemView: View) : RecyclerView.ViewHolder(itemView){

        val searchtext = itemView.findViewById<TextView>(R.id.rsl_text)
        val parent = itemView.findViewById<MaterialCardView>(R.id.rsl_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return SearchVH(LayoutInflater.from(context).inflate(R.layout.recycler_search_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        val modal = list[position]

        holder.searchtext.text = modal.form_name
        holder.parent.setOnClickListener{
            context.startActivity(Intent(context,FormDetailActivity::class.java).putExtra("formId",modal.form_id))
        }



    }
}