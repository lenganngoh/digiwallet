package com.lenganngoh.digiwallet.data.remote.service

import com.lenganngoh.digiwallet.data.remote.response.HistoryListResponse
import com.lenganngoh.digiwallet.data.remote.response.WalletListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MenuService {
    @GET("wallets")
    fun getRemoteWalletList(): Single<Response<WalletListResponse>>

    @GET("wallets/429")
    fun getRemoteWalletListError429(): Single<Response<WalletListResponse>>

    @GET("wallets/500")
    fun getRemoteWalletListError500(): Single<Response<WalletListResponse>>

    @GET("histories")
    fun getRemoteHistoryList(): Single<Response<HistoryListResponse>>

    @GET("histories/429")
    fun getRemoteHistoryListError429(): Single<Response<HistoryListResponse>>

    @GET("histories/500")
    fun getRemoteHistoryListError500(): Single<Response<HistoryListResponse>>
}