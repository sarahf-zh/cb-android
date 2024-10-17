package com.example.carebridge.model.autoCompleteResponse

data class PlacesAutoCompleteResponse(
    val predictions: List<Prediction>,
    val status: String
)