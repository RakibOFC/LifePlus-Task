package com.rakibofc.lifeplustask.ui.activity

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

open class BaseActivity : AppCompatActivity() {

    fun showError(view: View, @StringRes errorMessageResId: Int) {
        if (view is TextInputEditText) {
            view.error = getString(errorMessageResId)
            view.requestFocus()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}