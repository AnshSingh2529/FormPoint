package coading.champ.online_form_india.modal

import com.google.gson.annotations.SerializedName

data class ApplicantData(
    @SerializedName("applicant_name")
    val applicantName: String,
    val mobile: String,
    val email: String,
    val address: String
)