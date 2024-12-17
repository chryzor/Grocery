package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainer
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    // Needs to get done
    // Create a list, gives unique token for user to remember
    // Join a list, enter token, given nickname for id
    //


    //User Interface elememts
    private lateinit var createListButton: Button
    private lateinit var joinListText: EditText
    private lateinit var joinListButton: Button


    private lateinit var firestore: FirebaseFirestore
    private lateinit var linearView: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding the UI elements
        linearView = findViewById(R.id.linearView)
        joinListButton = findViewById(R.id.joinListButton)
        joinListText = findViewById(R.id.joinListText)
        createListButton = findViewById(R.id.createListButton)

        // Initialize FireStore
        firestore = FirebaseFirestore.getInstance()

        //  Create Button Clicker
        createListButton.setOnClickListener {
            val intent = Intent(this, NewList::class.java)
            startActivity(intent)
        }
        // Join Button Clicker
        joinListButton.setOnClickListener {
            val enteredToken = joinListText.text.toString().trim()

            if (enteredToken.isEmpty()) {
                Toast.makeText(this, "Please enter a token", Toast.LENGTH_SHORT).show()
            } else {
                // Query Firestore for the document with the matching token
                firestore.collection("lists")
                    .whereEqualTo("token", enteredToken)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            // Retrieve the first matching document
                            val document = documents.documents[0]
                            val listName = document.getString("listName") ?: "Unnamed List"
                            val token = document.getString("token") ?: "No Token"

                            // Navigate to the list activity and pass data
                            val intent = Intent(this, ListHomeActivity::class.java)
                            intent.putExtra("listName", listName)
                            intent.putExtra("token", token)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "No list found with the provided token", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error fetching list: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

}