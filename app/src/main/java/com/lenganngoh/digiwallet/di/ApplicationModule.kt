package com.lenganngoh.digiwallet.di

import android.content.Context
import com.lenganngoh.digiwallet.BuildConfig
import com.lenganngoh.digiwallet.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * The application module provides dependencies for application level requirements
 * i.e. http client, retrofit2, and database. Any new dependencies that will be used app-wide
 * should be added in here
 */
@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {
    @Provides
    internal fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

    @Provides
    internal fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(httpClient)
            .build()

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)
}