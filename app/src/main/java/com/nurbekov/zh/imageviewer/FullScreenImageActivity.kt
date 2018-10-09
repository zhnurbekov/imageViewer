package com.nurbekov.zh.imageviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_screen_image.*
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class FullScreenImageActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        info("onCreate")
        val callingActivityIntent = intent
        if (callingActivityIntent != null) {
            val imageUri = callingActivityIntent.data
            if (imageUri != null && fullScreenImageView != null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(fullScreenImageView)
            }
        }
        fullScreenImageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
        back_btn.setOnClickListener{
            finish()
        }
    }


}