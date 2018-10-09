package com.nurbekov.zh.imageviewer.model


object FlikrImage {
    data class FlickrData(val stat: String, val photos: Photos)
    data class Photos(val page: Int, val pages: Int, val perpage: Int, val total: Int, val photo: ArrayList<Photo>)
    data class Photo(val id: String, val owner: String, val secret: String, val server: String, val farm: String, var url: String) {
    }
}
