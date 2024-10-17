package com.example.carebridge.model.nearbySearchRequest

data class PlaceRequest(

    val location: String,
    val radius: Int,
    val type: String,
    val apiKey: String

)
