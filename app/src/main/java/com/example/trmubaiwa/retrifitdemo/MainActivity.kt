package com.example.trmubaiwa.retrifitdemo

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.trmubaiwa.retrifitdemo.Adapters.UserRecyclerAdapter
import com.example.trmubaiwa.retrifitdemo.Models.UsersModel
import com.example.trmubaiwa.retrifitdemo.Services.UserAPI
import com.example.trmubaiwa.retrifitdemo.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.koin.android.architecture.ext.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller


class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: UserRecyclerAdapter
    private var userList: List<UsersModel>? = null
    private lateinit var serviceInterface: UserAPI
    private val userViewModel by viewModel<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fastScroller = this.findViewById(R.id.fast_scroller) as VerticalRecyclerViewFastScroller
        val recyclerView = client_list

        fastScroller.setRecyclerView(recyclerView)
        recyclerView.setOnScrollListener(fastScroller.onScrollListener)

        layoutManager = LinearLayoutManager(this)
        client_list.layoutManager = layoutManager
        serviceInterface = App.create()
        searchView.onFocusChangeListener

        makeCall()

        btn_refresh.setOnClickListener {
            makeCall()
        }
    }


    private fun makeCall() {
        btn_refresh.visibility = View.GONE

        if (checkInternetAvailability()) {
            Log.d("State", " there is network and waiting for the call to finish")
//            val call = serviceInterface.getUsers()
            adapter = UserRecyclerAdapter(userViewModel.getUserList())
            client_list.visibility = View.VISIBLE

//            call.enqueue(UserCallback())

        } else {
            client_list.visibility = View.GONE
            txt_connection_message.visibility = View.VISIBLE
            alert(Appcompat, "There is no network, please enable the network to view the client list",
                    "Connection Error") {
                yesButton { toast("oh...") }
                noButton { }
            }.show()
        }
    }

    private fun checkInternetAvailability(): Boolean {
        val cm: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        activeNetwork?.let {
            return activeNetwork.isConnectedOrConnecting
        }
        return false
    }

    private fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
        if (this != null) f(this)
    }

    inner class UserCallback : Callback<List<UsersModel>> {

        override fun onResponse(call: Call<List<UsersModel>>?, response: Response<List<UsersModel>>?) {

            response?.notNull {
                userList = it.body()
                adapter = UserRecyclerAdapter(userViewModel.getUserList())
                client_list.visibility = View.VISIBLE

            }
//            userList = response?.body()
//            adapter = UserRecyclerAdapter(userList!!)
            client_list.adapter = adapter
            Log.d("State", "User List ${userList}")
            Log.d("State", "This is the response body \n ${response?.body()}")
        }

        override fun onFailure(call: Call<List<UsersModel>>?, t: Throwable?) {
            Log.d("State", "This is the resposne Body \n ${call}")
            Log.d("State", "This is the resposne Body \n ${t}")
//            snackbar(R.layout.notification_action, "Failed to make a connection")
            client_list.visibility = View.GONE
//            txt_connection_message.visibility = View.VISIBLE
            txt_connection_message.text = t.toString()
            btn_refresh.visibility = View.VISIBLE
        }
    }
}
