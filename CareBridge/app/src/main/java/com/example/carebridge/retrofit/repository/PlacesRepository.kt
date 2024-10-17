package com.example.carebridge.retrofit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carebridge.model.autoCompleteRequest.PlaceAutoCompleteRequest
import com.example.carebridge.model.autoCompleteResponse.Prediction
import com.example.carebridge.model.nearbySearchRequest.PlaceRequest
import com.example.carebridge.model.nearbySearchResponse.Result
import com.example.carebridge.model.placeDetails.PlaceDetailsResponse
import com.example.carebridge.model.placeDetialsRequest.PlaceDetailsRequest
import com.example.carebridge.retrofit.PlacesApi
import com.example.carebridge.utils.NetworkResults
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val placesApi: PlacesApi) {

    private val _autocompleteResult = MutableLiveData<NetworkResults<List<Prediction>>>()
    val autocompleteResultLiveData: LiveData<NetworkResults<List<Prediction>>>
        get() = _autocompleteResult

    private var _placeDetailsResponse =
        MutableLiveData<NetworkResults<PlaceDetailsResponse>>()
    val placeDetailsResponse: LiveData<NetworkResults<PlaceDetailsResponse>>
        get() = _placeDetailsResponse

    private val _nearbyResult = MutableLiveData<NetworkResults<List<Result>>>()
    val nearbyResultLiveData: LiveData<NetworkResults<List<Result>>>
        get() = _nearbyResult

    suspend fun getAutoCompletePlaces(autoCompleteRequest: PlaceAutoCompleteRequest){
        _autocompleteResult.postValue(NetworkResults.Loading())
        try{
            val response = placesApi.getAutoCompletePlaces(autoCompleteRequest.input,autoCompleteRequest.location,autoCompleteRequest.component
                ,autoCompleteRequest.radius,autoCompleteRequest.apiKey)
            if (response.isSuccessful && response.body() != null) {
                _autocompleteResult.postValue(NetworkResults.Success(response.body()!!.predictions))
            } else {
                _autocompleteResult.postValue(NetworkResults.Error("Something went wrong"))
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }
    }

    suspend fun getPlaceDetails(placeDetailsRequest: PlaceDetailsRequest){
        _placeDetailsResponse.postValue(NetworkResults.Loading())
        try{
            val response = placesApi.getPlaceDetails(placeDetailsRequest.placeID,placeDetailsRequest.apiKey)
            if (response.isSuccessful && response.body() != null) {
                _placeDetailsResponse.postValue(NetworkResults.Success(response.body()!!))
            } else {
                _placeDetailsResponse.postValue(NetworkResults.Error("Something went wrong"))
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }
    }

    suspend fun getNearbySearchResults(placeRequest: PlaceRequest){
        _nearbyResult.postValue(NetworkResults.Loading())
        try {
            val response = placesApi.getNearbyPlaces(placeRequest.location,placeRequest.radius,placeRequest.type,placeRequest.apiKey)
            if(response.isSuccessful && response.body() != null){
                _nearbyResult.postValue(NetworkResults.Success(response.body()!!.results))
            }else{
                _nearbyResult.postValue((NetworkResults.Error("Something went wrong")))
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}