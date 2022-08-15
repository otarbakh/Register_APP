package com.example.loginregister.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://reqres.in/api/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val authServiceBuilder by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    val authService by lazy{
        authServiceBuilder.create(PostAPI::class.java)
    }
}

