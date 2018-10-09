package com.nurbekov.zh.imageviewer.adapters


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nurbekov.zh.imageviewer.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SuggestionAdapter(val userList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<SuggestionAdapter.ViewHolder>(), AnkoLogger {

    var onSuggestionTextClick: ((String) -> Unit)? = null
    var onSuggestionRemoveClick: ((String) -> Unit)? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        info("onBindViewHolder")
        holder?.suggestionText?.text = userList[position]
        holder.suggestionText!!.setOnClickListener {
            onSuggestionTextClick?.invoke(holder.suggestionText.text.toString())
        }
        holder.suggestionRemove.setOnClickListener {
            var suggestion = holder.suggestionText.text.toString()
            onSuggestionRemoveClick?.invoke(suggestion)
            userList.remove(suggestion)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        info("onCreateViewHolder")
        val v = LayoutInflater.from(context).inflate(R.layout.search_items, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun addItem(suggestion: String) {
        if (!userList.contains(suggestion)) userList.add(suggestion)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val suggestionText = itemView.findViewById<TextView>(R.id.suggestion_text)
        val suggestionRemove = itemView.findViewById<ImageView>(R.id.remove_suggestion)

    }

}