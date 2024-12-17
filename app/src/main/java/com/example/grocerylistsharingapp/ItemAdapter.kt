package com.example.grocerylistsharingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var itemList: MutableList<ListItem>, private val context: Context) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewHolder class
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemNameTextView.text = currentItem.itemName
        holder.quantityTextView.text = "Quantity: ${currentItem.quantity}"
        holder.priceTextView.text = "Price: $${currentItem.price}"
        holder.dateTextView.text = "Date: ${currentItem.date}"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ItemsDetailsActivity::class.java).apply {
                putExtra("itemId", currentItem.id)
                putExtra("itemName", currentItem.itemName)
                putExtra("quantity", currentItem.quantity)
                putExtra("price", currentItem.price.toFloat())
                putExtra("date", currentItem.date)
                putExtra("token", currentItem.token)
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = itemList.size

    // Update data in RecyclerView
    fun updateData(newItems: List<ListItem>) {
        itemList.clear()
        itemList.addAll(newItems)
        notifyDataSetChanged()
    }
}