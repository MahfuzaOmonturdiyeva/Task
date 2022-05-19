package uz.gita.task.di

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.task.data.source.local.LocalStorage
import java.util.logging.Level
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val BASE_URL = "https://valixon.bexatobot.uz"

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    val loggingInterceptor = HttpLoggingInterceptor()

    @[Provides Singleton]
    fun client(@ApplicationContext context: Context, storage: LocalStorage): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor {
                Log.d("1111", "client:${storage.token} ")
                val request = it.request()
                    .newBuilder()
                    .addHeader("Authorization", "Token ${storage.token}")
                    .build()
                it.proceed(request)
            }
            .build()
}