package com.jcdev.bestplaystv.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_popular_game.view.*

class PopularGamesListAdapter(var gameList: List<Game>, val listener: (Game, ImageView) -> Unit) :
    RecyclerView.Adapter<PopularGamesViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PopularGamesViewHolder {
        return PopularGamesViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.listitem_popular_game,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(popularGamesVH: PopularGamesViewHolder, position: Int) {
        popularGamesVH.bind(gameList[position], listener)
    }

    fun loadItems(populargames: List<Game>) {
        gameList = populargames
        notifyItemRangeChanged(0, gameList.size)
    }

    fun getPopularGamesListSize(): Int {
        return gameList.size
    }
}

class PopularGamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(game: Game, listener: (Game, ImageView) -> Unit) = with(itemView) {
        Picasso.get()
            .load("https:" + game.thumbnail.replace("exmedium", "exlarge"))
            .fit()
            .noFade()
            .centerCrop()
            .into(gameImage)

        gameTitleText.text = game.title
        setOnClickListener {
            listener(game, gameImage)
        }
    }
}