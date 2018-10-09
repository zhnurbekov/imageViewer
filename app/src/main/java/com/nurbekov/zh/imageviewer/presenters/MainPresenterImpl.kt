package com.nurbekov.zh.imageviewer.presenters

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.nurbekov.zh.imageviewer.apiService.FlickrApi
import com.nurbekov.zh.imageviewer.views.FullScreenImageActivity
import com.nurbekov.zh.imageviewer.Utils.Consts
import com.nurbekov.zh.imageviewer.helper.SuggestionHelperDao
import com.nurbekov.zh.imageviewer.model.FlikrImage
import com.nurbekov.zh.imageviewer.model.SuggestionModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainPresenterImpl(private val suggestionHelperDao: SuggestionHelperDao) : MainPresenter, AnkoLogger {

    val OFFSET: Int = 20
    val FORMAT: String = "json"
    val NO_JSON_CALLBACK: Int = 1

    private val apiInterface by lazy {
        FlickrApi.create()
    }

    override fun getFlikrImages(searchText: String, page: Int, callback: (FlikrImage.FlickrData) -> Unit): Disposable? {
        info("getFlikrImages")
        return apiInterface.getFlickrImages(Consts.APY_KEY, searchText, page, OFFSET, FORMAT, NO_JSON_CALLBACK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            info("getFlikrImages apiservice success")
                            callback(result)
                        },
                        { error ->
                            error{"getFlikrImages apiservice false"}
                            System.out.print("")
                        }
                )
    }


    override fun photoView(url: String, context: Context) {
        info("photoView")
        val fullScreenIntent = Intent(context, FullScreenImageActivity::class.java)
        val photoUrl = Uri.parse(url)
        fullScreenIntent.setData(photoUrl)
        context.startActivity(fullScreenIntent)
    }


    override fun insertSuggestion(suggestion: String) {
        info("insertSuggestion")
        suggestionHelperDao.insert(SuggestionModel(suggestion, suggestion))
    }

    override fun getSuggestions(): ArrayList<String> {
        info("getSuggestions")
        return suggestionHelperDao.getAll()
    }

    override fun deleteSuggestions(suggestion: String) {
        info("deleteSuggestions")
        suggestionHelperDao.delete(suggestion)
    }


}