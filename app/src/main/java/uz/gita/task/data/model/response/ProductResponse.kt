package uz.gita.task.data.model.response

data class ProductResponse(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val description: String,
    val price: Float
)