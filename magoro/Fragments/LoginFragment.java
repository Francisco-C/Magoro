package com.example.magoro.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.magoro.Activities.MainActivity;
import com.example.magoro.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private EditText LogEmail;
    private EditText LogPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.layout_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getActivity(), MainActivity.class));
        }

        TextView switchToRegister = view.findViewById(R.id.switchToRegister);
        LogEmail = view.findViewById(R.id.editTextEmail);
        LogPassword = view.findViewById(R.id.editTextPassword);
        Button logBtn = view.findViewById(R.id.LoginButton);
        TextView forgotPass = view.findViewById(R.id.forgotPass);
        progressBar = view.findViewById(R.id.loginLoad);
        progressBar.setVisibility(View.GONE);

        switchToRegister.setOnClickListener(v -> {

            RegisterFragment fragment1 = new RegisterFragment();
            FragmentManager fragmentManager1 = getFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.login_main, fragment1);
            fragmentTransaction1.addToBackStack(null);
            fragmentTransaction1.commit();
        });

        forgotPass.setOnClickListener(this::showPopupWindowForgotPass);

        logBtn.setOnClickListener(v -> SignIn());

        return view;
    }

    private void SignIn() {

        String mail = LogEmail.getText().toString().trim();
        String pass = LogPassword.getText().toString().trim();

        if(mail.isEmpty()){
            LogEmail.setError("Email is required!");
            LogEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            LogEmail.setError("Please enter a valid email!");
            LogEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            LogPassword.setError("Password is required");
            LogPassword.requestFocus();
            return;
        }

        if(pass.length() < 6){
            LogPassword.setError("Password length should be at least 6 characters!");
            LogPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(task -> {

            if(task.isSuccessful()){

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user.isEmailVerified()){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
                else{
                    user.sendEmailVerification();
                    showPopupWindowVerify(getView());
                    progressBar.setVisibility(View.GONE);
                }
            }
            else{
                Toast.makeText(getActivity(), "Failed to login, please check your credentials!", Toast.LENGTH_LONG).show();
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

    public void showPopupWindowVerify(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_verification, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button okay = popupView.findViewById(R.id.verificationOkay);

        okay.setOnClickListener(v -> popupWindow.dismiss());

        dimBehind(popupWindow);
    }

    public void showPopupWindowForgotPass(final View view){

        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up_forgotpass, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ProgressBar progressBar = popupView.findViewById(R.id.resetLoad);
        progressBar.setVisibility(View.GONE);
        EditText mail = popupView.findViewById(R.id.writeMail);
        Button sendMail = popupView.findViewById(R.id.sendMail);

        sendMail.setOnClickListener(v -> {
            String email = mail.getText().toString().trim();

            if(email.isEmpty()){
                mail.setError("Email is required!");
                mail.requestFocus();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                mail.setError("Please provide a valid email!");
                mail.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Check your email to reset the password!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();
                }
            });
            progressBar.setVisibility(View.GONE);
            popupWindow.dismiss();
        });

        dimBehind(popupWindow);
    }
}
