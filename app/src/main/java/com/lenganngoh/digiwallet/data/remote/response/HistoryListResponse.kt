package com.lenganngoh.digiwallet.data.remote.response

import com.lenganngoh.digiwallet.data.model.History

data class HistoryListResponse(
    val histories: List<History>
)