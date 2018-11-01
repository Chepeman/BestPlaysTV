package com.jcdev.bestplaystv

import android.app.Application
import com.jcdev.bestplaystv.dependencyinjection.AppModule
import com.jcdev.bestplaystv.dependencyinjection.DependencyInjection
import com.jcdev.bestplaystv.dependencyinjection.DaggerAppComponent

class PlaysApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DependencyInjection.init(DaggerAppComponent.builder()
            .application(this)
            .build())
    }
}