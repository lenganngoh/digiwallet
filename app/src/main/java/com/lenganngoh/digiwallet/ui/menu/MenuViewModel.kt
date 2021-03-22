package com.lenganngoh.digiwallet.ui.menu

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenganngoh.digiwallet.data.model.History
import com.lenganngoh.digiwallet.data.model.Wallet
import com.lenganngoh.digiwallet.data.remote.response.HistoryListResponse
import com.lenganngoh.digiwallet.data.remote.response.WalletListResponse
import com.lenganngoh.digiwallet.data.repository.MenuRepository
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Response

class MenuViewModel @ViewModelInject constructor(private val repo: MenuRepository) : ViewModel() {
    var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var walletError: MutableLiveData<String> = MutableLiveData()
    var historyError: MutableLiveData<String> = MutableLiveData()

    var localWallet: LiveData<List<Wallet>> = repo.getLocalWalletList()
    var localHistory: LiveData<List<History>> = repo.getLocalHistoryList()

    fun getRemoteWalletList() {
        isLoading.value = true
        walletError.value = ""
        repo.getRemoteWalletList().subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Response<WalletListResponse>>() {
                override fun onSuccess(t: Response<WalletListResponse>) {
                    if (t.errorBody() != null) {
                        logWalletFetchError(t.raw().message())
                        return
                    }

                    repo.clearLocalWalletList()
                    t.body()?.wallets?.forEach {
                        repo.insertWallet(it)
                    }
                }

                override fun onError(e: Throwable) {
                    logWalletFetchError(e.message)
                }
            })
    }

    fun getRemoteWalletListError429() {
        isLoading.value = true
        walletError.value = ""
        repo.getRemoteWalletListError429().subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Response<WalletListResponse>>() {
                override fun onSuccess(t: Response<WalletListResponse>) {
                    if (t.errorBody() != null) {
                        logWalletFetchError(t.raw().message())
                        return
                    }
                }

                override fun onError(e: Throwable) {
                    logWalletFetchError(e.message)
                }
            })
    }

    fun getRemoteWalletListError500() {
        isLoading.value = true
        walletError.value = ""
        repo.getRemoteWalletListError500().subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Response<WalletListResponse>>() {
                override fun onSuccess(t: Response<WalletListResponse>) {
                    if (t.errorBody() != null) {
                        logWalletFetchError(t.raw().message())
                        return
                    }
                }

                override fun onError(e: Throwable) {
                    logWalletFetchError(e.message)
                }
            })
    }

    fun getRemoteHistoryList() {
        isLoading.value = true
        historyError.value = ""
        repo.getRemoteHistoryList().subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Response<HistoryListResponse>>() {
                override fun onSuccess(t: Response<HistoryListResponse>) {
                    if (t.errorBody() != null) {
                        logHistoryFetchError(t.raw().message())
                        return
                    }

                    repo.clearLocalHistoryList()
                    t.body()?.histories?.forEach {
                        repo.insertHistory(it)
                    }
                }

                override fun onError(e: Throwable) {
                    logHistoryFetchError(e.message)
                }
            })
    }

    fun getRemoteHistoryListError429() {
        isLoading.value = true
        historyError.value = ""
        repo.getRemoteHistoryListError429().subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Response<HistoryListResponse>>() {
                override fun onSuccess(t: Response<HistoryListResponse>) {
                    if (t.errorBody() != null) {
                        logHistoryFetchError(t.raw().message())
                        return
                    }
                }

                override fun onError(e: Throwable) {
                    logHistoryFetchError(e.message)
                }
            })
    }

    fun getRemoteHistoryListError500() {
        isLoading.value = true
        historyError.value = ""
        repo.getRemoteHistoryListError429().subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<Response<HistoryListResponse>>() {
                override fun onSuccess(t: Response<HistoryListResponse>) {
                    if (t.errorBody() != null) {
                        logHistoryFetchError(t.raw().message())
                        return
                    }
                }

                override fun onError(e: Throwable) {
                    logHistoryFetchError(e.message)
                }
            })
    }

    fun logWalletFetchError(message: String?) {
        viewModelScope.launch {
            walletError.value = message
        }
    }

    fun logHistoryFetchError(message: String?) {
        viewModelScope.launch {
            historyError.value = message
        }
    }
}