package com.example.appssquaretask.data.api


import android.util.Log
import com.example.appssquaretask.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//
object ApiClient {
    var basE_URL: String? = Constants.baseUrl
    internal var interceptor = HttpLoggingInterceptor()
    internal val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
        .connectTimeout(20, TimeUnit.MINUTES)
        .writeTimeout(20, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.MINUTES)
        .build()
    lateinit var retrofit: Retrofit

    val client: Retrofit
        get() {
            if (basE_URL != null) {
                val base_url = basE_URL
                Log.i("TAG", "getClient: " + basE_URL!!)
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                retrofit = Retrofit.Builder()
                    .baseUrl(basE_URL!!)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit
        }

}


