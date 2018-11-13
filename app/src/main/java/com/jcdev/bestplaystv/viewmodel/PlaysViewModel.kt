package com.jcdev.bestplaystv.viewmodel

import androidx.lifecycle.ViewModel
import com.jcdev.bestplaystv.dependencyinjection.DependencyInjection
import com.jcdev.bestplaystv.transport.PlaysTransport
import javax.inject.Inject

open class PlaysViewModel : ViewModel() {

    @Inject
    lateinit var playsTransport: PlaysTransport

    init {
        DependencyInjection.appComponent.inject(this)
    }
}