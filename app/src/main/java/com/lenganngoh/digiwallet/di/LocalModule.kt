package com.lenganngoh.digiwallet.di

import com.lenganngoh.digiwallet.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object LocalModule {
    @Provides
    fun provideMenuDao(db: AppDatabase) = db.menuDao()
}