package coading.champ.online_form_india.modal

data class NotificationModal(
    val created_at: String,
    val id: String,
    val notification: String,
    val form_id : String,
    val title: String
)