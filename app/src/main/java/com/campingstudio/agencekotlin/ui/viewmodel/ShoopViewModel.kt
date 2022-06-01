package com.campingstudio.agencekotlin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.domain.GetProductsUseCase
import kotlinx.coroutines.launch

class ShopViewModel:ViewModel() {
    companion object {
        const val TAG = "TProductViewModel"
    }

    private val productRepository = GetProductsUseCase()

    val isLoading = MutableLiveData<Boolean>()

    private var _tProductCarListLive = MutableLiveData( mutableListOf<Product>())
    var tProductCarListLive: MutableLiveData<MutableList<Product>> = _tProductCarListLive

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

    fun loadTProductList(){
        productRepository().let {
            tProductListLive.value = (it as MutableList<Product>)
            _tProductListLive.postValue(tProductListLive.value)
        }

        Log.d(TAG,_tProductListLive.value?.size.toString())
    }

    fun selectProduct(product: Product){
        tProductSelected.value = product
    }

    fun addProduct(product: Product) {
        tProductListLive.value?.add(product)
        _tProductListLive.postValue(tProductListLive.value)
    }
}