package com.example.loginregister.Network

import com.example.register_app.Model.Request.LoginRequest
import com.example.register_app.Model.Response.LoginResponse
import com.example.register_app.Model.Response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostAPI {
    @POST("login")
    suspend fun logIn(@Body body: LoginRequest): Response<LoginResponse>
    @POST("register")
    suspend fun register(@Body body: LoginRequest): Response<RegisterResponse>
}