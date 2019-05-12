package app.company.androidtask.activities.Home.Fragments.MapFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

import app.company.androidtask.R;


public class MapFragment extends Fragment
        implements OnMapReadyCallback, DirectionCallback {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 90;
    View view;
    ProgressBar progressBar;
    LatLng latLng;
    GoogleMap mGoogleMap;
    Marker mCurrLocationMarker;

    boolean EmptyCurrentLocation = true;
    String distance1, distance2, distance3;
    String duration1, duration2, duration3;

    LocationRequest mLocationRequest;
    int destinationCounter = 1;
    Location mLastLocation;

    LatLng firstLatLong = new LatLng(30.961510, 29.542669);
    LatLng secondLatLong = new LatLng(29.309469, 30.843121);
    LatLng thirdLatLong = new LatLng(30.063049, 31.346661);

    LinearLayoutCompat cardLinearLayout;
    TextView destinationNameTextView;
    TextView distanceTextView;

    private FusedLocationProviderClient client;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        cardLinearLayout = view.findViewById(R.id.card_linear_layout);
        progressBar = view.findViewById(R.id.progress_bar);
        destinationNameTextView = view.findViewById(R.id.destination_name_text_view);
        distanceTextView = view.findViewById(R.id.distance_text_view);

        cardLinearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (getFragmentManager() != null) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(this);
            }

        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyLocation();
    }


    public void requestDirection(LatLng origin, LatLng destination) {
        String serverKey = "AIzaSyCRQNGhfmQSmFntJ2xlxpS5scjf8J9Vixk";
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .language(Language.ENGLISH)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            Leg leg = route.getLegList().get(0);
            Info distanceInfo = leg.getDistance();
            Info durationInfo = leg.getDuration();
            switch (destinationCounter) {
                case 1: {
                    distance1 = distanceInfo.getValue();
                    distance1 = String.valueOf(Double.valueOf(distance1) / 1000);
                    duration1 = durationInfo.getValue();
                    mGoogleMap.addMarker(new MarkerOptions().position(firstLatLong).title("Alexandria University").draggable(true));
                    requestDirection(latLng, secondLatLong);

                    destinationCounter++;
                }

                break;
                case 2: {
                    distance2 = distanceInfo.getValue();
                    distance2 = String.valueOf(Double.valueOf(distance2) / 1000);
                    duration2 = durationInfo.getValue();
                    mGoogleMap.addMarker(new MarkerOptions().position(secondLatLong).title("Fayoum").draggable(true));

                    destinationCounter++;
                    requestDirection(latLng, thirdLatLong);
                    break;
                }

                case 3: {
                    distance3 = distanceInfo.getValue();
                    distance3 = String.valueOf(Double.valueOf(distance3) / 1000);
                    duration3 = durationInfo.getValue();
                    mGoogleMap.addMarker(new MarkerOptions().position(thirdLatLong).title("Nasr City").draggable(true));
                    destinationCounter++;
                    progressBar.setVisibility(View.GONE);

                    break;
                }
            }
        }
    }


    @Override
    public void onDirectionFailure(Throwable t) {

    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (EmptyCurrentLocation) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    mLastLocation = locationList.get(locationList.size() - 1);

                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }
                    latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    setMarkers(latLng);
                    EmptyCurrentLocation = false;
                }

            }
        }

    };

    private void setMarkers(LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getString(R.string.current_position));

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        mGoogleMap.addMarker(markerOptions).showInfoWindow();

        requestDirection(latLng, firstLatLong);


        CameraUpdate latLngZoom = CameraUpdateFactory.newLatLngZoom(
                latLng, 6);
        mGoogleMap.animateCamera(latLngZoom);
    }

    @Override
    public void onDestroy() {
        if (client != null) {
            client.removeLocationUpdates(mLocationCallback);
        }
        super.onDestroy();
    }

    private void getMyLocation() {
        client = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500); // two minute interval
        mLocationRequest.setFastestInterval(800);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            } else {
                requestPermission();
            }

        } else {
            client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private boolean permissionAlreadyGranted() {
        int result = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    client.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                }
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                if (!showRationale) {
                    AlertDialog.Builder CallDialogBuilder;
                    AlertDialog CallDialog;

                    CallDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    @SuppressLint("InflateParams")
                    View mView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dialog_request_permission, null);

                    CallDialogBuilder.setView(mView);
                    CallDialog = CallDialogBuilder.create();
                    Window window = CallDialog.getWindow();
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                    }
                    Objects.requireNonNull(CallDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    CallDialog.show();
                    TextView cancelTextView = mView.findViewById(R.id.cancel_text_view);
                    TextView okTextView = mView.findViewById(R.id.ok_text_view);
                    okTextView.setOnClickListener(v2 -> {
                        requestPermission();
                    });
                    cancelTextView.setOnClickListener(v3 -> {
                        CallDialog.dismiss();

                    });

                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(latLng -> {
            if (cardLinearLayout.getVisibility() == View.VISIBLE) {
                cardLinearLayout.setVisibility(View.GONE);
            }
        });
        mGoogleMap.setOnMarkerClickListener(marker -> {
            destinationNameTextView.setText(marker.getTitle());
            if (marker.getPosition().latitude == firstLatLong.latitude && marker.getPosition().longitude == firstLatLong.longitude) {
                distanceTextView.setText(distance1 + " km");
                cardLinearLayout.setVisibility(View.VISIBLE);
                // distanceTextView.setText(duration1);
            } else if (marker.getPosition().latitude == secondLatLong.latitude && marker.getPosition().longitude == secondLatLong.longitude) {
                distanceTextView.setText(distance2 + " km");
                cardLinearLayout.setVisibility(View.VISIBLE);
                //  distanceTextView.setText(duration2);
            } else if (marker.getPosition().latitude == thirdLatLong.latitude && marker.getPosition().longitude == thirdLatLong.longitude) {
                distanceTextView.setText(distance3 + " km");
                cardLinearLayout.setVisibility(View.VISIBLE);
                // distanceTextView.setText(duration3);
            } else {
                cardLinearLayout.setVisibility(View.GONE);

            }


            return false;

        });

    }


}