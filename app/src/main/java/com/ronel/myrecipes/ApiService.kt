package com.ronel.myrecipes

import com.ronel.myrecipes.objects.GenericResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

enum class ApiStatus { LOADING, ERROR, DONE , NO_INTERNET}

private const val BASE_URL = "https://api.spoonacular.com/"

var interceptor = HttpLoggingInterceptor()
var client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
    .addInterceptor(Interceptor { chain ->
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("Accept", "application/json")
        chain.proceed(requestBuilder.build())
    })
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ApiRecipeService{

    @GET("recipes/random?number=20&tags=vegetarian,dessert&apiKey=ecd86a9035e04bca882759cb41f2525b")
    suspend fun getFoods(): GenericResponse
}

object ApiService{
    val retrofitService: ApiRecipeService by lazy {
        retrofit.create(ApiRecipeService::class.java)
    }
}
