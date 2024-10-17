package com.example.carebridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carebridge.model.nearbySearchRequest.PlaceRequest
import com.example.carebridge.model.placeDetialsRequest.PlaceDetailsRequest
import com.example.carebridge.retrofit.repository.PlacesRepository
import com.example.carebridge.utils.Constants.API_KEY
import com.example.carebridge.utils.Constants.NEARBY_RADIUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(private val placesRepository: PlacesRepository): ViewModel() {

    val placeDetailsResponseLiveData get() = placesRepository.placeDetailsResponse

    val nearbyResultLiveData get() = placesRepository.nearbyResultLiveData

    fun getPlacesDetails(id: String) {
        val placeDetailsRequest = PlaceDetailsRequest(id,
            API_KEY
        )
        viewModelScope.launch {
            placesRepository.getPlaceDetails(placeDetailsRequest)
        }
    }

    fun getNearbyLocations(location: String,type: String){
        val placeRequest = PlaceRequest(location, NEARBY_RADIUS, type, API_KEY)
        viewModelScope.launch {
            placesRepository.getNearbySearchResults(placeRequest)
        }
    }

}