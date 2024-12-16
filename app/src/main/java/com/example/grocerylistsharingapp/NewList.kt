package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class NewList : AppCompatActivity() {

    // UI components
    private lateinit var newListNameEditText: EditText
    private lateinit var saveListButton: Button
    private lateinit var understandButton: Button
    private lateinit var tokenText: TextView

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_list_form)

        // Initialize UI components
        newListNameEditText = findViewById(R.id.newListName)
        saveListButton = findViewById(R.id.saveListButton)
        understandButton = findViewById(R.id.understandButton)
        tokenText = findViewById(R.id.tokenText)

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
        // Generate a unique token using UUID
        val uniqueToken = UUID.randomUUID().toString()

        // Prepare the data to save
        val listData = hashMapOf(
            "listName" to listName,
            "token" to uniqueToken,
            "createdAt" to System.currentTimeMillis()
        )

        // Add the data to Firestore under a "lists" collection
        db.collection("lists")
            .add(listData)
            .addOnSuccessListener { documentReference ->
                // Success: Notify the user and display the token
                Toast.makeText(
                    this,
                    "List saved successfully!",
                    Toast.LENGTH_LONG
                ).show()
                tokenText.text = "The following is your unique token, you will need to save this to join this list in the future: ${uniqueToken}"
            }
            .addOnFailureListener { e ->
                // Handle any errors
                Toast.makeText(this, "Error saving list: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        understandButton.setOnClickListener {
            finish()
//            val intent = Intent(this, ListHomeActivity::class.java)
//            startActivity(intent)
        }
    }
}