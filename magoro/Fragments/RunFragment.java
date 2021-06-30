package com.example.magoro.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.magoro.DBobjects.DefaultSettings;
import com.example.magoro.DBobjects.RunningInfo;
import com.example.magoro.DBobjects.UserInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.magoro.R;


public class RunFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapView;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private Marker mCurrLocationMarker;
    private ArrayList<LatLng> pathList = new ArrayList<>();

    private Button startBtn;
    private Button pauseBtn;
    private Button stopBtn;
    private TextView km;
    private boolean pause = true;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private SharedPreferences runSharedPreferences;
    private boolean isRunning = false;
    private float distance = 0;
    private long elapsed = 0;

    public RunFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View rootView = inflater.inflate(R.layout.fragment_run, container, false);

        runSharedPreferences = getActivity().getSharedPreferences("relatorio", Context.MODE_PRIVATE);

        km = rootView.findViewById(R.id.km);
        startBtn = rootView.findViewById(R.id.start);
        stopBtn = rootView.findViewById(R.id.stop);
        pauseBtn = rootView.findViewById(R.id.pause);
        chronometer = rootView.findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);

        mapView.getMapAsync(this);

        startBtn.setVisibility(View.VISIBLE);
        stopBtn.setVisibility(View.GONE);
        pauseBtn.setVisibility(View.GONE);

        startBtn.setOnClickListener(v -> {

            startBtn.setVisibility(View.GONE);
            stopBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.VISIBLE);



            isRunning = true;

            if (!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
            }
        });

        pauseBtn.setOnClickListener(v -> {

            if (pause) {
                isRunning = false;
                pauseBtn.setBackgroundColor(Color.GREEN);
                String resume = "Resume";
                pauseBtn.setText(resume);
                pause = false;
            } else if (!pause) {
                isRunning = true;
                pauseBtn.setBackgroundColor(Color.parseColor("#00BCD4"));
                String paused = "Pause";
                pauseBtn.setText(paused);
                pause = true;
            }

            if (running) {
                isRunning = false;
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                running = false;
            } else {
                isRunning = true;
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
            }
        });

        stopBtn.setOnClickListener(v -> {

            startBtn.setVisibility(View.GONE);
            stopBtn.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.GONE);

            isRunning = false;

            elapsed = (SystemClock.elapsedRealtime()- chronometer.getBase());

            chronometer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            chronometer.stop();

            zoomToSnapWholeRun();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            snapTheRun();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showPopupWindowPrize(rootView);
        });

        return rootView;
    }



    @Override
    public void onPause() {
        super.onPause();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1500);
        mLocationRequest.setFastestInterval(1500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Location Permission already granted
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            //Request Location Permission
            checkLocationPermission();
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {

                String lastLoc = runSharedPreferences.getString("lastLoc", "0,0");

                Location location = locationList.get(locationList.size() - 1);

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());

                if(!isRunning){

                    String newPreviousLoc = "" + currentLoc.latitude + "," + currentLoc.longitude;
                    SharedPreferences.Editor editor = runSharedPreferences.edit();
                    editor.putString("lastLoc", newPreviousLoc);
                    editor.apply();
                }

                if(isRunning){

                    LatLng previousLoc;
                    if(lastLoc.equals("0,0")){

                        previousLoc = currentLoc;
                    }
                    else{
                        String[] split = lastLoc.split(",");
                        double lat = Double.parseDouble(split[0]);
                        double lng = Double.parseDouble(split[1]);
                        previousLoc = new LatLng(lat,lng);
                    }

                    distance += (float) SphericalUtil.computeDistanceBetween(currentLoc, previousLoc);
                    float distanceKM = distance / 1000;
                    float Rounded = (float) ((float)Math.round(distanceKM * 1000d) / 1000d);
                    km.setText(Rounded + " Km");

                    String newPreviousLoc = "" + currentLoc.latitude + "," + currentLoc.longitude;

                    SharedPreferences.Editor editor = runSharedPreferences.edit();
                    editor.putString("lastLoc", newPreviousLoc);
                    editor.apply();

                    PolylineOptions polylineOptions = new PolylineOptions().color(Color.BLUE).width(5).add(previousLoc).add(currentLoc);
                    pathList.add(currentLoc);
                    mGoogleMap.addPolyline(polylineOptions);
                }

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentLoc);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
            }
        }
    };

    public void zoomToSnapWholeRun(){

        LatLngBounds.Builder bounds = new LatLngBounds.Builder();

        for(LatLng loc : pathList){
            bounds.include(loc);
        }

        View view = mapView.getView();
        int padding = (int) (view.getHeight() * 0.05f);
        if(!pathList.isEmpty()){
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), padding));
        }
    }

    public void snapTheRun(){

        mGoogleMap.snapshot(bitmap -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            String userID = user.getUid();

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserInfo userInfo = snapshot.child("userInfo").getValue(UserInfo.class);

                    float walk2Km = snapshot.child("achievements").child("walk2Km").getValue(float.class);
                    float walk20Km = snapshot.child("achievements").child("walk20Km").getValue(float.class);
                    float walk100Km = snapshot.child("achievements").child("walk100Km").getValue(float.class);
                    float walk1000Km = snapshot.child("achievements").child("walk1000Km").getValue(float.class);
                    float walk2H = snapshot.child("achievements").child("walk2H").getValue(float.class);
                    float walk10H = snapshot.child("achievements").child("walk10H").getValue(float.class);
                    float walk50H = snapshot.child("achievements").child("walk50H").getValue(float.class);
                    float walk500H = snapshot.child("achievements").child("walk500H").getValue(float.class);

                    if(userInfo != null){
                        int weight = userInfo.weight;

                        String encoded = BitmapToString(bitmap);

                        long minute = (elapsed / (1000 * 60)) % 60;
                        long hour = (elapsed / (1000 * 60 * 60)) % 24;
                        long second = (elapsed / 1000) % 60;

                        @SuppressLint("DefaultLocale") String timeFormat = String.format("%02d:%02d:%02d", hour, minute, second);

                        float distanceKM = distance / 1000;
                        float Rounded = (float) ((float)Math.round(distanceKM * 1000d) / 1000d);

                        walk2Km += Rounded;
                        walk20Km += Rounded;
                        walk100Km += Rounded;
                        walk1000Km += Rounded;

                        float totalTimeHours = (float) (hour + (minute / 60.0) + (second / 3600.0));
                        float speed = Rounded / totalTimeHours;
                        float speedRounded = (float) ((float)Math.round(speed * 100d) / 100d);

                        walk2H += totalTimeHours;
                        walk10H += totalTimeHours;
                        walk50H += totalTimeHours;
                        walk500H += totalTimeHours;

                        float totalTimeMinutes = (float) ((hour * 60) + minute + (second / 60));

                        float MET = 0;
                        if(totalTimeMinutes < 10){
                            MET = 6;
                        }
                        else{
                            if(speedRounded < 8){
                                MET = 6;
                            }
                            else if(speedRounded >= 8 || speedRounded < 13){
                                MET = 8;
                            }
                            else if(speedRounded >= 13 || speedRounded < 16){
                                MET = (float) 13.5;
                            }
                            else if(speedRounded >= 16 || speedRounded < 17.5){
                                MET = 16;
                            }
                            else if(speedRounded >= 17.5){
                                MET = 18;
                            }
                        }
                        float calories = (float) (totalTimeMinutes * (MET * 3.5 * weight)/200);

                        reference.child(userID).child("achievements").child("walk2Km").setValue(walk2Km);
                        reference.child(userID).child("achievements").child("walk20Km").setValue(walk20Km);
                        reference.child(userID).child("achievements").child("walk100Km").setValue(walk100Km);
                        reference.child(userID).child("achievements").child("walk1000Km").setValue(walk1000Km);
                        reference.child(userID).child("achievements").child("walk2H").setValue(walk2H);
                        reference.child(userID).child("achievements").child("walk10H").setValue(walk10H);
                        reference.child(userID).child("achievements").child("walk50H").setValue(walk50H);
                        reference.child(userID).child("achievements").child("walk500H").setValue(walk500H);

                        RunningInfo runningInfo = new RunningInfo(Rounded, timeFormat, speedRounded, calories, encoded);
                        reference.child(userID).child("runningInfo").setValue(runningInfo);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    public String BitmapToString(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    public void showPopupWindowPrize(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_run_prize, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button okayBtn = popupView.findViewById(R.id.RedeemBtn);
        TextView textView = popupView.findViewById(R.id.Redeem);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        String userID = user.getUid();

        reference.child(userID).child("settings").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DefaultSettings defaultSettings = snapshot.getValue(DefaultSettings.class);
                if(defaultSettings != null){
                    int fitCoins = defaultSettings.fitCoins;
                    int happinessBar = defaultSettings.happinessBar;

                    float distanceKM = distance / 1000;
                    float Rounded = (float) ((float)Math.round(distanceKM * 1000d) / 1000d);
                    int coin = (int) (Rounded / 0.30);
                    textView.setText("You received "+ coin +" coins for running "+ Rounded +" kms.");

                    int newBar = (int) ((Rounded * 5)/0.15);

                    fitCoins += coin;
                    happinessBar += newBar;
                    if(happinessBar > 100){
                        happinessBar = 100;
                    }

                    reference.child(userID).child("settings").child("fitCoins").setValue(fitCoins);
                    reference.child(userID).child("settings").child("happinessBar").setValue(happinessBar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        okayBtn.setOnClickListener(v -> {
            popupWindow.dismiss();

            RunRelatorio fragment1 = new RunRelatorio();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.frame_layout, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        dimBehind(popupWindow);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION ))
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    mGoogleMap.setMyLocationEnabled(true);
                }

            } else {
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}