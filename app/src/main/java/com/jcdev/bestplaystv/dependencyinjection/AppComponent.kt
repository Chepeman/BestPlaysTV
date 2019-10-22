package com.jcdev.bestplaystv.dependencyinjection

import android.app.Application
import com.jcdev.bestplaystv.ui.view.activity.PlaysActivity
import com.jcdev.bestplaystv.ui.viewmodel.PlaysViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(viewModel: PlaysViewModel)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}