package com.campingstudio.agencekotlin.ui.view.my_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyProductsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Mis Productos..."
    }
    val text: LiveData<String> = _text
}