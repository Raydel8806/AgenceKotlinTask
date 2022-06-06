package com.campingstudio.agencekotlin.ui.view.product_detail
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.databinding.ActivityProductDetailBinding
import com.google.gson.Gson
import com.campingstudio.agencekotlin.R

class ProductDetailActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityProductDetailBinding

    private lateinit var vmProductDetailViewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vmProductDetailViewModel = ProductDetailViewModel()
        val bundle = intent.extras
        initProps()
        setupLiveData()
        addFragment()
        bundle?.getString("product")?.let {if (it.isNotEmpty()) {vmProductDetailViewModel.selectProduct(Gson().fromJson(it, Product::class.java))}}

    }

    private fun initProps() {
        binding.apply {
            btnAddProduct.setOnClickListener {showConfirmDialog()}
        }
    }

    private fun showConfirmDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@ProductDetailActivity)
        alertDialog.setTitle("Confirmar compra")
        alertDialog.setMessage("Está seguro que desea pagar por este producto?")
        alertDialog.setPositiveButton("Si") { _, _ ->showNotifyDialog()}
        alertDialog.setNegativeButton("No") { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun showNotifyDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@ProductDetailActivity)
        alertDialog.setTitle("Resultado del pago:")
        alertDialog.setMessage("La compra se realizó correctamente !!!")
        alertDialog.setPositiveButton("OK") { _, _ ->onBackPressed()}
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun addFragment( ) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.fl_layout_map, MapFragment())
        ft.commitAllowingStateLoss()
    }

    private fun setupLiveData() {
        vmProductDetailViewModel.tProductSelected.observe(this, {
            binding.tvProductName.text = it.nameOfProduct
            binding.tvProductDescription.text = it.descriptionOfProduct
            binding.ivProductPicture.setBackgroundResource(it.urlImageOfProduct)
        })
    }

}

