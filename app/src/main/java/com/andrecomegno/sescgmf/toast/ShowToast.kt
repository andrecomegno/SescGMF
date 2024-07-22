package com.andrecomegno.sescgmf.toast

import android.content.Context
import android.widget.Toast

object ToastUtils {
    // COM ID
    fun showToast(context: Context, messageResId: Int) {
        Toast.makeText(context, context.getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    // COM STRING
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}