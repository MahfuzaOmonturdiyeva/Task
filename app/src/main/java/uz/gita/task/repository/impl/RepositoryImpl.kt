package uz.gita.task.repository.impl

import android.util.Log
import com.google.gson.Gson
import uz.gita.task.data.common.MyResult
import uz.gita.task.data.model.request.LoginRequest
import uz.gita.task.data.model.response.ErrorMessage
import uz.gita.task.data.model.response.ProductResponse
import uz.gita.task.data.source.local.LocalStorage
import uz.gita.task.data.source.remote.api.Api
import uz.gita.task.repository.Repository
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val storage: LocalStorage,
    private val api: Api
) : Repository {

    override suspend fun login(data: LoginRequest): MyResult<Unit> {
        try {
            val response = api.userLogin(data)
            if (response.isSuccessful) {
                response.body()?.let {
                    storage.token = it.token
                    return MyResult.Success(Unit)
                }
                return MyResult.Error("Error success")
            } else {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), ErrorMessage::class.java)
                    return MyResult.Message(message.errorMessage!!)
                }
                return MyResult.Error("Error not success")
            }
        } catch (e: IOException) {
            return MyResult.Error(e.toString())
        }
    }

    override suspend fun getAllProducts(): MyResult<List<ProductResponse>> {
        try {
            val response = api.getAllProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    return MyResult.Success(it)
                }
                return MyResult.Error("Error success")
            } else {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), ErrorMessage::class.java)
                    return MyResult.Message(message.errorMessage!!)
                }
                return MyResult.Error("Error not success")
            }
        } catch (e: IOException) {
            return MyResult.Error(e.toString())
        }
    }

    override suspend fun product(id: Int): MyResult<ProductResponse> {
        try {
            val response = api.getProduct(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return MyResult.Success(it)
                }
                return MyResult.Error("Error success")
            } else {
                response.errorBody()?.let {
                    val message = Gson().fromJson(it.string(), ErrorMessage::class.java)
                    return MyResult.Message(message.errorMessage!!)
                }
                return MyResult.Error("Error not success")
            }
        } catch (e: IOException) {
            return MyResult.Error(e.toString())
        }
    }
}