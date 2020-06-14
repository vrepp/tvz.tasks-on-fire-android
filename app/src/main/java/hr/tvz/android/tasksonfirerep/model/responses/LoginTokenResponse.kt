package hr.tvz.android.tasksonfirerep.model.responses

data class LoginTokenResponse(
    val expire: String,
    val token: String
)