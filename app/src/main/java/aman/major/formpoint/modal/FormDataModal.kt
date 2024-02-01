package aman.major.formpoint.modal

import android.os.Parcelable

data class FormDataModal(
    val img : Int,
    val charges: String,
    val created_at: String,
    val eligibility: List<String>,
    val extra_charges: String,
    val id: String,
    val level: String,
    val name: String,
    val requirements: List<String>,
    val status: String,
    val type: String,
    val updated_at: String
)