package com.example.carebridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Toast;

import com.example.carebridge.databinding.FragmentHospitalBinding;
import com.example.carebridge.model.nearbySearchResponse.Result;
import com.example.carebridge.model.placeDetails.PlaceDetailsResponse;
import com.example.carebridge.utils.BottomSheetListener;
import com.example.carebridge.ui.BottomSheetLocation;
import com.example.carebridge.utils.Constants;
import com.example.carebridge.utils.NetworkResults;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HospitalTab extends Fragment
        implements OnMapReadyCallback, BottomSheetListener{

    private static final String TAG = "HospitalTab";

    private boolean mShowAllPlaces = false;
    private Activity context;
    private MapView mapView;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private AlertDialog locationserviceDialog;

    private FragmentHospitalBinding binding;
    private HospitalViewModel viewModel;


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {}

        @Override
        public void onProviderEnabled(String provider) {
            moveCameraToCurrentLocation();
            locationserviceDialog.dismiss();
        }

        @Override
        public void onProviderDisabled(String provider) {
            openDialog();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        context = getActivity();
        binding = FragmentHospitalBinding.inflate(inflater);
        mapView = binding.mapView;

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isLocationEnabled) {
            openDialog();
        }

        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(context);
        mapView.getMapAsync(this);

        binding.myLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.clear();
                moveCameraToCurrentLocation();
                Toast.makeText(context,
                        "The Blue Marker is your current location",
                        Toast.LENGTH_SHORT).show();

            }
        });

        initViewModel();
        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HospitalViewModel.class);
        viewModel.getPlaceDetailsResponseLiveData().observe((LifecycleOwner) this,
                (Observer<? super NetworkResults<PlaceDetailsResponse>>)
                        new Observer <NetworkResults<PlaceDetailsResponse>>() {
                            @Override
                            public void onChanged(NetworkResults<PlaceDetailsResponse> it) {
                                if (it != null && it.getData() != null
                                    &&it.getData().getResult() != null) {
                                    com.example.carebridge.model.placeDetails.Location location = it
                                            .getData().getResult().getGeometry().getLocation();
                                    animateCamera(location.getLat(),
                                            location.getLng(),
                                            it.getData().getResult().getName(),
                                            it.getData().getResult().getPlace_id(),
                                            it.getData().getResult().getTypes().get(0));
                                }
                            }
                        });

        viewModel.getNearbyResultLiveData().observe((LifecycleOwner) context,
                (Observer<? super NetworkResults<List<Result>>>)
                        new Observer <NetworkResults<List<Result>>>() {
                            @Override
                            public void onChanged(NetworkResults<List<Result>> it) {
                                googleMap.clear();
                                if (it != null && it.getData() != null) {
                                    for (Result place : it.getData()) {
                                        LatLng latLng = new LatLng(place.getGeometry().getLocation().getLat(),
                                                place.getGeometry().getLocation().getLng());
                                        addMarkersOnMap(latLng, place.getPlace_id(), place.getTypes().get(0));
                                }
                                }
                            }
                        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.clear();
        UiSettings settings = googleMap.getUiSettings();
        settings.setMapToolbarEnabled(false);
        settings.setMyLocationButtonEnabled(false);
        settings.setCompassEnabled(false);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                queryNearbyPlaces(latLng.latitude, latLng.longitude);
            }
        });

        final Fragment fragment = this;
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (marker.getTag().equals("custom_marker")) {
                    BottomSheetLocation bottomSheet = new BottomSheetLocation();
                    Bundle bundle = new Bundle();
                    bundle.putString("key", marker.getTitle());
                    bottomSheet.setArguments(bundle);
                    bottomSheet.show(fragment.getFragmentManager(), null);
                }
                return true;
            }
        });

        moveCameraToCurrentLocation();
    }

    private BitmapDescriptor getPlaceIcon(String category) {
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
        if (!TextUtils.isEmpty(category)) {
            if (category.equalsIgnoreCase(Constants.PLACE_TYPE_HOSPITAL)) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital);
            } else if (category.equalsIgnoreCase(Constants.PLACE_TYPE_PARK)) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_park);
            } else if (category.equalsIgnoreCase(Constants.PLACE_TYPE_ATM)) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_atm);
            } else if (category.equalsIgnoreCase(Constants.PLACE_TYPE_RESTAURANT)) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_restaurant);
            }
        }
        return icon;
    }

    private void addMarkersOnMap(LatLng latLng,
                                 String place_id,
                                 String place_type) {
        // We only add hospital icon here
        //BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital);
        if (!place_type.equalsIgnoreCase(Constants.PLACE_TYPE_HOSPITAL)) {
            Log.d(TAG, "addMarkersOnMap() got unexpected type: " + place_type);
            return;
        }

        BitmapDescriptor icon = getPlaceIcon(place_type);
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(place_id)
                .icon(icon));
        marker.setTag("custom_marker");
    }

    private void queryNearbyPlaces(double lat, double lng) {
        //query and display the nearby locations
        String locstr = String.valueOf(lat) + "," + String.valueOf(lng);
        viewModel.getNearbyLocations(locstr, Constants.PLACE_TYPE_HOSPITAL /*""*/);

        LatLng currentLatLng = new LatLng(lat, lng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f));
        MarkerOptions markerOption = new MarkerOptions()
                .position(currentLatLng)
                .title("I am here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOption);
    }

    private void moveCameraToCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.setMyLocationEnabled(true);
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        //query and display the nearby locations
                        double lat = location.getLatitude(), lng = location.getLongitude();
                        queryNearbyPlaces(lat, lng);
                    } else {
                        // Request location permission if it is not granted
                        context.requestPermissions(
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                1001
                        );
                    }
                }
            });
        }
    }

    private void animateCamera(Double lat, Double lng,
                               String placeName, String placeId, String category) {
        googleMap.clear();
        LatLng latLng = new LatLng(lat, lng);
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 12f)
        );

        // We only handle hospital type here
        //BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital);
        BitmapDescriptor icon = getPlaceIcon(category);
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(placeName)
                .icon(icon)
        );

        marker.setTag("custom_marker");
        final Fragment fragment = this;
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (marker.getTag().equals("custom_marker")) {
                    BottomSheetLocation bottomSheet = new BottomSheetLocation();
                    Bundle bundle = new Bundle();
                    bundle.putString("key", placeId);
                    bottomSheet.setArguments(bundle);
                    bottomSheet.show(fragment.getFragmentManager(), null);
                }
                return true;
            }
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Location Service Disabled");
        builder.setMessage("Please enable location service to use this app");
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                locationserviceDialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        locationserviceDialog = builder.create();
        locationserviceDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, move the camera to the current location
                moveCameraToCurrentLocation();
            } else {
                // Location permission denied, handle accordingly
                // You can show a message or disable location-related functionality
                Log.d(TAG, "Location permission denied!");
            }
        }
    }

    @Override public void onResume() {
        mapView.onResume();
        super.onResume();

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            openDialog();
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, register the LocationListener and request location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener);
        } else {
            // Permission is not granted, request the permission
            getActivity().requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    1001
            );
        }
    }

    @Override public void onPause() {
        mapView.onPause();
        locationManager.removeUpdates(locationListener);
        super.onPause();
    }

    @Override public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onMarkerAdd(@NonNull LatLng placeLatLng, @NonNull String place_id, @NonNull String category) {
        addMarkersOnMap(placeLatLng, place_id, category);
    }

    @Override
    public void clearMarkers() {
        googleMap.clear();
    }
}

