package coading.champ.online_form_india.modal

data class AppliedFormModal(
    val admit_card: String,
    val applicant_details: ApplicantDetails,
    val created_at: String,
    val extra_details: String,
    val form_details: FormDetails,
    val form_id: String,
    val id: String,
    val otp: String,
    val reciept: String,
    val result: String,
    val status: String,
    val txn_id: String,
    val updated_at: String,
    val user_id: String
)