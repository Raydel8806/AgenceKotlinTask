package com.campingstudio.agencekotlin.ui.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.core.AuthUserHelper
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.databinding.ActivityShoppBinding
import com.campingstudio.agencekotlin.ui.view.adapter.DataAdapter
import com.campingstudio.agencekotlin.ui.viewmodel.ShopViewModel
import com.google.gson.Gson

class ShoppActivity : AppCompatActivity() {
private lateinit var binding: ActivityShoppBinding
    private lateinit var vmShopViewModel: ShopViewModel

    private lateinit var authUserHelper: AuthUserHelper

    private val dataAdapter: DataAdapter by lazy {
        DataAdapter(vmShopViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Agence Shop"
        binding.toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        vmShopViewModel = ShopViewModel()
        vmShopViewModel.onCreate()
        setupLiveData()
    }

    private fun setupLiveData() {
        vmShopViewModel.isLoading.observe(this, {
            binding.pbLoading.isVisible = it
        })

        vmShopViewModel.tProductListLive.observe(
            this, {
                refreshShoppRecycler()
            }
        )

        vmShopViewModel.tProductSelected.observe(this, {
            goDetailOfProduct(it)
        })
    }
    private fun goDetailOfProduct(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("product", Gson().toJson(product))
        startActivity(intent)
    }
    private fun refreshShoppRecycler() {
        binding.contentView.rvGridProds.apply {
                layoutManager = GridLayoutManager(
                    this@ShoppActivity,
                    2,
                    RecyclerView.VERTICAL,
                    false)
                hasFixedSize()
                this.adapter = dataAdapter
            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                return true
            }
            R.id.action_my_products ->{
                return true
            }
            R.id.action_settings ->{
                return true
            }
            R.id.action_logout -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)}
}
}