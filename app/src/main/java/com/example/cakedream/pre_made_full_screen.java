package com.example.cakedream;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakedream.pre_made_Adapter.preMadeHomeAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class pre_made_full_screen extends AppCompatActivity {
    private ArrayList<productData> productItemData1 = new ArrayList<>();
    private ArrayList<productData> copyItemData = new ArrayList<>();
    private RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    preMadeFullAdapter preMadeFulladapter;
    ImageButton sorting_button;
    SearchView searchView;
    ImageView back_button, cart_button;
    Spinner dropdown;
    int i;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pre_made);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.pre_made_recycle);

        dropdown = findViewById(R.id.sort_filter);
        productItemData1 = new ArrayList<productData>();

        back_button = findViewById(R.id.back_button);
        cart_button = findViewById(R.id.cart);



        setProductInformation();

        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), pre_made_base.class);
            startActivity(intent);
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sort_filter,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(spinnerAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(i == 1){
                    preMadeFulladapter.lowTohigh(copyItemData);
                }
                if(i == 2){
                    preMadeFulladapter.highToLow(copyItemData);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setAdapter() {
        preMadeFulladapter = new preMadeFullAdapter(productItemData1);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(pre_made_full_screen.this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(preMadeFulladapter);


    }

    private void setProductInformation() {
        firebaseFirestore.collection("products").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            productData data = d.toObject(productData.class);
                            assert data != null;
                            String type = data.getType();
                            productItemData1.add(data);
                        }
                    }else {
                        Toast.makeText(this, "Cakes are not avalible for this time", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(this, "Opps!,Fail to fetch data", Toast.LENGTH_SHORT).show())
                .addOnCompleteListener(task -> {
                    copyItemData.addAll(productItemData1);
                    setAdapter();

                });
    }

}
