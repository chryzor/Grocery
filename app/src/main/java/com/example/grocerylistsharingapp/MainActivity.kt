package com.example.grocerylistsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
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
    private lateinit var joinListButton: Button


    private lateinit var firestore: FirebaseFirestore
    private lateinit var linearView: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding the UI elements
        linearView = findViewById(R.id.linearView)
        joinListButton = findViewById(R.id.joinListButton)
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

        }

    }

}