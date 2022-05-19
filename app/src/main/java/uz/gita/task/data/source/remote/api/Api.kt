package uz.gita.task.data.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.gita.task.data.model.request.LoginRequest
import uz.gita.task.data.model.response.LoginResponse
import uz.gita.task.data.model.response.ProductResponse


interface Api {
    @POST("/api/v1/authenticate")
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/v1/products")
    suspend fun getAllProducts(): Response<List<ProductResponse>>

    @GET("/api/v1/products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<ProductResponse>
}