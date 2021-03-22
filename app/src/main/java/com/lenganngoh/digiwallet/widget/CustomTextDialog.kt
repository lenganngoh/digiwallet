package com.lenganngoh.digiwallet.widget

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lenganngoh.digiwallet.R
import com.lenganngoh.digiwallet.databinding.DialogCustomTextBinding

class CustomTextDialog : DialogFragment() {

    private lateinit var binding: DialogCustomTextBinding
    private lateinit var title: String
    private lateinit var text: String
    private lateinit var buttonText: String
    private lateinit var listener: Listener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_transparent)
        binding = DialogCustomTextBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!, R.style.DialogInterstitialTheme)
        if (dialog.window != null) {
            dialog.window!!.setWindowAnimations(R.style.DialogThemeAnimation)
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initializeViews()
    }

    override fun onResume() {
        super.onResume()
        binding.txtTitle.text = title
        binding.txtField.text = text
        binding.btnField.text = buttonText
    }

    private fun initializeViews() {
        binding.btnField.setOnClickListener {
            listener.onButtonClick(this)
        }

        binding.btnClose.setOnClickListener {
            listener.onClose(this)
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun updateFields(title: String, fieldText: String, buttonText: String) {
        this.title = title
        this.text = fieldText
        this.buttonText = buttonText
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener.onClose(this)
    }

    interface Listener {
        fun onButtonClick(dialog: CustomTextDialog)
        fun onClose(dialog: CustomTextDialog)
    }
}