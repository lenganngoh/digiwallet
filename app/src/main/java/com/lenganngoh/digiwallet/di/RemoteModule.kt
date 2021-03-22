package com.lenganngoh.digiwallet.di

import com.lenganngoh.digiwallet.data.remote.service.MenuService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object RemoteModule {
    @Provides
    fun provideMenuService(retrofit: Retrofit): MenuService = retrofit.create(
        MenuService::class.java
    )
}