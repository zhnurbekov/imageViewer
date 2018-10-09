package com.nurbekov.zh.imageviewer.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.nurbekov.zh.imageviewer.R
import kotlinx.android.synthetic.main.image_item.view.*
import com.nurbekov.zh.imageviewer.model.FlikrImage
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class ImagesAdapter : BaseAdapter, AnkoLogger {
    var imageList = ArrayList<FlikrImage.Photo>()
    var context: Context? = null
    var onItemClick: ((FlikrImage.Photo) -> Unit)? = null

    constructor(context: Context, foodsList: ArrayList<FlikrImage.Photo>) : super() {
        info { "constructor" }
        this.context = context
        this.imageList = foodsList
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItem(position: Int): Any {
        return imageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addItem(photoList: ArrayList<FlikrImage.Photo>) {
        for (photo in photoList) {
            imageList.add(photo)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        info ("getView")
        val photo = this.imageList[position]
        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val imageView = inflator.inflate(R.layout.image_item, null)
        photo.url = ("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg")
        imageView.setOnClickListener {
            onItemClick?.invoke(photo)
        }

        Glide.with(context)
                .load(photo.url)
                .apply(RequestOptions().override(300).centerCrop()
                        .placeholder(R.mipmap.loading).diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(imageView.image_item)
        return imageView


    }
}
