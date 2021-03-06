package com.campingstudio.agencekotlin.ui.view.home.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.campingstudio.agencekotlin.R
import com.campingstudio.agencekotlin.data.model.Product
import com.campingstudio.agencekotlin.ui.view.home.HomeViewModel

class DataAdapterViewHolder(itemView: View): RecyclerView
.ViewHolder(itemView) {
    companion object {
        const val TAG = "DataAdapterViewHolder"
    }
      fun bind(itemProduct: Product, homeViewModel: HomeViewModel) {
          val cvProduct = itemView.findViewById<CardView>(R.id.cv_product)
          val ivPhoto = itemView.findViewById<ImageView>(R.id.iv_product_picture)
          val tvProductName = itemView.findViewById<TextView>(R.id.tv_product_name)
          Log.d(TAG, "load: " + itemProduct.urlImageOfProduct)
          tvProductName.text = itemProduct.nameOfProduct
          ivPhoto.setBackgroundResource(itemProduct.urlImageOfProduct)
        cvProduct.setOnClickListener {
        }
        ivPhoto.setOnClickListener {
            homeViewModel.selectProduct(itemProduct)
        }
    }

}