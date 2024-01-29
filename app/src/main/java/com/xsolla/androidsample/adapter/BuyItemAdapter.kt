package com.xsolla.androidsample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xsolla.android.store.entity.response.items.VirtualItemsResponse
import com.xsolla.androidsample.R
import com.xsolla.androidsample.StoreActivity

class BuyItemAdapter(private val parentActivity: StoreActivity, private val items: List<VirtualItemsResponse.Item>) :
    RecyclerView.Adapter<BuyItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyItemViewHolder {
        return BuyItemViewHolder( LayoutInflater.from(parent.context)
            .inflate(R.layout.buy_item_sample, parent, false))
    }

    override fun onBindViewHolder(holder: BuyItemViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.view).load(item.imageUrl).into(holder.itemImage)
        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        var priceText: String
        if(item.virtualPrices.isNotEmpty()) {
            priceText = item.virtualPrices[0].getAmountRaw() + " " + item.virtualPrices[0].name
        } else {
            priceText = item.price?.getAmountRaw() + " " + item.price?.currency.toString()
        }

        holder.itemPrice.text = priceText

        holder.itemButton.setOnClickListener {
            showNotificationMessage("buy item clicked")
        }
    }

    override fun getItemCount() = items.size

    private fun showNotificationMessage(message: String) {
        Toast.makeText(
            parentActivity,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }
}