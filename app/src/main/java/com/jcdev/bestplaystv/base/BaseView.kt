package com.jcdev.bestplaystv.base

import android.content.Context

interface BaseView {
    /**
     * Returns the Context in which the application is running.
     * @return the Context in which the application is running
     */
    fun getContext(): Context
}