package com.jcdev.bestplaystv.custom

import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import com.jcdev.bestplaystv.R

class FullscreenClient(private val parent: ViewGroup) : WebChromeClient() {

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
        val rootParent = getRootParent(parent)
        val contentView = rootParent.findViewById<View>(R.id.contentContainer)
        val fullscreenContainer = rootParent.findViewById<FrameLayout>(R.id.fullscreenContainer)

        if(contentView != null) {
            contentView.visibility = View.GONE
        }

        if (fullscreenContainer != null) {
            fullscreenContainer.removeAllViews()
            fullscreenContainer.addView(view)
            contentView.visibility = View.VISIBLE
        }
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        val rootParent = getRootParent(parent)
        val contentView = rootParent.findViewById<View>(R.id.contentContainer)
        val fullscreenContainer = rootParent.findViewById<FrameLayout>(R.id.fullscreenContainer)

        if (fullscreenContainer != null) {
            fullscreenContainer.removeAllViews()
            contentView.visibility = View.GONE
        }

        if(contentView != null) {
            contentView.visibility = View.VISIBLE
        }
    }

    private fun getRootParent(view: View): View {
        return if (view.parent != null && view.id != R.id.root_view) {
            getRootParent(view.parent as View)
        } else {
            view
        }
    }
}