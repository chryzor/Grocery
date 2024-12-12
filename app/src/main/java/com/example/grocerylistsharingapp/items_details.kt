package com.example.grocerylistsharingapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemsDetailsActivity : AppCompatActivity() {
    // UI Components
    private lateinit var itemName: TextView
    private lateinit var itemBuyBefore: TextView
    private lateinit var itemQuantity: TextView
    private lateinit var itemCost: TextView
    private lateinit var editButton: Button
    private lateinit var removeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)

        // UI components
        itemName = findViewById(R.id.itemName)
        itemBuyBefore = findViewById(R.id.itemBuyBefore)
        itemQuantity = findViewById(R.id.itemQuantity)
        itemCost = findViewById(R.id.itemCost)
        editButton = findViewById(R.id.editButton)
        removeButton = findViewById(R.id.removeButton)

        // Retrieve data from an intent or database
        val name = intent.getStringExtra("ITEM_NAME") ?: "Unknown Item"
        val buyBefore = intent.getStringExtra("ITEM_BUY_BEFORE") ?: "Not Set"
        val quantity = intent.getIntExtra("ITEM_QUANTITY", 0)
        val cost = intent.getFloatExtra("ITEM_COST", 0.0f)

        // Set data to TextViews
        itemName.text = "Item Name: $name"
        itemBuyBefore.text = "Buy Before: $buyBefore"
        itemQuantity.text = "Quantity: $quantity"
        itemCost.text = "Total Cost: $$cost"

        // Set up button listeners
        editButton.setOnClickListener {
            // Navigate to edit item activity
        }

        removeButton.setOnClickListener {
            // Remove item logic
        }
    }
}