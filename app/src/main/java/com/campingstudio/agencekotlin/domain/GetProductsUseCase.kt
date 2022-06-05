package com.campingstudio.agencekotlin.domain
import com.campingstudio.agencekotlin.data.ProductRepository

class GetProductsUseCase {
    private val productRepository = ProductRepository()
    operator fun invoke() = productRepository.getAllProducts()
}