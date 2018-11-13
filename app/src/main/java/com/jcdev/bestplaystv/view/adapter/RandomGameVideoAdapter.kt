package com.jcdev.bestplaystv.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Game
import com.jcdev.bestplaystv.model.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_related_video.view.*


class RandomGameVideoAdapter(var videoList: List<Video>) : RecyclerView.Adapter<RandomGameVideoAdapter.RandomVideoVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomVideoVH {
        return RandomVideoVH(LayoutInflater.from(parent.context).inflate(R.layout.listitem_related_video, parent, false))
    }

    override fun getItemCount(): Int {
        return videoList.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RandomVideoVH, position: Int) {
        holder.bind(videoList[position])
    }

    fun loadItems(relatedVideos : List<Video>) {
        videoList = relatedVideos
        notifyItemRangeChanged(0, videoList.size)
    }

    class RandomVideoVH(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(video : Video) = with(itemView) {
            videoView.loadUrl("https:" + video.embed)
            videoAuthor.text = video.author.id
            videoTitle.text = video.description
        }
    }
}