package com.nurbekov.zh.imageviewer.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.nurbekov.zh.imageviewer.model.DBSuggestion
import com.nurbekov.zh.imageviewer.model.SuggestionModel

class SuggestionHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) , SuggestionHelperDao {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    override fun insert(suggestions: SuggestionModel): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(DBSuggestion.SuggestionEntry.COLUMN_ID, suggestions.id)
        values.put(DBSuggestion.SuggestionEntry.COLUMN_TEXT, suggestions.text)
        val newRowId = db.insert(DBSuggestion.SuggestionEntry.TABLE_NAME, null, values)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    override fun delete(id: String): Boolean {
        val db = writableDatabase
        val selection = DBSuggestion.SuggestionEntry.COLUMN_ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(DBSuggestion.SuggestionEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }



    override fun getAll(): ArrayList<String> {
        val suggestion = ArrayList<String>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBSuggestion.SuggestionEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var text: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getString(cursor.getColumnIndex(DBSuggestion.SuggestionEntry.COLUMN_ID))
                text = cursor.getString(cursor.getColumnIndex(DBSuggestion.SuggestionEntry.COLUMN_TEXT))
                suggestion.add(text)
                cursor.moveToNext()
            }
        }
        return suggestion
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "imageviewer.db"
        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBSuggestion.SuggestionEntry.TABLE_NAME + " (" +
                        DBSuggestion.SuggestionEntry.COLUMN_ID + " PRIMARY KEY," +
                        DBSuggestion.SuggestionEntry.COLUMN_TEXT + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBSuggestion.SuggestionEntry.TABLE_NAME
    }

}