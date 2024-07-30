package com.rakibofc.lifeplustask.ui.activity

import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable

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

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
            key,
            T::class.java
        )

        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }
}