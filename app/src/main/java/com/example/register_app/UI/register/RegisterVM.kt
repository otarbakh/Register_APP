package com.example.register_app.UI.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.register_app.Model.Request.LoginRequest
import com.example.register_app.Model.Response.RegisterResponse
import com.example.register_app.Network.RetrofitBuilder
import com.example.register_app.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RegisterVM : ViewModel() {
    private val _registeredState = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading(false))
    val registeredState = _registeredState.asStateFlow()



    fun register(email: String,password: String){
        viewModelScope.launch {
            registerResponse(email,password).collect{
                _registeredState.value = it
            }
        }
    }


    private fun registerResponse(email: String, password: String) = flow {
        val response = RetrofitBuilder.authService.register(LoginRequest(email, password))

        try {
            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body).let {
                    emit(Resource.Success(body!!))
                }
            } else {
                val error = response.errorBody()
                emit(Resource.Error(error.toString()))
            }

        }catch (e:Throwable){
            emit(Resource.Error(e.message.toString()))
        }

    }

}