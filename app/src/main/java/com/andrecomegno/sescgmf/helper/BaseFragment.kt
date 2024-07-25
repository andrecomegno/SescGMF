package com.andrecomegno.sescgmf.helper

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseFragment : Fragment() {

    fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
