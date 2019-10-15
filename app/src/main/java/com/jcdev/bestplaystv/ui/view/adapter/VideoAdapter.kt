package com.jcdev.bestplaystv.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.model.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_related_video.view.*


class VideoAdapter(
    private var videoList: List<Video>,
    private val listener: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoVH>() {

    private var isCastReady = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
        return VideoVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_related_video,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return videoList.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {
        holder.bind(videoList[position], isCastReady, this.listener)
    }

    fun loadItems(relatedVideos: List<Video>) {
        notifyItemRangeRemoved(0, videoList.size)
        videoList = relatedVideos
        notifyItemRangeChanged(0, videoList.size)
    }

    fun setCastReady(isCastReady: Boolean) {
        this.isCastReady = isCastReady
        notifyDataSetChanged()
    }

    class VideoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(video: Video, isCastReady: Boolean, listener: (Video) -> Unit) = with(itemView) {
            if (!isCastReady) {
                videoView.visibility = View.VISIBLE
                videoThumbnail.visibility = View.GONE
                videoView.loadUrl("https:" + video.embed)
                if (videoContainer.hasOnClickListeners()) {
                    videoContainer.setOnClickListener(null)
                }
            } else {
                videoView.visibility = View.GONE
                videoThumbnail.visibility = View.VISIBLE

                var thumbnailUrl = "https:" + video.thumbnail.replace("exmedium", "exlarge")
                thumbnailUrl = if (video.videoId.length == 24) {
                    thumbnailUrl
                        .replace("/video/", "/media/")
                        .replace("/processed/", "/transcoded/")
                } else {
                    thumbnailUrl
                        .replace("/media/", "/video/")
                        .replace("/transcoded/", "/processed/")
                }

                Picasso.get()
                    .load(thumbnailUrl.replace("exmedium", "exlarge"))
                    .fit()
                    .noFade()
                    .centerCrop()
                    .into(videoThumbnail)
                videoContainer.setOnClickListener {
                    listener(video)
                }
            }
            videoAuthor.text = video.author.id
            videoTitle.text = video.description
        }
    }
}