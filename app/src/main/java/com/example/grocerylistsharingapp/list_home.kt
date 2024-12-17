package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListHomeActivity : AppCompatActivity() {

    // UI components
    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemButton: Button
    private lateinit var logoutButton: Button
    private lateinit var listTitle: TextView
    private lateinit var tokenTitle: TextView

    private lateinit var adapter: ItemAdapter
    private lateinit var db: FirebaseFirestore

    private var itemList = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_home)

        // Initialize UI components
        recyclerView = findViewById(R.id.itemList)
        addItemButton = findViewById(R.id.addButton)
        logoutButton = findViewById(R.id.homeLogoutButton)
        listTitle = findViewById(R.id.listTitle)
        tokenTitle = findViewById(R.id.tokenTitle)

        // Retrieve the list token from intent
        val listName = intent.getStringExtra("listName") ?: "No List Name"
        val uniqueToken = intent.getStringExtra("token") ?: "No token"

        listTitle.text = listName
        tokenTitle.text = "List Token: $uniqueToken"

        // Set up RecyclerView
        adapter = ItemAdapter(itemList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        db = FirebaseFirestore.getInstance()

        // Fetch and load data from Firestore
        fetchItemsFromFirestore(uniqueToken)

        // Button listeners
        addItemButton.setOnClickListener {
            val intent = Intent(this, AddForm::class.java)
            intent.putExtra("token", uniqueToken) // Pass the list token
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            finish()
        }
    }

    private fun fetchItemsFromFirestore(token: String) {
        db.collection("lists")
            .document(token)
            .collection("items")
            .get()
            .addOnSuccessListener { documents ->
                val fetchedItems = mutableListOf<ListItem>()
                for (document in documents) {
                    val item = document.toObject(ListItem::class.java)
                    fetchedItems.add(item)
                }
                adapter.updateData(fetchedItems) // Update RecyclerView
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load items: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onResume() {
        super.onResume()
        // Reload items when returning to this activity
        val token = intent.getStringExtra("token") ?: return
        fetchItemsFromFirestore(token)
    }
}