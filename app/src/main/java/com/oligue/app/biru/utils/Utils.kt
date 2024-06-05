package com.oligue.app.biru.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun showSnackMessage(view: View, text: CharSequence, duration: Int){
        Snackbar.make(view, text, duration).show()
    }

}