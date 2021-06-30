package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class FoodFragment extends Fragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    String userID = user.getUid();
    private int food30, food250, food500, food1000;
    private ImageView magoro;
    private ConstraintLayout layout;
    private AnimationDrawable giveFood;
    private CardView loading;

    public FoodFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View view =  inflater.inflate(R.layout.fragment_food, container, false);

        Button openFoodMenu = view.findViewById(R.id.foodBtn);
        magoro = view.findViewById(R.id.MagoroFood);
        layout = view.findViewById(R.id.defaultKit);
        loading = view.findViewById(R.id.loadingFoodPage);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String defaultMagoro = snapshot.child("items").child("defaultMagoro").getValue(String.class);
                int defaultKitchen = snapshot.child("items").child("defaultKitchen").getValue(int.class);
                int sleepState = snapshot.child("settings").child("sleepState").getValue(int.class);

                if(sleepState == 1){
                    magoro.setVisibility(View.GONE);
                }
                else{
                    if(defaultKitchen == 1){
                        layout.setBackgroundResource(R.drawable.kit5);
                    }else{
                        layout.setBackgroundResource(R.drawable.kit010);
                    }

                    switch(defaultMagoro){
                        case "red":
                            magoro.setImageResource(R.drawable.eat1red);
                            magoro.setImageResource(R.drawable.red_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                        case "orange":
                            magoro.setImageResource(R.drawable.eat1orange);
                            magoro.setImageResource(R.drawable.orange_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                        case "blue":
                            magoro.setImageResource(R.drawable.eat1blue);
                            magoro.setImageResource(R.drawable.blue_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                        case "purple":
                            magoro.setImageResource(R.drawable.eat1roxo);
                            magoro.setImageResource(R.drawable.roxo_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                        case "cyan":
                            magoro.setImageResource(R.drawable.eat1cian);
                            magoro.setImageResource(R.drawable.cian_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                        case "green":
                            magoro.setImageResource(R.drawable.eat1green);
                            magoro.setImageResource(R.drawable.green_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                        case "color":
                            magoro.setImageResource(R.drawable.eat1color);
                            magoro.setImageResource(R.drawable.color_eat);
                            giveFood = (AnimationDrawable) magoro.getDrawable();
                            break;
                    }
                }

                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        openFoodMenu.setOnClickListener(this::showPopupWindowOpenFoodMenu);

        return view;
    }

    public void showPopupWindowOpenFoodMenu(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_items, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ImageButton close = popupView.findViewById(R.id.closeFoodMenuBtn);
        
        Button appleX1 = popupView.findViewById(R.id.appleBtn1);
        Button appleX5 = popupView.findViewById(R.id.appleBtn5);
        Button bananaX1 = popupView.findViewById(R.id.bananaBtn1);
        Button bananaX5 = popupView.findViewById(R.id.bananaBtn5);
        Button juiceX1 = popupView.findViewById(R.id.JuiceBtn1);
        Button juiceX5 = popupView.findViewById(R.id.JuiceBtn5);
        Button cerealX1 = popupView.findViewById(R.id.cerealsBtn1);
        Button cerealX5 = popupView.findViewById(R.id.cerealsBtn5);
        Button saladX1 = popupView.findViewById(R.id.saladBtn1);
        Button saladX5 = popupView.findViewById(R.id.saladBtn5);
        Button iceCreamX1 = popupView.findViewById(R.id.iceCreamBtn1);
        Button iceCreamX5 = popupView.findViewById(R.id.iceCreamBtn5);
        Button muffinX1 = popupView.findViewById(R.id.muffinBtn1);
        Button muffinX5 = popupView.findViewById(R.id.muffinBtn5);
        Button cookieX1 = popupView.findViewById(R.id.cookieBtn1);
        Button cookieX5 = popupView.findViewById(R.id.cookieBtn5);
        Button yogurtX1 = popupView.findViewById(R.id.yogurtBtn1);
        Button yogurtX5 = popupView.findViewById(R.id.yogurtBtn5);
        Button pizzaX1 = popupView.findViewById(R.id.pizzaBtn1);
        Button pizzaX5 = popupView.findViewById(R.id.pizzaBtn5);
        Button sushiX1 = popupView.findViewById(R.id.sushiBtn1);
        Button sushiX5 = popupView.findViewById(R.id.sushiBtn5);
        Button bologneseX1 = popupView.findViewById(R.id.bologneseBtn1);
        Button bologneseX5 = popupView.findViewById(R.id.bologneseBtn5);

        TextView appleNum = popupView.findViewById(R.id.appleNum);
        TextView juiceNum = popupView.findViewById(R.id.juiceNum);
        TextView bananaNum = popupView.findViewById(R.id.bananaNum);
        TextView cerealNum = popupView.findViewById(R.id.cerealNum);
        TextView saladNum = popupView.findViewById(R.id.saladNum);
        TextView cookieNum = popupView.findViewById(R.id.cookieNum);
        TextView muffinNum = popupView.findViewById(R.id.muffinNum);
        TextView iceCreamNum = popupView.findViewById(R.id.iceCreamNum);
        TextView pizzaNum = popupView.findViewById(R.id.pizzaNum);
        TextView yogurtNum = popupView.findViewById(R.id.yogurtNum);
        TextView sushiNum = popupView.findViewById(R.id.sushiNum);
        TextView bologneseNum = popupView.findViewById(R.id.bologneseNum);

        appleNum.bringToFront();
        juiceNum.bringToFront();
        bananaNum.bringToFront();
        cerealNum.bringToFront();
        saladNum.bringToFront();
        cookieNum.bringToFront();
        muffinNum.bringToFront();
        iceCreamNum.bringToFront();
        pizzaNum.bringToFront();
        yogurtNum.bringToFront();
        sushiNum.bringToFront();
        bologneseNum.bringToFront();
        
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int hunger = snapshot.child("settings").child("hungerBar").getValue(int.class);

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

                food30 = snapshot.child("achievements").child("food30").getValue(int.class);
                food250 = snapshot.child("achievements").child("food250").getValue(int.class);
                food500 = snapshot.child("achievements").child("food500").getValue(int.class);
                food1000 = snapshot.child("achievements").child("food1000").getValue(int.class);

                appleNum.setText(String.valueOf(apple));
                bananaNum.setText(String.valueOf(banana));
                bologneseNum.setText(String.valueOf(bolognese));
                cerealNum.setText(String.valueOf(cereals));
                cookieNum.setText(String.valueOf(cookie));
                iceCreamNum.setText(String.valueOf(iceCream));
                juiceNum.setText(String.valueOf(juice));
                muffinNum.setText(String.valueOf(muffin));
                pizzaNum.setText(String.valueOf(pizza));
                saladNum.setText(String.valueOf(salad));
                sushiNum.setText(String.valueOf(sushi));
                yogurtNum.setText(String.valueOf(yogurt));

                appleX1.setOnClickListener(v -> eating(apple, hunger, "apple", v, popupWindow));
                bananaX1.setOnClickListener(v -> eating(banana, hunger, "banana", v, popupWindow));
                bologneseX1.setOnClickListener(v -> eating(bolognese, hunger, "bolognese", v, popupWindow));
                cerealX1.setOnClickListener(v -> eating(cereals, hunger, "cereals", v, popupWindow));
                cookieX1.setOnClickListener(v -> eating(cookie, hunger, "cookie", v, popupWindow));
                iceCreamX1.setOnClickListener(v -> eating(iceCream, hunger, "iceCream", v, popupWindow));
                juiceX1.setOnClickListener(v -> eating(juice, hunger, "juice", v, popupWindow));
                muffinX1.setOnClickListener(v -> eating(muffin, hunger, "muffin", v, popupWindow));
                pizzaX1.setOnClickListener(v -> eating(pizza, hunger, "pizza", v, popupWindow));
                saladX1.setOnClickListener(v -> eating(salad, hunger, "salad", v, popupWindow));
                sushiX1.setOnClickListener(v -> eating(sushi, hunger, "sushi", v, popupWindow));
                yogurtX1.setOnClickListener(v -> eating(yogurt, hunger, "yogurt", v, popupWindow));

                appleX5.setOnClickListener(v -> eatingMore(apple, hunger, "apple", v, popupWindow));
                bananaX5.setOnClickListener(v -> eatingMore(banana, hunger, "banana", v, popupWindow));
                bologneseX5.setOnClickListener(v -> eatingMore(bolognese, hunger, "bolognese", v, popupWindow));
                cerealX5.setOnClickListener(v -> eatingMore(cereals, hunger, "cereals", v, popupWindow));
                cookieX5.setOnClickListener(v -> eatingMore(cookie, hunger, "cookie", v, popupWindow));
                iceCreamX5.setOnClickListener(v -> eatingMore(iceCream, hunger, "iceCream", v, popupWindow));
                juiceX5.setOnClickListener(v -> eatingMore(juice, hunger, "juice", v, popupWindow));
                muffinX5.setOnClickListener(v -> eatingMore(muffin, hunger, "muffin", v, popupWindow));
                pizzaX5.setOnClickListener(v -> eatingMore(pizza, hunger, "pizza", v, popupWindow));
                saladX5.setOnClickListener(v -> eatingMore(salad, hunger, "salad", v, popupWindow));
                sushiX5.setOnClickListener(v -> eatingMore(sushi, hunger, "sushi", v, popupWindow));
                yogurtX5.setOnClickListener(v -> eatingMore(yogurt, hunger, "yogurt", v, popupWindow));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        close.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }

    public void showPopupWindowToShop(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_comida, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button storeBtn = popupView.findViewById(R.id.messageStoreBtn);

        Button okayBtn = popupView.findViewById(R.id.messageOkayBtn);

        storeBtn.setOnClickListener(v -> {
            popupWindow.dismiss();
            Fragment frag = new LojaFragment();
            FragmentManager fm= getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.frame_layout,frag).commit();
            ((MainActivity)getActivity()).MoveToShop();
        });

        okayBtn.setOnClickListener(v -> popupWindow.dismiss());

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

    public void eating(int fruitQtd, int decrease, String fruit, View view, PopupWindow popupWindow){

        int hunger = 0;
        giveFood.stop();
        switch(fruit){

            case "apple":
            case "banana":
            case "cookie":
                hunger = 3;
                break;
            case "bolognese":
                hunger = 40;
                break;
            case "cereals":
            case "muffin":
                hunger = 8;
                break;
            case "iceCream":
            case "salad":
                hunger = 10;
                break;
            case "juice":
                hunger = 1;
                break;
            case "pizza":
                hunger = 15;
                break;
            case "sushi":
                hunger = 25;
                break;
            case "yogurt":
                hunger = 13;
                break;
        }

        if(fruitQtd < 1){
            popupWindow.dismiss();
            showPopupWindowToShop(view);
        }
        else{
            fruitQtd -= 1;
            decrease += hunger;
            if(decrease > 100){
                decrease = 100;
            }

            food30 += 1;
            food250 += 1;
            food500 += 1;
            food1000 += 1;
            reference.child(userID).child("achievements").child("food30").setValue(food30);
            reference.child(userID).child("achievements").child("food250").setValue(food250);
            reference.child(userID).child("achievements").child("food500").setValue(food500);
            reference.child(userID).child("achievements").child("food1000").setValue(food1000);

            reference.child(userID).child("items").child(fruit).setValue(fruitQtd);
            assert getParentFragment() != null;
            ((HomeFragment) getParentFragment()).hungerBar.setProgress(decrease);
            reference.child(userID).child("settings").child("hungerBar").setValue(decrease);
            popupWindow.dismiss();
            giveFood.start();
        }
    }

    public void eatingMore(int fruitQtd, int decrease, String fruit, View view, PopupWindow popupWindow){

        int hunger = 0;
        int x3x2 = 0;
        int achieve = 0;
        giveFood.stop();
        switch(fruit){

            case "apple":
            case "banana":
            case "cookie":
                hunger = 15;
                break;
            case "bolognese":
                hunger = 80;
                x3x2 = 2;
                break;
            case "cereals":
            case "muffin":
                hunger = 40;
                break;
            case "iceCream":
            case "salad":
                hunger = 50;
                break;
            case "juice":
                hunger = 5;
                break;
            case "pizza":
                hunger = 75;
                break;
            case "sushi":
                hunger = 75;
                x3x2 = 3;
                break;
            case "yogurt":
                hunger = 65;
                break;
        }

        if(fruitQtd < 5 && x3x2 == 0 || fruitQtd < 3 && x3x2 == 3 || fruitQtd < 2){
            popupWindow.dismiss();
            showPopupWindowToShop(view);
        }
        else{
            switch (x3x2){
                case 0:
                    fruitQtd -= 5;
                    achieve = 5;
                    break;
                case 2:
                    fruitQtd -= 2;
                    achieve = 2;
                    break;
                case 3:
                    fruitQtd -= 3;
                    achieve = 3;
                    break;
            }

            decrease += hunger;
            if(decrease > 100){
                decrease = 100;
            }

            food30 += achieve;
            food250 += achieve;
            food500 += achieve;
            food1000 += achieve;
            reference.child(userID).child("achievements").child("food30").setValue(food30);
            reference.child(userID).child("achievements").child("food250").setValue(food250);
            reference.child(userID).child("achievements").child("food500").setValue(food500);
            reference.child(userID).child("achievements").child("food1000").setValue(food1000);

            reference.child(userID).child("items").child(fruit).setValue(fruitQtd);
            assert getParentFragment() != null;
            ((HomeFragment) getParentFragment()).hungerBar.setProgress(decrease);
            reference.child(userID).child("settings").child("hungerBar").setValue(decrease);
            popupWindow.dismiss();
            giveFood.start();
        }
    }
}