package com.campingstudio.agencekotlin.ui.view.product_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campingstudio.agencekotlin.data.model.Product

class ProductDetailViewModel:ViewModel() {
    companion object {
        const val TAG = "ProductDetailViewModel"
    }

    private var _tProductSelected = MutableLiveData<Product>()
    var tProductSelected : MutableLiveData<Product> = _tProductSelected

    fun selectProduct(product: Product){
        Log.d(TAG, "selectProduct: ${product.nameOfProduct}")
        tProductSelected.value = product
    }

}