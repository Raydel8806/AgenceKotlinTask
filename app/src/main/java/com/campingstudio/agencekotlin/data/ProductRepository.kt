package com.campingstudio.agencekotlin.data

import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.data.model.ProductProvider

class ProductRepository {
    fun getAllProducts(): List<Product>? {
        return  ProductProvider().getAllProducts()
    }
}