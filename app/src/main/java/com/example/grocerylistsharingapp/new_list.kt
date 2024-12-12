package com.example.grocerylistsharingapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class new_list : AppCompatActivity() {

    // UI components
    private lateinit var newListNameEditText: EditText
    private lateinit var saveListButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_list_form)

        // Initialize UI components
        newListNameEditText = findViewById(R.id.newListName)
        saveListButton = findViewById(R.id.saveListButton)

        // Handle Save button click
        saveListButton.setOnClickListener {
            val listName = newListNameEditText.text.toString().trim()

            if (listName.isEmpty()) {
                // Show error if the list name is empty
                Toast.makeText(this, "Please enter a list name", Toast.LENGTH_SHORT).show()
            } else {
                // Logic to save the list (e.g., save to a database or shared preferences)
                saveList(listName)
            }
        }
    }

    // Method to save the new list
    private fun saveList(listName: String) {
        // TODO: Add logic to save the list
        Toast.makeText(this, "List '$listName' saved successfully!", Toast.LENGTH_SHORT).show()

        // Optionally, finish the activity or navigate back to the previous screen
        finish()
    }
}