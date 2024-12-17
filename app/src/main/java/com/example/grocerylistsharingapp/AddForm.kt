package com.example.grocerylistsharingapp

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddForm : AppCompatActivity() {
    // UI Components
    private lateinit var saveItemButton: Button
    private lateinit var buyBeforePicker: DatePicker
    private lateinit var itemInputName: EditText
    private lateinit var itemQuantityInput: EditText
    private lateinit var itemCostInput: EditText
    private lateinit var homeButton: Button
    private lateinit var logoutButton: Button

    // Firestore instance
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_form)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Initialize UI components
        saveItemButton = findViewById(R.id.saveItemButton)
        buyBeforePicker = findViewById(R.id.buyBeforePicker)
        itemInputName = findViewById(R.id.itemNameInput)
        itemQuantityInput = findViewById(R.id.itemQuantityInput)
        itemCostInput = findViewById(R.id.itemCostInput)
        logoutButton = findViewById(R.id.logoutButton)

        // Retrieve list token from intent
        val listToken = intent.getStringExtra("token")

        saveItemButton.setOnClickListener {
            saveItemToFirestore(listToken)
        }

        logoutButton.setOnClickListener {
            finish()
        }
    }

    private fun saveItemToFirestore(token: String?) {
        if (token == null) {
            Toast.makeText(this, "Error: Missing list token", Toast.LENGTH_SHORT).show()
            return
        }

        // Get user input
        val itemName = itemInputName.text.toString().trim()
        val quantity = itemQuantityInput.text.toString().toIntOrNull() ?: 0
        val price = itemCostInput.text.toString().toDoubleOrNull() ?: 0.0

        // Format date from DatePicker
        val day = buyBeforePicker.dayOfMonth
        val month = buyBeforePicker.month + 1
        val year = buyBeforePicker.year
        val date = "$month/$day/$year"

        // Validate input
        if (itemName.isEmpty()) {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show()
            return
        }

        // Create ListItem object
        val newItem = hashMapOf(
            "itemName" to itemName,
            "quantity" to quantity,
            "price" to price,
            "date" to date
        )

        // Save to Firestore under the  list token
        db.collection("lists")
            .document(token)
            .collection("items")
            .add(newItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Item saved successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}