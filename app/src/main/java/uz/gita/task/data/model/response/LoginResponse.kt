package uz.gita.task.data.model.response

data class LoginResponse(
    val login: String,
    val token: String,
    val fullName: String
)
