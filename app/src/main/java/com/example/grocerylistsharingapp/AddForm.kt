package com.example.grocerylistsharingapp

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddForm : AppCompatActivity(){
    // User Interface
    private lateinit var saveItemButton:Button
    private lateinit var buyBeforePicker:DatePicker
    private lateinit var itemInputName:EditText
    private lateinit var itemQuantityInput: EditText
    private  lateinit var itemCostInput: EditText
    private lateinit var homeButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_form)

        // Initialize UI components
        saveItemButton = findViewById(R.id.saveItemButton)
        buyBeforePicker = findViewById(R.id.buyBeforePicker)
        itemInputName = findViewById(R.id.itemNameInput)
        itemQuantityInput = findViewById(R.id.itemQuantityInput)
        itemCostInput = findViewById(R.id.itemCostInput)
        logoutButton = findViewById(R.id.logoutButton)

        // Set up listeners if needed
        saveItemButton.setOnClickListener {
            // Handle saving the item
        }

        logoutButton.setOnClickListener {
            finish()
        }
    }
}
