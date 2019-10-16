package com.jcdev.bestplaystv.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jcdev.bestplaystv.database.PlaysRepository
import com.jcdev.bestplaystv.dependencyinjection.DependencyInjection
import com.jcdev.bestplaystv.transport.PlaysTransport
import javax.inject.Inject

open class PlaysViewModel : ViewModel() {

    @Inject
    lateinit var playsTransport: PlaysTransport

    @Inject
    lateinit var playsRepository: PlaysRepository

    init {
        DependencyInjection.appComponent.inject(this)
    }
}