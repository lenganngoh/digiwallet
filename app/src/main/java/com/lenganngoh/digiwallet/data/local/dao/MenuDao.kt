package com.lenganngoh.digiwallet.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lenganngoh.digiwallet.data.model.History
import com.lenganngoh.digiwallet.data.model.Wallet

@Dao
interface MenuDao {
    @Query("SELECT * FROM wallet")
    fun getLocalWalletList(): LiveData<List<Wallet>>

    @Query("DELETE FROM wallet")
    fun clearLocalWalletList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(wallet: Wallet)

    @Query("SELECT * FROM history")
    fun getLocalHistoryList(): LiveData<List<History>>

    @Query("DELETE FROM history")
    fun clearLocalHistoryList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)
}