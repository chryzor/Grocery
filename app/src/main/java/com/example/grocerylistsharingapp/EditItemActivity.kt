package com.example.grocerylistsharingapp

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class EditItemActivity : AppCompatActivity() {

    private lateinit var editItemName: EditText
    private lateinit var editBuyBeforePicker: DatePicker
    private lateinit var editItemQuantity: EditText
    private lateinit var editItemCost: EditText
    private lateinit var saveEditButton: Button
    private lateinit var cancelEditButton: Button

    private lateinit var db: FirebaseFirestore
    private var itemId: String? = null
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_item_form)

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance()

        // Initialize UI components
        editItemName = findViewById(R.id.editItemName)
        editBuyBeforePicker = findViewById(R.id.editBuyBeforePicker)
        editItemQuantity = findViewById(R.id.editItemQuantity)
        editItemCost = findViewById(R.id.editItemCost)
        saveEditButton = findViewById(R.id.saveEditButton)
        cancelEditButton = findViewById(R.id.cancelEditButton)

        // Retrieve item data from Intent
        val name = intent.getStringExtra("itemName") ?: "Unknown Item"
        val buyBefore = intent.getStringExtra("date") ?: "Not Set"
        val quantity = intent.getIntExtra("quantity", 0)
        val cost = intent.getFloatExtra("price", 0.0f)
        itemId = intent.getStringExtra("itemId")
        token = intent.getStringExtra("token")

        // Set initial values to fields
        editItemName.setText(name)
        editItemQuantity.setText(quantity.toString())
        editItemCost.setText(cost.toString())

        // Parse date and set DatePicker
        val dateParts = buyBefore.split("/")
        if (dateParts.size == 3) {
            // Adjust to fit the DatePicker's year, month, and day structure
            editBuyBeforePicker.updateDate(dateParts[2].toInt(), dateParts[1].toInt() - 1, dateParts[0].toInt())
        }

        // Set save button listener
        saveEditButton.setOnClickListener {
            val updatedName = editItemName.text.toString()
            val updatedQuantity = editItemQuantity.text.toString().toInt()
            val updatedCost = editItemCost.text.toString().toFloat()
            val updatedDate = formatDate(editBuyBeforePicker.year, editBuyBeforePicker.month + 1, editBuyBeforePicker.dayOfMonth)

            updateItemInFirestore(updatedName, updatedQuantity, updatedCost, updatedDate)
        }

        // Set cancel button listener
        cancelEditButton.setOnClickListener {
            finish()  // Close activity without saving changes
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        // Format the date as dd/mm/yyyy
        return "$month/$day/$year"
    }

    private fun updateItemInFirestore(name: String, quantity: Int, cost: Float, date: String) {
        if (itemId.isNullOrEmpty() || token.isNullOrEmpty()) {
            Toast.makeText(this, "Unable to update item: Missing token or ID", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedItem = hashMapOf(
            "itemName" to name,
            "quantity" to quantity,
            "price" to cost,
            "date" to date
        )

        db.collection("lists")
            .document(token!!)
            .collection("items")
            .document(itemId!!)
            .set(updatedItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show()
                finish()  // Close activity and return to the previous screen
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update item: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
