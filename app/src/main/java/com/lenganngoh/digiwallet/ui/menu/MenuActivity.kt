package com.lenganngoh.digiwallet.ui.menu

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.lenganngoh.digiwallet.R
import com.lenganngoh.digiwallet.databinding.ActivityMenuBinding
import com.lenganngoh.digiwallet.ui.adapter.HistoryListAdapter
import com.lenganngoh.digiwallet.ui.adapter.WalletListAdapter
import com.lenganngoh.digiwallet.util.ActivityUtil
import com.lenganngoh.digiwallet.util.PixelUtil.Companion.toPx
import com.lenganngoh.digiwallet.widget.CustomTextDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private val viewModel: MenuViewModel by viewModels()
    private lateinit var binding: ActivityMenuBinding

    private lateinit var walletAdapter: WalletListAdapter
    private lateinit var historyAdapter: HistoryListAdapter
    private lateinit var skeletonWallet: RecyclerViewSkeletonScreen
    private lateinit var skeletonHistory: RecyclerViewSkeletonScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivityProp()
        setupWalletRecyclerView()
        setupSkeletalLoading()
        setupObservers()
    }

    private fun setupActivityProp() {
        ActivityUtil.renderAsFullscreenAdjustResize(this, binding.root)
    }

    private fun setupObservers() {
        viewModel.localWallet.observe(this, {
            skeletonWallet.hide()
            viewModel.isLoading.value = false
            walletAdapter.setWalletList(it)
        })

        viewModel.localHistory.observe(this, {
            skeletonHistory.hide()
            viewModel.isLoading.value = false
            historyAdapter.setHistoryList(it)
        })

        viewModel.walletError.observe(this, {
            binding.errorWallet.visibility = if (!it.isNullOrEmpty()) {
                viewModel.localWallet.value.let { wallets ->
                    walletAdapter.setWalletList(wallets!!)
                    skeletonWallet.hide()
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        viewModel.historyError.observe(this, {
            binding.errorHistory.visibility = if (!it.isNullOrEmpty()) {
                viewModel.localHistory.value.let { histories ->
                    historyAdapter.setHistoryList(histories!!)
                    skeletonHistory.hide()
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun setupWalletRecyclerView() {
        walletAdapter = WalletListAdapter()
        binding.rvWallets.layoutManager = LinearLayoutManager(this)
        binding.rvWallets.adapter = walletAdapter

        historyAdapter = HistoryListAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = historyAdapter
    }

    private fun setupSkeletalLoading() {
        skeletonWallet = Skeleton.bind(binding.rvWallets).adapter(walletAdapter)
            .load(R.layout.skeleton_wallet_list).show()
        skeletonHistory = Skeleton.bind(binding.rvHistory).adapter(historyAdapter)
            .load(R.layout.skeleton_wallet_list).show()
    }

    private fun animateHeader(size: Int) {
        val view = binding.cardHeader
        val valueAnimator = ValueAnimator.ofInt(view.measuredHeight, size)
        valueAnimator.duration = 500L
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

    fun onTapWalletClick(view: View) {
        viewModel.walletError.value.let {
            showDialog(it!!)
        }
    }

    fun onTapHistoryClick(view: View) {
        viewModel.historyError.value.let {
            showDialog(it!!)
        }
    }

    private fun showDialog(message: String) {
        val customTextDialog = CustomTextDialog()
        customTextDialog.updateFields(
            getString(R.string.str_error_fetching_data), message,
            getString(R.string.str_refresh)
        )

        val listener = object : CustomTextDialog.Listener {
            override fun onButtonClick(dialog: CustomTextDialog) {
                dialog.dismissAllowingStateLoss()
                skeletonHistory.show()
                skeletonWallet.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getRemoteWalletList()
                    viewModel.getRemoteHistoryList()
                }, 1500)
            }

            override fun onClose(dialog: CustomTextDialog) {
                dialog.dismissAllowingStateLoss()
            }
        }
        customTextDialog.setListener(listener)
        customTextDialog.show(supportFragmentManager, null)
    }

    fun onButtonClick(view: View) {
        if (view.id == R.id.btn_back) {
            scaleInMenuButtons()
            return
        }

        binding.scrollContent.scrollY = 0
        binding.errorHistory.visibility = View.GONE
        binding.errorWallet.visibility = View.GONE
        skeletonWallet.show()
        skeletonHistory.show()
        scaleOutMenuButtons()
        when (view.id) {
            R.id.btn_demo1 -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getRemoteWalletList()
                    viewModel.getRemoteHistoryList()
                }, 1500)
            }
            R.id.btn_demo2 -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getRemoteWalletListError429()
                    viewModel.getRemoteHistoryListError429()
                }, 1500)
            }
            R.id.btn_demo3 -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getRemoteWalletListError500()
                    viewModel.getRemoteHistoryListError500()
                }, 1500)
            }
        }
    }

    private fun scaleInMenuButtons() {
        binding.btnDemo1.visibility = View.VISIBLE
        binding.btnDemo2.visibility = View.VISIBLE
        binding.btnDemo3.visibility = View.VISIBLE
        binding.btnBack.visibility = View.GONE

        binding.btnDemo1.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).setListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    val cardHeaderParent = binding.cardHeader.parent as View
                    animateHeader(cardHeaderParent.measuredHeight + 20.toPx())
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}

            }
        ).start()
        binding.btnDemo2.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).setListener(null)
            .start()
        binding.btnDemo3.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200).setListener(null)
            .start()
    }

    private fun scaleOutMenuButtons() {
        binding.btnDemo1.animate().scaleX(0.0f).scaleY(0.0f).setDuration(200).setListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    binding.btnDemo1.visibility = View.GONE
                    binding.btnDemo2.visibility = View.GONE
                    binding.btnDemo3.visibility = View.GONE
                    binding.btnBack.visibility = View.VISIBLE

                    binding.btnBack.scaleX = 0f
                    binding.btnBack.scaleY = 0f
                    binding.btnBack.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200)
                        .setListener(null).start()

                    animateHeader(150.toPx())
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}
            }
        ).start()
        binding.btnDemo2.animate().scaleX(0.0f).scaleY(0.0f).setDuration(200).setListener(null)
            .start()
        binding.btnDemo3.animate().scaleX(0.0f).scaleY(0.0f).setDuration(200).setListener(null)
            .start()
    }
}