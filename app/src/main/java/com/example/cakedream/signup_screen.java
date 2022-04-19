package com.example.cakedream;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup_screen extends AppCompatActivity {
    private TextInputEditText name, email, phoneno, password, confirmpassword;
    private AppCompatButton signUpBtn;
    private TextView loginBtn;
    private ImageView googlesignin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String uId;
    ProgressDialog progressDialog;
    private static final int RC_SIGN_IN = 1000;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup_screen);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
              .requestIdToken(getString(R.string.default_web_client_id1))
              .requestEmail()
              .build();

         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findId();

        signUpBtn.setOnClickListener(view -> Validate());

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), login_screen.class);
            startActivity(intent);
        });
        googlesignin.setOnClickListener(view -> signIn());
    }

    @SuppressLint("CutPasteId")
    private  void findId(){
        name = findViewById(R.id.fullname);
        phoneno = findViewById(R.id.phoneno);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        googlesignin = findViewById(R.id.googlesignin);

        signUpBtn = findViewById(R.id.signup_btn2);
        loginBtn = findViewById(R.id.login_btn2);

        progressDialog=new ProgressDialog(this);

    }

    private void Validate(){

        String nameText = (name.getText()).toString();
        String emailText = (email.getText()).toString();
        String phoneText = (phoneno.getText()).toString();
        String passwordText = (password.getText()).toString();
        String confirmPasswordText = (confirmpassword.getText()).toString();


        if (!nameText.isEmpty()){
            if(isValidEmailAddress(emailText)){
                if(isMobileNumberValid(phoneText)){
                    if(isValidPassword(passwordText)){
                        if(passwordText.equals(confirmPasswordText)){
                            creatUser(nameText, emailText, phoneText, passwordText);
                        }
                        else {
                            confirmpassword.setError("Password and confirm Password is not match.");
                            confirmpassword.requestFocus();
                        }
                    }
                    else {
                        password.setError("Password is not valid.");
                        password.requestFocus();
                    }
                }
                else {
                    phoneno.setError("Invalid phone number.");
                    phoneno.requestFocus();
                }
            }
            else {
                email.setError("Invalid email.");
                email.requestFocus();
            }
        } else {
            name.setError("Name must not be empty.");
        }
        progressDialog.setMessage("Please Wait While Registration...");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        }
    public boolean isValidEmailAddress(String emailID) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(emailID);
        if(!Objects.requireNonNull(email.getText()).toString().isEmpty()){
            return m.matches();
        }
        return false;
    }

    public boolean isMobileNumberValid(String phoneNumber) {
        String regex = "[0-9*#+() -]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!Objects.requireNonNull(phoneno.getText()).toString().isEmpty()){
            return matcher.matches();
        }
        return false;
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void setToast(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void creatUser(String name, String email, String phone, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
            if(mFirebaseUser != null) {
                uId = mFirebaseUser.getUid();
                DocumentReference documentReference = firestore.collection("users").document(uId);

                Map<String, String> user = new HashMap<>();
                user.put("uid", uId);
                user.put("name", name);
                user.put("email", email);
                user.put("phone", phone);
                user.put("password", password);

                documentReference.set(user).addOnSuccessListener(unused -> {
                    progressDialog.dismiss();
                    Toast.makeText(signup_screen.this, "User Created", Toast.LENGTH_SHORT).show();
                    sendUserToNextActivity();
                }).addOnFailureListener(e -> setToast(e.toString()));
            }
        }).addOnFailureListener(e -> setToast(e.toString()));
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken(), account.getEmail(), account.getGivenName());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken, String email, String name) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uId = user.getUid();
                            DocumentReference documentReference = firestore.collection("users").document(uId);

                            Map<String, String> userGoogle = new HashMap<>();
                            userGoogle.put("uId", uId);
                            userGoogle.put("name", name);
                            userGoogle.put("email", email);
                            userGoogle.put("phone", "");
                            userGoogle.put("password", "");
                            documentReference.set(userGoogle).addOnSuccessListener(unused -> {
                                Intent intent = new Intent(getBaseContext(), pre_made_base.class);
                                startActivity(intent);
                            }).addOnFailureListener(e -> setToast(e.toString()));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }
    private void sendUserToNextActivity() {
        Intent intent= new Intent(signup_screen.this,pre_made_base.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
