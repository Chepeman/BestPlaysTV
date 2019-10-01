package com.jcdev.bestplaystv.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_search.view.*

class SearchAdapter(var searchList: List<Any>) :
    RecyclerView.Adapter<SearchResultsVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchResultsVH {
        return SearchResultsVH(
            LayoutInflater.from(p0.context).inflate(
                R.layout.listitem_search,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(searchResultsVH: SearchResultsVH, position: Int) {
        searchResultsVH.bind(searchList[position])
    }

    fun loadItems(searchItems: List<Any>) {
        searchList = searchItems
        notifyItemRangeChanged(0, searchList.size)
    }
}

class SearchResultsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Any) = with(itemView) {
        if (item is Game) {
            Picasso.get()
                .load("https:" + item.thumbnail.replace("exmedium", "exlarge"))
                .fit()
                .noFade()
                .centerCrop()
                .into(itemImage)
            itemTitle.text = item.title
        } else {
            val userItem = item as User
            Picasso.get()
                .load("https:" + userItem.avatar.replace("exmedium", "exlarge"))
                .fit()
                .noFade()
                .centerCrop()
                .into(itemImage)
            itemTitle.text = userItem.id
        }
    }
}