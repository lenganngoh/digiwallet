package com.lenganngoh.digiwallet.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lenganngoh.digiwallet.data.model.Wallet
import com.lenganngoh.digiwallet.databinding.ViewholderWalletListBinding
import kotlin.collections.ArrayList

class WalletListAdapter : RecyclerView.Adapter<WalletListViewHolder>() {

    private val walletList = ArrayList<Wallet>()

    fun setWalletList(walletList: List<Wallet>) {
        this.walletList.clear()
        this.walletList.addAll(walletList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val binding: ViewholderWalletListBinding =
            ViewholderWalletListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return WalletListViewHolder(binding)
    }

    override fun getItemCount(): Int = walletList.size

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) =
        holder.bind(walletList[position])
}

class WalletListViewHolder(
    private val binding: ViewholderWalletListBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var wallet: Wallet

    fun bind(wallet: Wallet) {
        this.wallet = wallet

        binding.wallet = wallet
        binding.cardWalletBackground.background
        if (wallet.start_color!!.isNotEmpty() &&
            wallet.center_color!!.isNotEmpty() &&
            wallet.end_color!!.isNotEmpty()
        ) {
            val gd = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(
                    Color.parseColor(wallet.start_color),
                    Color.parseColor(wallet.center_color),
                    Color.parseColor(wallet.end_color)
                )
            )
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            }
            gd.gradientRadius = 135f
            binding.cardWalletBackground.background = gd

            binding.iconWallet.load(wallet.icon)
        }

    }
}