package com.campingstudio.agencekotlin.ui.view.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campingstudio.agencekotlin.core.AuthUserHelper


class ProfileViewModel(context : Context) : ViewModel() {
    private val _authUser = AuthUserHelper(context)
    private val _text = MutableLiveData<String>().apply {
        value = _authUser.user?.userName
    }
    val text: LiveData<String> = _text
}