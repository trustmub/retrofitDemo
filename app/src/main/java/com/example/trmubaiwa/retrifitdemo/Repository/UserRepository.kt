package com.example.trmubaiwa.retrifitdemo.Repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.UserManager
import android.util.Log
import com.example.trmubaiwa.retrifitdemo.Models.UsersModel
import com.example.trmubaiwa.retrifitdemo.Services.UserAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository constructor(private val webservice: UserAPI) {

    fun getUserList(): LiveData<List<UsersModel>> {
        val data: LiveData<List<UsersModel>> = MutableLiveData()
        webservice.getUsers().enqueue(UserListCallback(data))
        return data
    }

    inner class UserListCallback(private val data: LiveData<List<UsersModel>>) : Callback<List<UsersModel>> {
        override fun onFailure(call: Call<List<UsersModel>>?, t: Throwable?) {
        }

        override fun onResponse(call: Call<List<UsersModel>>?, response: Response<List<UsersModel>>?) {
            println("${data.value}")

            if (response!!.isSuccessful) println(response.body())

        }
    }
}