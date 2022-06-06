package com.campingstudio.agencekotlin.data.model
import com.campingstudio.agencekotlin.R
import java.util.*

class ProductProvider {
    private var products : List<Product>? = listOf(
        Product(
            1,
            "Google Android",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_android),
        Product(
            2,
            "Google Assistant",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_assistance),
        Product(
            3,
            "Google Drive",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_drive),
        Product(
            4,
            "Google Earth",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_earth),
        Product(
            5,
            "Google Fit",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_fit),
        Product(
            6,
            "Google Play",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_games),
        Product(
            7,
            "Wifi",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_wifi),
        Product(
            8,
            "YouTube",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_youtube),
        Product(
            9,
            "Google Fonts",
            "DateOfCreation:"+Calendar.getInstance().time.toString() + "\\n\"" + "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            R.drawable.imag_fonts),
    )

    fun getAllProducts(): List<Product>? {
        return products
    }
}