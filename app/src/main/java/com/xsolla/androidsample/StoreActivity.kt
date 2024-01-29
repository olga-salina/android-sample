package com.xsolla.androidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsolla.android.store.XStore
import com.xsolla.android.store.callbacks.GetVirtualItemsCallback
import com.xsolla.android.store.entity.response.items.VirtualItemsResponse
import com.xsolla.androidsample.adapter.BuyItemAdapter

class StoreActivity : AppCompatActivity() {

    private lateinit var itemsView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        XStore.init(254262)

        initUI()
        loadVirtualItems()
    }

    private fun initUI() {
        itemsView = findViewById(R.id.buy_recycler_view)
        itemsView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadVirtualItems() {
        val parentActivity = this
        XStore.getVirtualItems(object : GetVirtualItemsCallback {
            override fun onSuccess(response: VirtualItemsResponse) {
                itemsView.adapter = BuyItemAdapter(parentActivity, response.items.filter { item -> item.virtualPrices.isEmpty() && !item.isFree })
            }

            override fun onError(throwable: Throwable?, errorMessage: String?) {
                showNotificationMessage(errorMessage ?: throwable?.javaClass?.name ?: "Error")
            }
        })
    }

    private fun showNotificationMessage(message: String) {
        Toast.makeText(
            baseContext,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }
}