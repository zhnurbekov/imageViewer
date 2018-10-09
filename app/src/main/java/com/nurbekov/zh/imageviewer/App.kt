package com.nurbekov.zh.imageviewer

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.nurbekov.zh.imageviewer.helper.SuggestionHelper
import com.nurbekov.zh.imageviewer.helper.SuggestionHelperDao
import com.nurbekov.zh.imageviewer.presenters.MainPresenterImpl
import com.nurbekov.zh.imageviewer.presenters.MainPresenter

class App : Application(), KodeinAware {
    override val kodein: Kodein = Kodein {
        import(diModel)
    }

    override fun onCreate() {
        super.onCreate()
    }
}

val diModel = Kodein.Module {
    bind<MainPresenter>() with singleton { MainPresenterImpl(instance()) }
    bind<SuggestionHelperDao>() with singleton { SuggestionHelper(instance()) }

}


