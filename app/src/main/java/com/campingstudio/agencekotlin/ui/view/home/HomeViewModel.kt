package com.campingstudio.agencekotlin.ui.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.domain.GetProductsUseCase
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    companion object {
        const val TAG = "TProductViewModel"
    }

    private val productRepository = GetProductsUseCase()
    val isLoading = MutableLiveData<Boolean>()
    private var _tProductListLive = MutableLiveData( mutableListOf<Product>())
    var tProductListLive: MutableLiveData<MutableList<Product>> = _tProductListLive
    private var _tProductSelected = MutableLiveData<Product>()
    var tProductSelected : MutableLiveData<Product> = _tProductSelected

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val itResult = productRepository()
            if(!itResult.isNullOrEmpty()){
                tProductListLive.value = (itResult as MutableList<Product>)
                _tProductListLive.postValue(tProductListLive.value)
            }
            isLoading.postValue(false)
        }
    }

    fun selectProduct(product: Product){
        tProductSelected.value = product
    }
/*
    fun addProduct(product: Product) {
        tProductListLive.value?.add(product)
        _tProductListLive.postValue(tProductListLive.value)
    }*/
}