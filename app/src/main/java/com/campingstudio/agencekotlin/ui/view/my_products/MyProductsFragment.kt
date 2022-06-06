package com.campingstudio.agencekotlin.ui.view.my_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campingstudio.agencekotlin.databinding.FragmentMyProductsBinding

class MyProductsFragment : Fragment() {

    private lateinit var myProductsViewModel: MyProductsViewModel
    private var _binding: FragmentMyProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myProductsViewModel =
            ViewModelProvider(this)[MyProductsViewModel::class.java]

        _binding = FragmentMyProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        activity!!.title="Mis Productos"
        val textView: TextView = binding.textSlideshow
        myProductsViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}