package aman.major.formpoint.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object{

        fun getClient():ApiServices{


             val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS)    // Read timeout
                .writeTimeout(30, TimeUnit.SECONDS)   // Write timeout
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY // Logging level
                })
                // Add any other interceptor if needed
                .build()


            var retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiServices::class.java)

        }
    }
}