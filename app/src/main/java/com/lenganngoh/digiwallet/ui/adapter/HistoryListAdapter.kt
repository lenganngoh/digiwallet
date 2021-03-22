package com.lenganngoh.digiwallet.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lenganngoh.digiwallet.data.local.enum.HistoryEntryType
import com.lenganngoh.digiwallet.data.model.History
import com.lenganngoh.digiwallet.databinding.ViewholderHistoryListBinding
import java.math.BigDecimal

class HistoryListAdapter : RecyclerView.Adapter<HistoryListViewHolder>() {

    private val walletList = ArrayList<History>()

    fun setHistoryList(walletList: List<History>) {
        this.walletList.clear()
        this.walletList.addAll(walletList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
        val binding: ViewholderHistoryListBinding =
            ViewholderHistoryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HistoryListViewHolder(binding)
    }

    override fun getItemCount(): Int = walletList.size

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) =
        holder.bind(walletList[position])
}

class HistoryListViewHolder(
    private val binding: ViewholderHistoryListBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var history: History

    fun bind(history: History) {
        this.history = history
        binding.history = history
        HistoryEntryType.fromString(history.entry).let {
            binding.historyEntryDisplayString = it?.display_string
            when (it) {
                HistoryEntryType.INCOMING -> {
                    binding.historySecondaryActor = history.sender
                    binding.historyAmount = String.format("+%s%s", history.currency, history.amount)
                    binding.textTransactionValue.isActivated = true
                }
                else -> {
                    binding.historySecondaryActor = history.recipient
                    binding.historyAmount = String.format("-%s%s", history.currency, history.amount)
                    binding.textTransactionValue.isActivated = false
                }
            }
        }
    }
}