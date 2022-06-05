package com.campingstudio.agencekotlin.ui.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.ui.view.home.viewholder.DataAdapterViewHolder
import com.campingstudio.agencekotlin.ui.view.home.HomeViewModel

class DataAdapter (var homeViewModel: HomeViewModel) : RecyclerView
.Adapter<DataAdapterViewHolder>() {

    private var adapterData = homeViewModel.tProductListLive.value.orEmpty()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataAdapterViewHolder {

        val layout = R.layout.product_card

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return DataAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DataAdapterViewHolder,
        position: Int
    ) {
        holder.bind(adapterData[position] ,homeViewModel)
    }

    override fun getItemCount(): Int = adapterData.size



}