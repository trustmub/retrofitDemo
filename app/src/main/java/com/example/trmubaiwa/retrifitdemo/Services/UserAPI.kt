package com.example.trmubaiwa.retrifitdemo.Services

import com.example.trmubaiwa.retrifitdemo.Models.UsersModel
import retrofit2.Call
import retrofit2.http.GET


interface UserAPI {

    @GET("users")
    fun getUsers(): Call<List<UsersModel>>
}