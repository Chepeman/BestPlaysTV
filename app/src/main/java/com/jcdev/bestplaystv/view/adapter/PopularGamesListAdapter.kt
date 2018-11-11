package com.jcdev.bestplaystv.view.adapter

import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.listitem_popular_game.*
import kotlinx.android.synthetic.main.listitem_popular_game.view.*

class PopularGamesListAdapter(var gameList : List<Game>, val listener: (Game) -> Unit) : RecyclerView.Adapter<PopularGamesViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PopularGamesViewHolder {
        return PopularGamesViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.listitem_popular_game, p0, false))
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(popularGamesVH : PopularGamesViewHolder, position : Int) {
       popularGamesVH.bind(gameList[position], listener)
    }

    fun loadItems(populargames : List<Game>) {
        gameList = populargames
        notifyItemRangeChanged(0, gameList.size)
    }

    fun getPopularGamesListSize() : Int {
        return gameList.size
    }
}

class PopularGamesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(game : Game, listener: (Game) -> Unit) = with(itemView) {
        if(game.thumbnail.startsWith("//")) {
            val subStringUrl = "https://" + game.thumbnail.substring(2, game.thumbnail.length)
            Picasso.get().load(subStringUrl).into(gameImage)
        } else {
            Picasso.get().load(game.thumbnail).into(gameImage)
        }

        gameTitleText.text = game.title
        setOnClickListener {
            listener(game)
        }
    }
}