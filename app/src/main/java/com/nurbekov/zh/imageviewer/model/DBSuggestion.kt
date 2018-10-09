package com.nurbekov.zh.imageviewer.model

import android.provider.BaseColumns

object DBSuggestion  {

    class SuggestionEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "suggestions"
            val COLUMN_ID = "id"
            val COLUMN_TEXT= "text"
        }
    }
}