package com.lenganngoh.digiwallet.data.repository

import androidx.lifecycle.LiveData
import com.lenganngoh.digiwallet.data.local.dao.MenuDao
import com.lenganngoh.digiwallet.data.model.History
import com.lenganngoh.digiwallet.data.model.Wallet
import com.lenganngoh.digiwallet.data.remote.response.HistoryListResponse
import com.lenganngoh.digiwallet.data.remote.service.MenuService
import com.lenganngoh.digiwallet.data.remote.response.WalletListResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val remote: MenuService,
    private val local: MenuDao
) {
    fun clearLocalWalletList() = local.clearLocalWalletList()

    fun insertWallet(wallet: Wallet) = local.insertWallet(wallet)

    fun getLocalWalletList(): LiveData<List<Wallet>> = local.getLocalWalletList()

    fun getRemoteWalletList(): Single<Response<WalletListResponse>> = remote.getRemoteWalletList()

    fun getRemoteWalletListError429(): Single<Response<WalletListResponse>> = remote.getRemoteWalletListError429()

    fun getRemoteWalletListError500(): Single<Response<WalletListResponse>> = remote.getRemoteWalletListError500()

    fun clearLocalHistoryList() = local.clearLocalHistoryList()

    fun insertHistory(history: History) = local.insertHistory(history)

    fun getLocalHistoryList(): LiveData<List<History>> = local.getLocalHistoryList()

    fun getRemoteHistoryList(): Single<Response<HistoryListResponse>> = remote.getRemoteHistoryList()

    fun getRemoteHistoryListError429(): Single<Response<HistoryListResponse>> = remote.getRemoteHistoryListError429()

    fun getRemoteHistoryListError500(): Single<Response<HistoryListResponse>> = remote.getRemoteHistoryListError500()
}