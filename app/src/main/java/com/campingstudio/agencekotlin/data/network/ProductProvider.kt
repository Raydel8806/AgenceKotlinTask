package com.campingstudio.agencekotlin.data.network

import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.data.model.Product
import java.util.*

class ProductProvider {
    private var products  = listOf(
        Product(
            1,
            "Product "+1,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            2,
            "Product "+2,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            3,
            "Product "+3,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            4,
            "Product "+4,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            5,
            "Product "+5,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            6,
            "Product "+6,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            7,
            "Product "+7,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),
        Product(
            8,
            "Product "+8,
            "DateOfCreation:"+Calendar.getInstance().time.toString(),
            R.drawable.com_facebook_button_icon),

    )

    fun getAllProducts(): List<Product>? {
        return products
    }
}