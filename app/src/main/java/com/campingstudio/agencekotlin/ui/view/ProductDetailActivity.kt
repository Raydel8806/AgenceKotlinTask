package com.campingstudio.agencekotlin.ui.view
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.databinding.ActivityProductDetailBinding
import com.campingstudio.agencekotlin.ui.viewmodel.ProductDetailViewModel
import com.google.gson.Gson

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var vmProductDetailViewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vmProductDetailViewModel = ProductDetailViewModel()
        val bundle = intent.extras
        setupLiveData()
        bundle?.getString("product")?.let {
            if (it.isNotEmpty()) {
                vmProductDetailViewModel.selectProduct(Gson().fromJson(it, Product::class.java))
                /* Gson().fromJson(it, Product::class.java).apply {
                    binding.tvProductName.text = this.nameOfProduct
                    binding.tvProductDescription.text = this.descriptionOfProduct
                }*/
            }
        }


    }

    private fun setupLiveData() {

        vmProductDetailViewModel.tProductSelected.observe(this, {
            binding.tvProductName.text = it.nameOfProduct
            binding.tvProductDescription.text = it.descriptionOfProduct
        })
    }

    private fun shopingConfirmProduct(){

    }

    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

