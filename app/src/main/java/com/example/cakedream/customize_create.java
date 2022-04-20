package com.example.cakedream;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class customize_create extends FragmentActivity {
    TextView type, shape, size, flavour, design;
    ImageView home_btn, cart_btn;
    AppCompatButton continue_btn;
    private  int value = 1;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_create_screen);

        type = findViewById(R.id.type);
        shape = findViewById(R.id.shape);
        size = findViewById(R.id.size);
        flavour = findViewById(R.id.flavour);
        design = findViewById(R.id.design);


        continue_btn = findViewById(R.id.continue_btn);

        home_btn = findViewById(R.id.home1);
        cart_btn = findViewById(R.id.cart_btn);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (value){
                    case 1:
                        replaceFragment(new customizeType());
                        break;
                    case 2:
                        replaceFragment(new customizeShape());
                        break;
                    case 3:
                        replaceFragment(new customizeSize());
                        break;
                    case 4:
                        replaceFragment(new customizeFlavour());
                        break;
                    case 5:
                        replaceFragment(new customizeDesign());
                        break;



                }
                value++;
                if(value>5){
                    value=1;
                }
                }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(customize_create.this,pre_made_base.class);
                startActivity(intent);
            }
        });

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeType());
                value=1;
            }
        });
        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeShape());
                value=2;
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeSize());
                value=3;
            }
        });
        flavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeFlavour());
                value=4;
            }
        });
        design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeDesign());
                value=5;
            }
        });



    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


}
