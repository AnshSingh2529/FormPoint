package coading.champ.online_form_india.modal

data class FormDataModal(
    val img : Int,
    val charges: String,
    val created_at: String,
    val eligibility: List<String>,
    val extra_charges: String,
    val id: String,
    val level: String,
    val name: String,
    val charge_obc: String,
    val charge_general: String,
    var url: String,
    val charge_sc_st: String,
    val requirements: List<String>,
    val status: String,
    val type: String,
    val updated_at: String
)