package com.example.trmubaiwa.retrifitdemo

import android.app.Application
import org.koin.dsl.module.applicationContext
import com.example.trmubaiwa.retrifitdemo.Services.UserAPI
import com.example.trmubaiwa.retrifitdemo.ViewModel.UserViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(getViewModelModules()))

    }

    companion object {
        fun create(): UserAPI {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .build()

            return retrofit.create(UserAPI::class.java)
        }
    }

    open fun getViewModelModules() = applicationContext {
        viewModel { UserViewModel(get()) }
    }
}



