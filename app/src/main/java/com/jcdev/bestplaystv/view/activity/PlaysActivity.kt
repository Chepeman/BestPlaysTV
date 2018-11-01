package com.jcdev.bestplaystv.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jcdev.bestplaystv.dependencyinjection.DependencyInjection
import com.jcdev.bestplaystv.transport.PlaysTransport
import com.jcdev.bestplaystv.transport.Transport
import javax.inject.Inject

open class PlaysActivity : AppCompatActivity() {

    @Inject
    lateinit var playsTransport: PlaysTransport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DependencyInjection.appComponent.inject(this)
    }

}