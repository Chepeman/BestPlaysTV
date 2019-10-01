package com.jcdev.bestplaystv.dependencyinjection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.jcdev.bestplaystv.BuildConfig
import com.jcdev.bestplaystv.base.BaseView
import com.jcdev.bestplaystv.database.PlaysDatabase
import com.jcdev.bestplaystv.database.PlaysRepository
import com.jcdev.bestplaystv.transport.PlaysTransport
import com.jcdev.bestplaystv.transport.Transport
import dagger.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object AppModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideTransport(transport: Transport, application: Application): PlaysTransport {
        return PlaysTransport(transport, application)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePlaysTransport(retrofit: Retrofit): Transport {
        return retrofit.create(Transport::class.java)
    }
    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePlaysDatabase(application: Application): PlaysDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            PlaysDatabase::class.java,
            PlaysDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePlaysRepository(database: PlaysDatabase): PlaysRepository {
        return PlaysRepository(
            database.gameDao
        )
    }
}