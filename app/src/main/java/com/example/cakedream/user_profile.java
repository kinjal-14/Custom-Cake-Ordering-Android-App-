package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user_profile extends AppCompatActivity {

    private TextView nametv, emailtv, phonetv, passwordtv;
    private EditText nameetv,emailetv, phoneetv, passwordetv, confirmpasswordedit;
    private ImageButton namebtn, namesavebtn, emailbtn,emailsavebtn, phonebtn, phonesavebtn, passwordbtn, passwordsavebtn;
    private LinearLayout confirmpassword;
    private  ImageView backbtn;
    private Button logout;
    private FirebaseFirestore firestore;
    private String uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_screen);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null){
            uid = mFirebaseUser.getUid();
            setdata();
        }else {
            Intent intent = new Intent(getApplicationContext(),login_screen.class);
            startActivity(intent);
        }
        findxmlids();

        namebtn.setOnClickListener(v -> {
            namebtn.setVisibility(View.GONE);
            nametv.setVisibility(View.GONE);
            nameetv.setVisibility(View.VISIBLE);
            namesavebtn.setVisibility(View.VISIBLE);
        });

        emailbtn.setOnClickListener(v -> {
            emailbtn.setVisibility(View.GONE);
            emailtv.setVisibility(View.GONE);
            emailetv.setVisibility(View.VISIBLE);
            emailsavebtn.setVisibility(View.VISIBLE);
        });

        phonebtn.setOnClickListener(v -> {
            phonebtn.setVisibility(View.GONE);
            phonetv.setVisibility(View.GONE);
            phoneetv.setVisibility(View.VISIBLE);
            phonesavebtn.setVisibility(View.VISIBLE);
        });

        passwordbtn.setOnClickListener(v -> {
            passwordbtn.setVisibility(View.GONE);
            passwordtv.setVisibility(View.GONE);
            passwordetv.setVisibility(View.VISIBLE);
            confirmpassword.setVisibility(View.VISIBLE);
        });

        namesavebtn.setOnClickListener(v -> updatename());

        emailsavebtn.setOnClickListener(v -> updateemail());

        phonesavebtn.setOnClickListener(v -> updatephone());

        passwordsavebtn.setOnClickListener(v -> updatepassword());

        backbtn.setOnClickListener(v -> onBackPressed());

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),pre_made_base.class);
            startActivity(intent);
        });

    }

    private void findxmlids() {
        nametv = findViewById(R.id.user_name);
        emailtv = findViewById(R.id.user_email);
        phonetv = findViewById(R.id.user_phone);
        passwordtv = findViewById(R.id.user_password);

        nameetv = findViewById(R.id.edit_user_name);
        emailetv = findViewById(R.id.edit_user_email);
        phoneetv = findViewById(R.id.edit_user_phone);
        passwordetv = findViewById(R.id.edit_user_password);
        confirmpasswordedit = findViewById(R.id.edit_user_confirmpassword);

        confirmpassword = findViewById(R.id.user_confirmpassword);

        namebtn = findViewById(R.id.user_name_edit);
        namesavebtn = findViewById(R.id.user_name_save);

        emailbtn = findViewById(R.id.user_email_edit);
        emailsavebtn = findViewById(R.id.user_email_save);

        phonebtn = findViewById(R.id.user_phone_edit);
        phonesavebtn = findViewById(R.id.user_phone_save);

        passwordbtn = findViewById(R.id.user_password_edit);
        passwordsavebtn = findViewById(R.id.user_password_save);

        logout = findViewById(R.id.logout_button);
        backbtn = findViewById(R.id.back_button);




    }

    private void setdata() {
        firestore.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    String name = Objects.requireNonNull(document.getData().get("name")).toString();
                    String email = Objects.requireNonNull(document.getData().get("email")).toString();
                    String phone = Objects.requireNonNull(document.getData().get("phone")).toString();

                    nametv.setText(name);
                    emailtv.setText(email);
                    phonetv.setText(phone);

                    nameetv.setHint(name);
                    emailetv.setHint(email);
                    phoneetv.setHint(phone);
                } else {
                    Log.d("document Not Found", "No such document");
                }
            } else {
                Log.d("error", "get failed with ", task.getException());
            }
        }).addOnFailureListener(e -> Log.d("error", e.toString()));

    }

    private void updatepassword() {
        String updatedPassword = passwordetv.getText().toString();
        String updatedConfirmPasword = confirmpasswordedit.getText().toString();

        if(validatePassword(updatedPassword)){
            if(updatedPassword.equals(updatedConfirmPasword)){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                user.updatePassword(updatedPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                passwordbtn.setVisibility(View.VISIBLE);
                                passwordtv.setVisibility(View.VISIBLE);
                                passwordetv.setVisibility(View.GONE);
                                confirmpassword.setVisibility(View.GONE);
                                setToast("Password updated successfully!");
                            }
                        });
            }else{
                setToast("Password and confirm password is not match.");
            }
        }else {
            setToast("Use strong password.");
        }

    }


    private boolean validatePassword(String updatedPassword) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(updatedPassword);
        return matcher.matches();
    }

    private void updatephone() {

        String upadatedPhone = phoneetv.getText().toString();
        if(validatePhone(upadatedPhone)){
            firestore.collection("users").document(uid)
                    .update("phone", upadatedPhone)
                    .addOnSuccessListener(unused -> {
                        phonebtn.setVisibility(View.VISIBLE);
                        phonetv.setVisibility(View.VISIBLE);
                        phoneetv.setVisibility(View.GONE);
                        phonesavebtn.setVisibility(View.GONE);
                        setdata();
                        setToast("Phone change Successfully!");
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());
        }else {
            setToast("Phone no is not valid");
        }
    }

    private boolean validatePhone(String upadatedPhone) {
        String regex = "[0-9*#+() -]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(upadatedPhone);
        if(!upadatedPhone.isEmpty()){
            return matcher.matches();
        }
        return false;

    }

    private void updateemail() {
        String updatedEmail = emailetv.getText().toString();

        if(validateEmail(updatedEmail)){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            user.updateEmail(updatedEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emailbtn.setVisibility(View.VISIBLE);
                            emailtv.setVisibility(View.VISIBLE);
                            emailetv.setVisibility(View.GONE);
                            emailsavebtn.setVisibility(View.GONE);
                            setToast("Email Change Successfully!");
                            setdata();
                        }
                    });
        }else {
            setToast("Invalid email id");
        }
    }

    private boolean validateEmail(String updatedEmail) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(updatedEmail);
        if(!updatedEmail.isEmpty()){
            return m.matches();
        }
        return false;
    }


    private void updatename() {

        String upadatedName = nameetv.getText().toString();
        if(!upadatedName.isEmpty()){
            firestore.collection("users").document(uid)
                    .update("name", upadatedName)
                    .addOnSuccessListener(unused -> {
                        namebtn.setVisibility(View.VISIBLE);
                        nametv.setVisibility(View.VISIBLE);
                        nameetv.setVisibility(View.GONE);
                        namesavebtn.setVisibility(View.GONE);
                        setdata();
                        setToast("Name change Successfully!");
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());
        }else {
            setToast("Name Should not be empty");
        }
    }

    public void setToast(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, account_screen.class);
        startActivity(intent);
    }
}
