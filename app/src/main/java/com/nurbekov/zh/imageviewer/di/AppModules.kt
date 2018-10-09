package com.nurbekov.zh.imageviewer.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.nurbekov.zh.imageviewer.diModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
fun appModule(appContext: Context)  {
    val apiModel = Kodein.Module {
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://api.flickr.com/")
                    .build()
        }
    }
}*/


