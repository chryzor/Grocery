package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListHomeActivity : AppCompatActivity() {

    // UI components
    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemButton: Button
    private lateinit var homeButton: Button
    private lateinit var logoutButton: Button
    private lateinit var listTitle: TextView
    private lateinit var tokenTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_home)

        // Initialize UI components
        recyclerView = findViewById(R.id.itemList)
        addItemButton = findViewById(R.id.addButton)
        logoutButton = findViewById(R.id.homeLogoutButton)
        listTitle = findViewById(R.id.listTitle)
        tokenTitle = findViewById(R.id.tokenTitle)

        val listName = intent.getStringExtra("listName") ?: "No List Name"
        val uniqueToken = intent.getStringExtra("token") ?: "No token"

        listTitle.text = listName
        tokenTitle.text = "List Token: ${uniqueToken}"

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Button listeners
        addItemButton.setOnClickListener {
            // Navigate to add item activity
            val intent = Intent(this, AddForm::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            finish()
        }
    }
}