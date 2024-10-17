package com.example.carebridge.model.autoCompleteRequest

data class PlaceAutoCompleteRequest(

    val input: String,
    val location: String,
    val component: String,
    val radius: String,
    val apiKey: String

)
