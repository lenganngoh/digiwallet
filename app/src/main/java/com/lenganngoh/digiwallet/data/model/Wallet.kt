package com.lenganngoh.digiwallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class Wallet(
    @PrimaryKey
    val id: Long = 0,
    val wallet_name: String? = "",
    val currency: String? = "",
    val balance: Double = 0.0,
    val icon: String? = "",
    val start_color: String? = "",
    val center_color: String? = "",
    val end_color: String? = ""
)