package com.nurbekov.zh.imageviewer

import com.nurbekov.zh.imageviewer.Utils.Consts
import com.nurbekov.zh.imageviewer.model.FlikrImage
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/?method=flickr.photos.search")
    fun getFlickrImages(@Query("api_key") api_key: String,
                      @Query("text") text: String,
                      @Query("page") page: Int,
                      @Query("per_page") per_page: Int,
                      @Query("format") format: String,
                      @Query("nojsoncallback") nojsoncallback: Int): Observable<FlikrImage.FlickrData>

    companion object {
        fun create(): FlickrApi {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Consts.URL)
                    .build()

            return retrofit.create(FlickrApi::class.java)
        }
    }

}
