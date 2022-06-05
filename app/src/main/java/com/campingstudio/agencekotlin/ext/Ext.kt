package com.campingstudio.agencekotlin.ext

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Any?.isNull() = this == null

fun Activity.toastLong(  message: String?) {
    val toast = Toast.makeText( this, message,Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
}
fun Activity.toastShort( message: String?) {
    val toast = Toast.makeText( this, message,Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
}
fun Fragment.toastLong(message: String?) {
    val toast = Toast.makeText( this.context, message,Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
}
fun Fragment.toastShort( message: String?) {
    val toast = Toast.makeText( this.context, message,Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.show()
}

fun ImageView.load(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(this.context).load(url).into(this)
    }
}