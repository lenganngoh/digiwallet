package com.lenganngoh.digiwallet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey
    val id: Long = 0,
    val entry: String? = "",
    val amount: String? = "",
    val currency: String? = "",
    val sender: String? = "",
    val recipient: String? = "",
    val wallet_id: Long = 0,
)