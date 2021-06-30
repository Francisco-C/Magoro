package com.example.magoro.Activities;

import  androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.magoro.Fragments.GameFragment;
import com.example.magoro.Fragments.HomeFragment;
import com.example.magoro.Fragments.LojaFragment;
import com.example.magoro.R;

import com.example.magoro.Fragments.RunFragment;
import com.example.magoro.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity{

    public static BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        menu = findViewById(R.id.bottom_navigation);
        menu.getMenu().findItem(R.id.Home).setChecked(true);

        new CheckInternet(3000, 1000).start();

        menu = findViewById(R.id.bottom_navigation);

        menu.setOnNavigationItemSelectedListener(item -> {

           Fragment selectedFragment = null;

           switch (item.getItemId()){
               case R.id.Run:
                   selectedFragment = new RunFragment();
                   break;
               case R.id.Settings:
                   selectedFragment = new SettingsFragment();
                   break;
               case R.id.Home:
                   selectedFragment = new HomeFragment();
                   break;
               case R.id.Games:
                   selectedFragment = new GameFragment();
                   break;
               case R.id.Shop:
                   selectedFragment = new LojaFragment();
                   break;
           }

           getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
           return true;
       });
    }

    public void MoveToShop(){
        menu.getMenu().findItem(R.id.Shop).setChecked(true);
    }

    public void MoveToSettings(){
        menu.getMenu().findItem(R.id.Settings).setChecked(true);
    }

    public class CheckInternet extends CountDownTimer{

        public CheckInternet(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {

            boolean internet = isNetworkAvailable();
            if(!internet){
                View v = getWindow().getDecorView().getRootView();
                showPopupWindowInternet(v);
            }
            else{
                new CheckInternet(3000, 1000).start();
            }
        }
    }

    private boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void showPopupWindowInternet(final View view) {

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_internet, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button tryAgain = popupView.findViewById(R.id.InternetBtn);

        tryAgain.setOnClickListener(v -> {
            boolean internet = isNetworkAvailable();
            popupWindow.dismiss();
            if (!internet) {
                View view1 = getWindow().getDecorView().getRootView();
                showPopupWindowInternet(view1);
            }
            else{
                new CheckInternet(3000, 1000).start();
            }
        });

        dimBehind(popupWindow);
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
}