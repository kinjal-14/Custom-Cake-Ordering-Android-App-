package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class admin_order extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ImageView back_button;
    admin_order_adapter admin_order_adapter1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order);

        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpager);
        back_button = findViewById(R.id.back_button);

        FragmentManager fragmentManager = getSupportFragmentManager();
        admin_order_adapter1 = new admin_order_adapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(admin_order_adapter1);
        tabLayout.addTab(tabLayout.newTab().setText("Confirmed"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_order.this,admin_home.class);
                startActivity(intent);
            }
        });
    }
}
