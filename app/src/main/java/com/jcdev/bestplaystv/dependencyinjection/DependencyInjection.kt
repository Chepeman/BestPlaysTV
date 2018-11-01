package com.jcdev.bestplaystv.dependencyinjection

class DependencyInjection {
    companion object {
        lateinit var appComponent : AppComponent

        fun init(appComponent: AppComponent) {
            this.appComponent = appComponent
        }

        fun component() : AppComponent {
            return appComponent
        }

    }
}