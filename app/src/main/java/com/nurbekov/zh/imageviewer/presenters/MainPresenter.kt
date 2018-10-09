package com.nurbekov.zh.imageviewer.presenters

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.nurbekov.zh.imageviewer.model.FlikrImage
import io.reactivex.disposables.Disposable


interface MainPresenter {

    fun getFlikrImages(searchText: String, page: Int, callback: (FlikrImage.FlickrData) -> Unit): Disposable?

    fun photoView(usl: String, context: Context)

    fun insertSuggestion(suggestions: String)

    fun getSuggestions(): ArrayList<String>

    fun deleteSuggestions(suggestions: String)

}