package com.example.googleadsadvancenew.adUtils

import android.app.Activity
import android.content.Context
import android.widget.Toast

object Util {

    @JvmStatic
    fun showToast(c: Context, message: String) {
        try {
            if (!(c as Activity).isFinishing) {
                c.runOnUiThread { //show your Toast here..
                    Toast.makeText(c.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}