package com.example.carebridge.utils

import com.google.android.gms.maps.model.LatLng

interface BottomSheetListener {

    fun onMarkerAdd(placeLatLng: LatLng, place_id: String, category: String)

    fun clearMarkers()
}