package com.campingstudio.agencekotlin.ui.view.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.databinding.FragmentHomeBinding
import com.campingstudio.agencekotlin.ui.view.product_detail.ProductDetailActivity
import com.campingstudio.agencekotlin.ui.view.home.adapter.DataAdapter
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var vmHomeViewModel: HomeViewModel
    private val dataAdapter: DataAdapter by lazy {
        DataAdapter(vmHomeViewModel)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        vmHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        vmHomeViewModel.onCreate()
        setupLiveData()
        return root
    }

    private fun setupLiveData() {
        vmHomeViewModel.isLoading.observe(this, {
            binding.pbLoading.isVisible = it
        })

        vmHomeViewModel.tProductListLive.observe(
            this, {
                refreshShoppRecycler()
            }
        )

        vmHomeViewModel.tProductSelected.observe(this, {
            goDetailOfProduct(it)
        })
    }

    private fun goDetailOfProduct(product: Product) {
        val intent = Intent(this.context, ProductDetailActivity::class.java)
        intent.putExtra("product", Gson().toJson(product))
        startActivity(intent)
    }

    private fun refreshShoppRecycler() {
        binding.contentView.rvGridProds.apply {
            layoutManager = GridLayoutManager(
                this@HomeFragment.activity,
                2,
                RecyclerView.VERTICAL,
                false)
            hasFixedSize()
            this.adapter = dataAdapter
        }

    }

   /* override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        authUserHelper.user.let {
            if (it != null) {
                menu.getItem(0).title = "Perfil "+it.userName.toString()
            }
        }
        return true}*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}