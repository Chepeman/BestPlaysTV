package com.jcdev.bestplaystv.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    protected val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    init {
        DependencyInjection.appComponent.inject(this)
    }
}