package com.campingstudio.agencekotlin.ext

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Any.toastLong(context: Context, message: String?) {
    val toast = Toast.makeText( context, message,Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
}
fun Any.toastShort(context: Context, message: String?) {
    val toast = Toast.makeText( context, message,Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
}