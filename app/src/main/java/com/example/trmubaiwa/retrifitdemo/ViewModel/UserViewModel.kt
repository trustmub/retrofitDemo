package com.example.trmubaiwa.retrifitdemo.ViewModel

import android.arch.lifecycle.ViewModel
import com.example.trmubaiwa.retrifitdemo.Repository.UserRepository

class UserViewModel constructor(private val repository: UserRepository): ViewModel() {

    fun getUserList() = repository.getUserList()
}