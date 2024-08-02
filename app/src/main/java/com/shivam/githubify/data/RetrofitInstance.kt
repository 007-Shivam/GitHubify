package com.shivam.githubify.data

import com.shivam.githubify.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author 007-Shivam (Shivam Bhosle)
 */

object RetrofitInstance {

    private const val PERSONAL_ACCESS_TOKEN = BuildConfig.PERSONAL_ACCESS_TOKEN

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "token $PERSONAL_ACCESS_TOKEN")
            .build()
        chain.proceed(newRequest)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(authInterceptor)
        .build()

    val api: Api = Retrofit.Builder()
        .baseUrl(Api.BASE_URL) // Ensure this is before the client configuration
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(Api::class.java)
}


