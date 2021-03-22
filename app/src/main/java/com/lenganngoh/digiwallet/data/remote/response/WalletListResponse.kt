package com.lenganngoh.digiwallet.data.remote.response

import com.lenganngoh.digiwallet.data.model.Wallet

data class WalletListResponse(
    val wallets: List<Wallet>
)