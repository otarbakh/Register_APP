package com.example.register_app.UI.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.register_app.Model.Request.LoginRequest
import com.example.register_app.Model.Response.LoginResponse
import com.example.register_app.Network.RetrofitBuilder
import com.example.register_app.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LoginVM : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading(false))
    val loginState = _loginState.asStateFlow()




    fun logIn(email: String,password: String){
        viewModelScope.launch {
            loginResponse(email,password).collect{
                _loginState.value = it
            }
        }
    }


    private fun loginResponse(email: String, password: String) = flow {
        val response = RetrofitBuilder.authService.logIn(LoginRequest(email, password))



        try {
            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body).let {
                    emit(Resource.Success(body!!))
                }
            } else {
                val error = response.errorBody()?.string()
                emit(Resource.Error(error.toString()))
            }

        }catch (e:Throwable){
            emit(Resource.Error(e.message.toString()))
        }

    }

}