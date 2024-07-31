package com.rakibofc.lifeplustask.util

import android.content.Context
import android.content.SharedPreferences


object AppPreference {

    fun with(context: Context): Builder {
        return Builder(context)
    }

    class Builder(context: Context) {
        private val preferences: SharedPreferences =
            context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        private val editor: SharedPreferences.Editor = preferences.edit()

        fun addBoolean(key: String?, value: Boolean) {
            editor.putBoolean(key, value).commit()
        }

        fun addLong(key: String?, value: Long) {
            editor.putLong(key, value).commit()
        }

        fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
            return preferences.getBoolean(key, defaultValue)
        }

        fun getLong(key: String?, defaultValue: Long): Long {
            return preferences.getLong(key, defaultValue)
        }

        fun remove(key: String?) {
            editor.remove(key).commit()
        }
    }
}