package com.example.cakedream;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class admin_order_adapter extends FragmentStateAdapter {

    public admin_order_adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new Delivered_Admin_Fragments();
            case 2:
                return new Cancelled_Admin_Fragment();
        }
        return new Processing_Admin_Fragment();
    }

    @Override
    public int getItemCount() { return 3; }
}
