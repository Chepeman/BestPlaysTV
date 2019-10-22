package com.jcdev.bestplaystv.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.jcdev.bestplaystv.BuildConfig
import com.jcdev.bestplaystv.database.PlaysDatabase
import com.jcdev.bestplaystv.database.PlaysRepository
import com.jcdev.bestplaystv.transport.PlaysTransport
import dagger.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
object AppModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun providePlaysTransport(retrofit: Retrofit): PlaysTransport {
        return retrofit.create(PlaysTransport::class.java)
    }
    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor { chain ->
                val request = chain.request()
                val httpUrl = request.url().newBuilder()
                    .addQueryParameter("appid", BuildConfig.app_id)
                    .addQueryParameter("appkey", BuildConfig.app_key).build()
                val newRequest = request.newBuilder().url(httpUrl).build()
                chain.proceed(newRequest)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(client)
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