package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ItemsDetailsActivity : AppCompatActivity() {
    // UI Components
    private lateinit var itemName: TextView
    private lateinit var itemBuyBefore: TextView
    private lateinit var itemQuantity: TextView
    private lateinit var itemCost: TextView
    private lateinit var editButton: Button
    private lateinit var removeButton: Button
    private lateinit var logoutButton: Button
    private lateinit var db: FirebaseFirestore
    private var itemId: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)

        db = FirebaseFirestore.getInstance()

        // UI components
        itemName = findViewById(R.id.itemName)
        itemBuyBefore = findViewById(R.id.itemBuyBefore)
        itemQuantity = findViewById(R.id.itemQuantity)
        itemCost = findViewById(R.id.itemCost)
        editButton = findViewById(R.id.editButton)
        removeButton = findViewById(R.id.removeButton)
        logoutButton = findViewById(R.id.logoutButton)

        // Retrieve data from an intent or database
        val name = intent.getStringExtra("itemName") ?: "Unknown Item"
        val buyBefore = intent.getStringExtra("date") ?: "Not Set"
        val quantity = intent.getIntExtra("quantity", 0)
        val cost = intent.getFloatExtra("price", 0.0f)
        itemId = intent.getStringExtra("itemId")
        token = intent.getStringExtra("token")

        // Set data to TextViews
        itemName.text = "Item Name: $name"
        itemBuyBefore.text = "Buy Before: $buyBefore"
        itemQuantity.text = "Quantity: $quantity"
        itemCost.text = "Total Cost: $$cost"

        // Set up button listeners
        editButton.setOnClickListener {
            val intent = Intent(this, EditItemActivity::class.java)
            intent.putExtra("itemName", name)
            intent.putExtra("date", buyBefore)
            intent.putExtra("quantity", quantity)
            intent.putExtra("price", cost)
            intent.putExtra("itemId", itemId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        removeButton.setOnClickListener {
            removeItemFromFirestore()
        }

        logoutButton.setOnClickListener {
            finish()
        }
    }

    private fun removeItemFromFirestore() {
        // Ensure token and itemId are not null
        if (token.isNullOrEmpty() || itemId.isNullOrEmpty()) {
            Toast.makeText(this, "Unable to remove item: Missing token or ID", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("lists")
            .document(token!!)
            .collection("items")
            .document(itemId!!)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show()
                finish() // Close this activity and return to the list
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to remove item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}