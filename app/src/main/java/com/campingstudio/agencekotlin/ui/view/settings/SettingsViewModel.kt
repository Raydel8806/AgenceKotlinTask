package com.campingstudio.agencekotlin.ui.view.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Configuración..."
    }
    val text: LiveData<String> = _text
}