package com.nurbekov.zh.imageviewer.helper

import com.nurbekov.zh.imageviewer.model.SuggestionModel


interface SuggestionHelperDao {

    fun insert(suggestions: SuggestionModel): Boolean

    fun getAll(): ArrayList<String>

    fun delete(id: String): Boolean
}