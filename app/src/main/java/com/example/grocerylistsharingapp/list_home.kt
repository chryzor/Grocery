package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListHomeActivity : AppCompatActivity() {

    // UI components
    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemButton: Button
    private lateinit var homeButton: Button
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_home)

        // Initialize UI components
        recyclerView = findViewById(R.id.itemList)
        addItemButton = findViewById(R.id.addItemButton)
        homeButton = findViewById(R.id.homeButton)
        logoutButton = findViewById(R.id.logoutButton)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Button listeners
        addItemButton.setOnClickListener {
            // Navigate to add item activity
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {

            finish()
        }

        logoutButton.setOnClickListener {
        }
    }
}