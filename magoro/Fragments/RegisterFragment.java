package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magoro.DBobjects.Achievements;
import com.example.magoro.DBobjects.DefaultSettings;
import com.example.magoro.DBobjects.GamesStatus;
import com.example.magoro.DBobjects.Items;
import com.example.magoro.DBobjects.RunningInfo;
import com.example.magoro.DBobjects.User;
import com.example.magoro.DBobjects.UserInfo;
import com.example.magoro.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText user, mail, pass;
    private ProgressBar progressBar;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.layout_register, container, false);

        mAuth = FirebaseAuth.getInstance();

        TextView switchToRegister = view.findViewById(R.id.switchToLogin);
        user = view.findViewById(R.id.username);
        mail = view.findViewById(R.id.mail);
        pass = view.findViewById(R.id.password);
        Button register = view.findViewById(R.id.registerBtn);
        progressBar = view.findViewById(R.id.registerLoad);

        progressBar.setVisibility(View.GONE);

        switchToRegister.setOnClickListener(v -> {

            LoginFragment fragment1 = new LoginFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.login_main, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();

        });

        register.setOnClickListener(v -> SignUp());

        return view;
    }

    private void SignUp() {

        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String name = user.getText().toString().trim();

        if(name.isEmpty()){
            user.setError("Username is required!");
            user.requestFocus();
            return;
        }

        if(email.isEmpty()){
            mail.setError("Email is required!");
            mail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Please enter a valid email!");
            mail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            pass.setError("Password is required!");
            pass.requestFocus();
            return;
        }

        if(password.length() < 6){
            pass.setError("Password length should be at least 6 characters!");
            pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                User user = new User(new UserInfo(email, name, 0, 0, 0),
                        new DefaultSettings(0, 20, 5, 100, 100,
                        0, 5000, 0, 5000, 0),
                        new RunningInfo(0, " ", 0, 0, " "),
                        new GamesStatus(0, 0, 0, 0),
                        new Items(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                0, "red", true, false, false, false,
                                false, false, false, 1, 1,
                                1, true, false, false, true, false,
                                true, false),
                        new Achievements(0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0, 0,
                                0, false, false, false, false,
                                false, false, false, false,
                                false, false, false, false,
                                false, false, false, false,
                                false, false));

                FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(task1 -> {

                            if(task1.isSuccessful()){
                                Toast.makeText(getActivity(), "User has been registered successfully", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                mAuth.getCurrentUser().sendEmailVerification();
                                showPopupWindowRegister(getView());
                            }
                            else{
                                Toast.makeText(getActivity(), "Failed to register!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        });
            }
            else{
                Toast.makeText(getActivity(), "Failed to register! User may already been registered!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
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

    public void showPopupWindowRegister(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_register, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button verify = popupView.findViewById(R.id.verifyRegister);

        verify.setOnClickListener(v -> {
            popupWindow.dismiss();
            LoginFragment fragment1 = new LoginFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.login_main, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        dimBehind(popupWindow);
    }
}
