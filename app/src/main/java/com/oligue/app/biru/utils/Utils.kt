package com.oligue.app.biru.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun showSnackMessage(view: View, text: CharSequence){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
    }

}