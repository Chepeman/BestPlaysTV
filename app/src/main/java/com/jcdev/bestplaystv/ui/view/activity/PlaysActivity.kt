package com.jcdev.bestplaystv.ui.view.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jcdev.bestplaystv.R
import com.jcdev.bestplaystv.ui.viewmodel.PlaysViewModel

open class PlaysActivity : AppCompatActivity() {

    protected lateinit var rootView: View

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    protected fun setupSnackbarObserver(viewModel: PlaysViewModel) {
        rootView = findViewById(R.id.root_view)
        viewModel.snackBarMessage.observe(this, Observer { message ->
            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
        })
    }
}