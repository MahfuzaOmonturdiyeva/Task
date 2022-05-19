package uz.gita.task.repository

import uz.gita.task.data.common.MyResult
import uz.gita.task.data.model.request.LoginRequest
import uz.gita.task.data.model.response.ProductResponse

interface Repository {

    suspend fun login(data: LoginRequest): MyResult<Unit>

    suspend fun getAllProducts(): MyResult<List<ProductResponse>>

    suspend fun product(id: Int): MyResult<ProductResponse>

}