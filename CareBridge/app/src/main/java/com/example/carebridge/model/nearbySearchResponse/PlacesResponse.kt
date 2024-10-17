package com.example.carebridge.model.nearbySearchResponse

data class PlacesResponse(
    val html_attributions: List<Any>,
    val results: List<Result>,
    val status: String
)