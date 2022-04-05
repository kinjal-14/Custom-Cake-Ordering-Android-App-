package com.example.cakedream;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class login_screen extends AppCompatActivity {


    private TextInputEditText email, password;
    public Button loginBtn, signUpBtn;
    private TextView login_valid;
    String emailPattern= "[a-zA-Z0-9._%+-]+@[A-Za-z.-]+\\.+[a-z]+";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn1);
        signUpBtn = findViewById(R.id.signup_btn1);
        login_valid = findViewById(R.id.login_valid);

        progressDialog=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        loginBtn.setOnClickListener(v -> PerLogAuth());

        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(login_screen.this, signup_screen.class);
            startActivity(intent);
        });

    }

        private void PerLogAuth() {
            String Email= Objects.requireNonNull(email.getText()).toString();
            String Password= Objects.requireNonNull(password.getText().toString());

            if (!Email.matches(emailPattern)){
                email.setError("enter valid email address");
                email.requestFocus();

            }else if (Password.isEmpty()||password.length()<8)
            {
                password.setError("enter proper password");
                password.requestFocus();
            }
            else{
                progressDialog.setMessage("Please Wait While Login...");
                progressDialog.setTitle("Login");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(login_screen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(login_screen.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    private void sendUserToNextActivity() {
        Intent intent= new Intent(login_screen.this,pre_made_base.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
