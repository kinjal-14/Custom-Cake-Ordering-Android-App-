package com.example.cakedream;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class shipping extends AppCompatActivity {

    TextInputEditText address1,address2,postal_code,city,state,name;
    TextView shiping_subtotal,shiping_discount,shiping_tax,shiping_total,del1;
    EditText first_name,last_name;
    String FirstName,LastName, Address1, Address2, PostalCode, City, State;
    ArrayList<cart_model> cartData = new ArrayList<>();
    String date, day, documentId;
    int hour, minute;
    ImageView back_button;
    Button order_button;
    FirebaseFirestore firestore;
    String uid;
    double subtotal;
    double discountValue = 0.0;
    double del = 0.0;
    double totalValue = 0.0;

    private double taxValue = 15.0;

    private String SECRET_KEY = "sk_test_51Knu8cIlBNNc1Yb9j41PgGWWMfjIA1Xo7RUAMdHtNCaa9KrVH86419lgPgU21dMuaRUgoh06Ftzs29Gmb4OeEIqg00ZIOMYfAv";
    private String PUBLISH_KEY = "pk_test_51Knu8cIlBNNc1Yb9sl5RmZ18DKmHVBtS6QQavLkfLXZKGCXPRfcFvNCUrPeVVqgj53YfXDdYClGAjRaOUMEkQq0T00DxYxksFW";
    PaymentSheet paymentSheet;

    String customerID;
    String EphericalKey;
    String ClientSecret;
    String grandtotal;

    String userphone,useremail;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.shipping_screen);
        Intent intent = getIntent();
        subtotal = (double) intent.getExtras().get("SubTotal")*100;
        Log.d("subtotal", String.valueOf(subtotal));
        String total = String.valueOf(subtotal);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            uid = mFirebaseUser.getUid();
            getUserDetail();
        } else {
            uid = null;
        }
        xmlid();
        totalcalculation();



        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(shipping.this,cart_screen.class);
                startActivity(intent);
            }
        });

        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fetchAndValidate()) {
                    paymentFlow();

                }

            }
        });
        PaymentConfiguration.init(shipping.this,PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this,paymentSheetResult -> {
            if (paymentSheetResult instanceof PaymentSheetResult.Completed){
                Toast.makeText(this,"payment sucess", Toast.LENGTH_SHORT).show();
                submitOrder();

            }

        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            customerID = object.getString("id");
                            Toast.makeText(shipping.this,customerID,Toast.LENGTH_SHORT).show();

                            getEmphericalKey(customerID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(shipping.this);
        requestQueue.add(stringRequest);

    }

    private void getUserDetail() {
        DocumentReference docRef = firestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    userData data = document.toObject(userData.class);
                    assert data != null;
                    userphone = data.phone;
                    useremail = data.email;
                } else {
                    Log.d("document Not Found", "No such document");
                }
            } else {
                Log.d("error", "get failed with ", task.getException());
            }
        }).addOnFailureListener(e -> Log.d("error", e.toString()));
    }

    private void getEmphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            EphericalKey = object.getString("id");
                            Toast.makeText(shipping.this,EphericalKey,Toast.LENGTH_SHORT).show();


                            getClientSecret(customerID,EphericalKey);
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2020-08-27");
                return header;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(shipping.this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String ephericalKey) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            Toast.makeText(shipping.this,ClientSecret,Toast.LENGTH_SHORT).show();
                            



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount","124"+"00");
                params.put("currency","cad");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(shipping.this);
        requestQueue.add(stringRequest);
    }

    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(
                ClientSecret, new PaymentSheet.Configuration("Cake Dream Bakery"
                ,new PaymentSheet.CustomerConfiguration(
                        customerID,
                        EphericalKey
                ))
        );
    }


    private void totalcalculation() {
        Random r = new Random();
        int random = r.nextInt(30);
        discountValue = ((random * 1.0) * subtotal) / 100;
        taxValue = (taxValue * subtotal) / 100;
        if (subtotal/100 < 100){
            del = 1000.0;
        }else {
            del = 0.0;
        }
        totalValue = (subtotal + taxValue + del) - discountValue;

        double subValue = Math.round(subtotal)/ 100 ;
        double disValue = Math.round(discountValue) / 100;
        double tValue = Math.round(taxValue) / 100;
        double totValue = Math.round(totalValue) / 100;
        double del2 = Math.round(del)/100;
        grandtotal = String.valueOf(totValue);
        Log.d("total", grandtotal);

        shiping_subtotal.setText("$" + subValue);
        shiping_discount.setText("-$" + disValue);
        shiping_tax.setText("$" + tValue);
        shiping_total.setText("$" + totValue);
        del1.setText("$" + del2);
    }

    private void xmlid() {
        order_button = findViewById(R.id.order_button);


        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        postal_code = findViewById(R.id.postal_code);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);


        shiping_subtotal = findViewById(R.id.shiping_subtotal);
        shiping_discount = findViewById(R.id.shiping_discount);
        shiping_tax = findViewById(R.id.shiping_tax);
        shiping_total = findViewById(R.id.shiping_total);
        del1 = findViewById(R.id.del);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean fetchAndValidate() {
        boolean validate = true;

        Address1 = address1.getText().toString();
        Address2 = address2.getText().toString();
        PostalCode = postal_code.getText().toString();
        City = city.getText().toString();
        State = state.getText().toString();
        FirstName = first_name.getText().toString();
        LastName = last_name.getText().toString();

        Calendar rightNow = Calendar.getInstance();
        hour = rightNow.get(Calendar.HOUR_OF_DAY);
        minute = rightNow.get(Calendar.MINUTE);
        LocalDate currentdate = LocalDate.now();
        date = currentdate.toString();
        day = currentdate.getDayOfWeek().name();
        Random random = new Random();

        documentId = random.ints(97, 122 + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        if (FirstName.isEmpty()){
            first_name.setError("enter your First Name");
            validate = false;
        }
        if (LastName.isEmpty()){
            last_name.setError("enter your Last Name");
        }

        if(Address1.isEmpty()){
            address1.setError("proper address required");
            validate = false;
        }
        if(Address2.isEmpty()){
            address2.setError("proper address required");
            validate = false;
        }
        if(PostalCode.isEmpty()){
            postal_code.setError("proper address required");
            validate = false;
        }
        if(City.isEmpty()){
            city.setError("proper address required");
            validate = false;
        }
        if(State.isEmpty()){
            state.setError("proper address required");
            validate = false;
        }

        firestore.collection("users").document(uid).collection("cart").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if(Objects.requireNonNull(task.getResult()).size() > 0) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    cart_model data = document.toObject(cart_model.class);
                                    cartData.add(data);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Task Fails to get Cart products", Toast.LENGTH_SHORT).show();
                    }
                });

        return validate;
    }
    private void submitOrder() {
        String time = hour+":"+minute+", "+day+", "+date;
        String name = FirstName+" "+LastName;
        String address = Address1+", "+Address2+", "+City+", "+ State+", "+PostalCode;
        DocumentReference documentReference = firestore.collection("users").document(uid).collection("order").document(documentId);

        Map<String, String> orderData = new HashMap<>();
        orderData.put("orderid", documentId);
        orderData.put("total",grandtotal);
        orderData.put("uid",uid);
        orderData.put("name", name);
        orderData.put("phone", userphone);
        orderData.put("email",useremail);
        orderData.put("address", address);
        orderData.put("placed", time);
        orderData.put("ordertype", "new");
        documentReference.set(orderData).addOnSuccessListener(unused -> {
            for(int i = 0; i < cartData.size(); i++){
                String productId = cartData.get(i).getProductid();
                DocumentReference docReference = firestore.collection("users").document(uid).collection("order").document(documentId).collection("products").document(productId);
                Map<String, String> products = new HashMap<>();
                products.put("productid", productId);
                products.put("quantity", cartData.get(i).getQuantity());
                products.put("name", cartData.get(i).getName());
                products.put("caketype", cartData.get(i).getCaketype());
                products.put("design",cartData.get(i).getDesign());
                products.put("flavour", cartData.get(i).getFlavour());
                products.put("image",cartData.get(i).getImage());
                products.put("notes",cartData.get(i).getNotes());
                products.put("price",cartData.get(i).getPrice());
                products.put("shape",cartData.get(i).getShape());
                products.put("size",cartData.get(i).getSize());
                products.put("type",cartData.get(i).getType());

                docReference.set(products).addOnSuccessListener(unused1 -> firestore.collection("users").document(uid).collection("cart").document(productId)
                        .delete()
                        .addOnSuccessListener(unused2 -> {
                            Toast.makeText(getApplicationContext(), "Item Added successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> Log.w("error", e.toString()))).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());
            }
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show());

    }

}