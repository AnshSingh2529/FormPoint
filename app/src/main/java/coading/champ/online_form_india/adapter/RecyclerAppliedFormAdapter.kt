package coading.champ.online_form_india.adapter

import coading.champ.online_form_india.R
import coading.champ.online_form_india.helper.RetrofitClient
import coading.champ.online_form_india.helper.SharedPrefManager
import coading.champ.online_form_india.modal.FormDataModal
import coading.champ.online_form_india.modal.NewFormAppliedModal
import coading.champ.online_form_india.ui.activity.ApplicationStatusActivity
import coading.champ.online_form_india.ui.activity.OtpActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerAppliedFormAdapter(
    var context: Context,
    var list: ArrayList<NewFormAppliedModal>,
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

        holder.formTitle.text = modal.formdetails.name
        holder.formPostDate.text = modal.created_at
        holder.formLocation.text = "Level: ${modal.formdetails.level}"
        holder.formType.text = modal.formdetails.type
        when (status) {
            4 -> {
                holder.titleImg.setImageResource(R.drawable.ic_application_status)
            }

            6 -> {
                holder.titleImg.setImageResource(R.drawable.ic_otp_menual)
            }
        }

        holder.itemView.setOnClickListener {
            when (status) {
                2->{
                    context.startActivity(
                        Intent(
                            context,
                            ApplicationStatusActivity::class.java
                        ).putExtra("formId", modal.form_id)
                            .putExtra("id",modal.id)
                            .putExtra("flag","admitCard")
                    )
                }

                3->{
                    context.startActivity(
                        Intent(
                            context,
                            ApplicationStatusActivity::class.java
                        ).putExtra("formId", modal.form_id)
                            .putExtra("id",modal.id)
                            .putExtra("flag","result")
                    )
                }


                4 -> {
                    context.startActivity(
                        Intent(
                            context,
                            ApplicationStatusActivity::class.java
                        ).putExtra("formId", modal.form_id)
                            .putExtra("id",modal.id)
                            .putExtra("flag","applicationStatus")
                    )
                }

                6 -> {
                    context.startActivity(
                        Intent(
                            context,
                            OtpActivity::class.java
                        ).putExtra("formId", modal.form_id)
                            .putExtra("id", modal.id)
                    )
                }
            }
        }

        //getFormDetails(modal.form_id,context,holder)
        //holder.titleImg.setImageResource(R.drawable.)

    }

    private fun getFormDetails(id: String, context: Context, holder: AppliedFormVH) {
        Log.d("getFormDetails", "getFormDetails: function call: formId: $id")
        val call = RetrofitClient.getClient()
            .getSingleFormData(id, SharedPrefManager.getInstance(context)?.user?.id.toString());
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    if (response.isSuccessful) {
                        Log.d("getFormDetails", "onResponse: response success: ${response.body()}")
                        val jsonObject = response.body();
                        if (jsonObject?.get("status")?.asString.equals("success", true)) {
                            val dataArray = jsonObject?.get("data")?.asJsonArray
                            val dataObj = dataArray?.get(0)?.asJsonObject
                            val modal = Gson().fromJson(dataObj, FormDataModal::class.java)
                            holder.formType.text = modal.type
                            holder.formLocation.text = "Level: ${modal.level}"
//                            binding.afdFormName.text = modal.name
//                            binding.afdFormLevel.text = "Level: ${modal.level}"
//                            binding.afdGovtPrice.text = "₹${modal.charges}"
//                            binding.afdExtraCharges.text = "₹${modal.extra_charges}"
//                            val totalPrice = modal.charges.toInt() + modal.extra_charges.toInt()
//                            binding.afdTotalPrice.text = "₹${totalPrice}"
//                            FormDetailActivity.requiredDocs = modal.requirements
//                            setEligibilityList(modal.eligibility)
//                            setRequiredDocsList(modal.requirements)

                        }
                    } else {
                        Log.d(
                            "getFormDetails",
                            "onResponse: response not success: ${
                                response.errorBody()?.string()
                            } error code: ${response.code()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.d("getFormDetails", "onResponse: Exception found ${e.localizedMessage}")
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("getFormDetails", "onFailure: ${t.localizedMessage}")
            }
        })
    }
}