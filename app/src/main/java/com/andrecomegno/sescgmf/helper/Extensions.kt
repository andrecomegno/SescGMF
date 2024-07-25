package com.andrecomegno.sescgmf.helper

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.andrecomegno.sescgmf.R
import com.andrecomegno.sescgmf.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar) {
    val activity = activity as AppCompatActivity
    activity.setSupportActionBar(toolbar)
    activity.title = ""
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
    toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))
}

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: Int,
    onClick: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val bottomSheetBinding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    bottomSheetBinding.textTitle.text = getString(titleDialog ?: R.string.title_bottom_sheet)
    bottomSheetBinding.textMessage.text = getText(message)
    bottomSheetBinding.btnClick.text = getString(titleButton ?: R.string.bt_button_bottom_sheet)
    bottomSheetBinding.btnClick.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setOnCancelListener {
        onCancel()
    }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()
}