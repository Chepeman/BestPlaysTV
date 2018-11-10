package com.jcdev.bestplaystv.dependencyinjection

import android.app.Application
import com.jcdev.bestplaystv.view.activity.PlaysActivity
import com.jcdev.bestplaystv.viewmodel.PlaysViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity : PlaysActivity)

    fun inject(viewModel: PlaysViewModel)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}