package com.nurbekov.zh.imageviewer.views

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import android.widget.*
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.nurbekov.zh.imageviewer.R
import com.nurbekov.zh.imageviewer.adapters.SuggestionAdapter
import com.nurbekov.zh.imageviewer.adapters.ImagesAdapter
import com.nurbekov.zh.imageviewer.model.FlikrImage
import com.nurbekov.zh.imageviewer.presenters.MainPresenter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainActivity : AppCompatActivity(), AppCompatActivityInjector, AnkoLogger {

    override val injector: KodeinInjector = KodeinInjector()
    private val presenter: MainPresenter by instance()
    private var disposable: Disposable? = null
    lateinit var adapter: ImagesAdapter
    lateinit var suggestionAdapter: SuggestionAdapter
    var page: Int = 1
    var searchText: String = ""


    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info("onCreate")
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initializeInjector()
        init()
    }

    private fun init(){
        info{"init"}
        recycler_view_suggestions.layoutManager = LinearLayoutManager(this)
        suggestionAdapter = SuggestionAdapter(presenter.getSuggestions(), this)
        recycler_view_suggestions.adapter = suggestionAdapter
        suggestionAdapter.onSuggestionTextClick = { suggestion -> setSuggestion(suggestion) }
        suggestionAdapter.onSuggestionRemoveClick = { suggestion -> removeSuggestion(suggestion) }

        toolbar_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.insertSuggestion(query)
                suggestionAdapter.addItem(query)
                recycler_view_suggestions.visibility = View.GONE
                searchText = query
                searchImages(searchText)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                when (newText.length) {
                    0 -> recycler_view_suggestions.visibility = View.GONE
                    1 -> recycler_view_suggestions.visibility = View.VISIBLE
                }
                return false
            }
        })
    }

    private fun searchImages(searchText: String) {
        info("searchImages")
        disposable = presenter.getFlikrImages(searchText, 1, { response: FlikrImage.FlickrData ->
            adapter = ImagesAdapter(this, response.photos.photo)
            grid_view.adapter = adapter
            adapter!!.onItemClick = { photo ->
                presenter.photoView(photo.url, this)
            }
            scrollListener()
        })
    }


    private fun scrollListener() {
        grid_view.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (totalItemCount - visibleItemCount <= firstVisibleItem && adapter!!.count + 20 <= 4000) {
                    nextPage()
                }
            }
            override fun onScrollStateChanged(view: AbsListView?, state: Int) {
            }
        })
    }

    private fun nextPage() {
        info("nextPage")
        page++
        disposable = presenter.getFlikrImages(searchText, page, { response: FlikrImage.FlickrData ->
            adapter.addItem(response.photos.photo)
            adapter.notifyDataSetChanged()
            adapter!!.onItemClick = { photo ->
                presenter.photoView(photo.url, this)
            }
        })
    }


    private fun setSuggestion(suggestion: String) {
        toolbar_searchview.setQuery(suggestion, true)
        recycler_view_suggestions.visibility = View.GONE
    }

    private fun removeSuggestion(suggestion: String) {
        presenter.deleteSuggestions(suggestion)
        suggestionAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
        destroyInjector()
    }
}
