package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class LojaFragment extends Fragment {

    private int btnClicked;
    private TextView coin_left;
    private TextView fitCoin_left;
    private Button orange;
    private Button purple;
    private Button blue;
    private Button cyan;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    String userID = user.getUid();
    CardView loading;

    public LojaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view =  inflater.inflate(R.layout.fragment_loja, container, false);

        coin_left = view.findViewById(R.id.moedaesquerda);
        fitCoin_left = view.findViewById(R.id.moedadireita);
        Button appleBtn = view.findViewById(R.id.macabtn);
        Button bananaBtn = view.findViewById(R.id.bananaBtn);
        Button cerealBtn = view.findViewById(R.id.cereaisBtn);
        Button saladBtn = view.findViewById(R.id.saladaBtn);
        Button cookieBtn = view.findViewById(R.id.CookieBtn);
        Button juiceBtn = view.findViewById(R.id.sumoBtn);
        Button yogurtBtn = view.findViewById(R.id.IogurteBtn);
        Button muffinBtn = view.findViewById(R.id.MuffinBtn);
        Button iceCreamBtn = view.findViewById(R.id.IceCreamBtn);
        Button pizzaBtn = view.findViewById(R.id.PizzaBtn);
        Button sushiBtnF = view.findViewById(R.id.SushiBtnCorrida);
        Button sushiBtnG = view.findViewById(R.id.SushiBtnJogo);
        Button bologneseBtnF = view.findViewById(R.id.EspargueteBtnCorrida);
        Button bologneseBtnG = view.findViewById(R.id.EspargueteiBtnJogo);
        orange = view.findViewById(R.id.OrangeBtn);
        purple = view.findViewById(R.id.RoxoBtn);
        blue = view.findViewById(R.id.AzulBtn);
        cyan = view.findViewById(R.id.CyanBtn);

        loading = view.findViewById(R.id.loadingShopPage);

        MainActivity.menu.setVisibility(View.INVISIBLE);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int coin = snapshot.child("settings").child("coins").getValue(int.class);
                int fitCoin = snapshot.child("settings").child("fitCoins").getValue(int.class);
                coin_left.setText(String.valueOf(coin));
                fitCoin_left.setText(String.valueOf(fitCoin));

                boolean magoroDarkBlue = snapshot.child("items").child("magoroDarkBlue").getValue(boolean.class);
                boolean magoroLightBlue = snapshot.child("items").child("magoroLightBlue").getValue(boolean.class);
                boolean magoroOrange = snapshot.child("items").child("magoroOrange").getValue(boolean.class);
                boolean magoroPurple = snapshot.child("items").child("magoroPurple").getValue(boolean.class);

                if(magoroDarkBlue){
                    blue.setVisibility(View.GONE);
                }
                if(magoroLightBlue){
                    cyan.setVisibility(View.GONE);
                }
                if(magoroOrange){
                    orange.setVisibility(View.GONE);
                }
                if(magoroPurple){
                    purple.setVisibility(View.GONE);
                }

                loading.setVisibility(View.INVISIBLE);
                MainActivity.menu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        appleBtn.setOnClickListener(v -> {
            btnClicked = 1;
            showPopupWindow(v);
        });

        bananaBtn.setOnClickListener(v -> {
            btnClicked = 2;
            showPopupWindow(v);
        });

        cerealBtn.setOnClickListener(v -> {
            btnClicked = 3;
            showPopupWindow(v);
        });

        saladBtn.setOnClickListener(v -> {
            btnClicked = 4;
            showPopupWindow(v);
        });

        cookieBtn.setOnClickListener(v -> {
            btnClicked = 5;
            showPopupWindow(v);
        });

        juiceBtn.setOnClickListener(v -> {
            btnClicked = 6;
            showPopupWindow(v);
        });

        yogurtBtn.setOnClickListener(v -> {
            btnClicked = 7;
            showPopupWindow(v);
        });


        muffinBtn.setOnClickListener(v -> {
            btnClicked = 8;
            showPopupWindow(v);
        });

        iceCreamBtn.setOnClickListener(v -> {
            btnClicked = 9;
            showPopupWindow(v);
        });

        pizzaBtn.setOnClickListener(v -> {
            btnClicked = 10;
            showPopupWindow(v);
        });

        sushiBtnF.setOnClickListener(v -> {
            btnClicked = 11;
            showPopupWindow(v);
        });

        sushiBtnG.setOnClickListener(v -> {
            btnClicked = 12;
            showPopupWindow(v);
        });

        bologneseBtnF.setOnClickListener(v -> {
            btnClicked = 13;
            showPopupWindow(v);
        });

        bologneseBtnG.setOnClickListener(v -> {
            btnClicked = 14;
            showPopupWindow(v);
        });

        orange.setOnClickListener(v -> {
            btnClicked = 15;
            showPopupWindow(v);
        });

        purple.setOnClickListener(v -> {
            btnClicked = 16;
            showPopupWindow(v);
        });

        blue.setOnClickListener(v -> {
            btnClicked = 17;
            showPopupWindow(v);
        });

        cyan.setOnClickListener(v -> {
            btnClicked = 18;
            showPopupWindow(v);
        });


        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void showPopupWindow(final View view) {

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_window, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button buttonBuy = popupView.findViewById(R.id.messageBuyButton);
        Button buttonCancel = popupView.findViewById(R.id.messageCancelButton);
        TextView buyText = popupView.findViewById(R.id.buyingItem);
        String text;

        switch(btnClicked){
            case 1:
            case 2:
            case 5:
                text = "This will lower your magoro hunger by 3%";
                buyText.setText(text);
                break;
            case 3:
            case 8:
                text = "This will lower your magoro hunger by 8%";
                buyText.setText(text);
                break;
            case 4:
            case 9:
                text = "This will lower your magoro hunger by 10%";
                buyText.setText(text);
                break;
            case 6:
                text = "This will lower your magoro hunger by 1%";
                buyText.setText(text);
                break;
            case 7:
                text = "This will lower your magoro hunger by 13%";
                buyText.setText(text);
                break;
            case 10:
                text = "This will lower your magoro hunger by 15%";
                buyText.setText(text);
                break;
            case 11:
            case 12:
                text = "This will lower your magoro hunger by 25%";
                buyText.setText(text);
                break;
            case 13:
            case 14:
                text = "This will lower your magoro hunger by 40%";
                buyText.setText(text);
                break;
            case 15:
                text = "The awesome Kitsy";
                buyText.setText(text);
                break;
            case 16:
                text = "The awesome Lotto";
                buyText.setText(text);
                break;
            case 17:
                text = "The awesome Rena";
                buyText.setText(text);
                break;
            case 18:
                text = "The awesome Jenga";
                buyText.setText(text);
                break;

        }

        buttonBuy.setOnClickListener(v -> {

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    int coin = snapshot.child("settings").child("coins").getValue(int.class);
                    int fitCoin = snapshot.child("settings").child("fitCoins").getValue(int.class);
                    int apple = snapshot.child("items").child("apple").getValue(int.class);
                    int banana = snapshot.child("items").child("banana").getValue(int.class);
                    int bolognese = snapshot.child("items").child("bolognese").getValue(int.class);
                    int cereals = snapshot.child("items").child("cereals").getValue(int.class);
                    int cookie = snapshot.child("items").child("cookie").getValue(int.class);
                    int iceCream = snapshot.child("items").child("iceCream").getValue(int.class);
                    int juice = snapshot.child("items").child("juice").getValue(int.class);
                    int muffin = snapshot.child("items").child("muffin").getValue(int.class);
                    int pizza = snapshot.child("items").child("pizza").getValue(int.class);
                    int salad = snapshot.child("items").child("salad").getValue(int.class);
                    int sushi = snapshot.child("items").child("sushi").getValue(int.class);
                    int yogurt = snapshot.child("items").child("yogurt").getValue(int.class);

                    switch(btnClicked){

                        case 1:
                            fitCoin -= 2;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                apple += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("apple").setValue(apple);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 2:
                            fitCoin -= 2;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                banana += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("banana").setValue(banana);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 3:
                            fitCoin -= 5;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                cereals += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("cereals").setValue(cereals);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 4:
                            fitCoin -= 6;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                salad += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("salad").setValue(salad);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 5:
                            coin -= 15;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                cookie += 1;
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("cookie").setValue(cookie);
                                coin_left.setText(String.valueOf(coin));
                            }
                            break;

                        case 6:
                            fitCoin -= 1;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                juice += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("juice").setValue(juice);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 7:
                            fitCoin -= 8;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                yogurt += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("yogurt").setValue(yogurt);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 8:
                            coin -= 20;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                muffin += 1;
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("muffin").setValue(muffin);
                                coin_left.setText(String.valueOf(coin));
                            }
                            break;

                        case 9:
                            coin -= 30;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                iceCream += 1;
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("iceCream").setValue(iceCream);
                                coin_left.setText(String.valueOf(coin));
                            }
                            break;

                        case 10:
                            coin -= 50;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                pizza += 1;
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("pizza").setValue(pizza);
                                coin_left.setText(String.valueOf(coin));
                            }
                            break;

                        case 11:
                            fitCoin -= 15;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                sushi += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("sushi").setValue(sushi);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 12:
                            coin -= 70;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                sushi += 1;
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("sushi").setValue(sushi);
                                coin_left.setText(String.valueOf(coin));
                            }
                            break;

                        case 13:
                            fitCoin -= 20;
                            if(fitCoin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                bolognese += 1;
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("bolognese").setValue(bolognese);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                            }
                            break;

                        case 14:
                            coin -= 100;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                bolognese += 1;
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("bolognese").setValue(bolognese);
                                coin_left.setText(String.valueOf(coin));
                            }
                            break;

                        case 15:
                            fitCoin -= 200;
                            if(fitCoin < 0){
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("magoroOrange").setValue(true);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                                orange.setVisibility(View.GONE);
                            }
                            break;

                        case 16:
                            coin -= 750;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("magoroPurple").setValue(true);
                                coin_left.setText(String.valueOf(coin));
                                purple.setVisibility(View.GONE);
                            }
                            break;

                        case 17:
                            fitCoin -= 100;
                            if(fitCoin < 0){
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                reference.child(userID).child("settings").child("fitCoins").setValue(fitCoin);
                                reference.child(userID).child("items").child("magoroDarkBlue").setValue(true);
                                fitCoin_left.setText(String.valueOf(fitCoin));
                                blue.setVisibility(View.GONE);
                            }
                            break;

                        case 18:
                            coin -= 1500;
                            if(coin < 0) {
                                popupWindow.dismiss();
                                showPopupWindowCancel(v);
                            }
                            else{
                                reference.child(userID).child("settings").child("coins").setValue(coin);
                                reference.child(userID).child("items").child("magoroLightBlue").setValue(true);
                                coin_left.setText(String.valueOf(coin));
                                cyan.setVisibility(View.GONE);
                            }
                            break;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            popupWindow.dismiss();
        });

        dimBehind(popupWindow);

        buttonCancel.setOnClickListener(v -> popupWindow.dismiss());

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
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

    public void showPopupWindowCancel(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_no_money, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button okayBtn = popupView.findViewById(R.id.messageOKBtn);

        okayBtn.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }
}