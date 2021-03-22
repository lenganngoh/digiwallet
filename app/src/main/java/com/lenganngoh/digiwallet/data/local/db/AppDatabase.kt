package com.lenganngoh.digiwallet.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lenganngoh.digiwallet.data.local.dao.MenuDao
import com.lenganngoh.digiwallet.data.model.History
import com.lenganngoh.digiwallet.data.model.Wallet

@Database(entities = [Wallet::class, History::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "digiwallet.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}