package com.example.grocerylistsharingapp

data class ListItem(
    var id: String = "",
    val itemName: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val date: String = "",
    var token: String = ""
)
