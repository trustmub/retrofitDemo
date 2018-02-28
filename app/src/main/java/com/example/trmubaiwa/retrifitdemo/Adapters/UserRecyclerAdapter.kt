package com.example.trmubaiwa.retrifitdemo.Adapters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.trmubaiwa.retrifitdemo.Models.UsersModel
import com.example.trmubaiwa.retrifitdemo.R


class UserRecyclerAdapter() : RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {

    private lateinit var users: LiveData<List<UsersModel>>

    constructor(userList: LiveData<List<UsersModel>>) : this() {
        this.users = userList
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.client_list_layout, parent, false)
        Log.d("State", "in the on Create View Holder")

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("State", "the number of items ${users.value!!.count()}")
        return users.value!!.count()
    }

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        holder?.let {
            it.username.text = users.value!![position].name
            it.company.text = users.value!![position].company.name
        }
        Log.d("State", "the values here are ${holder?.username}")
        Log.d("State", "the values here are ${holder?.company}")
    }

    open inner class UserViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var username: TextView = item.findViewById(R.id.txt_name_list)
        var company: TextView = item.findViewById(R.id.title)


    }


}