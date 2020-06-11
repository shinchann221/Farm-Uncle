package com.ntas.FarmUncle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        //Set Notification Bar color to transparent White :)
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.TransparentWhite));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_order:
                        startActivity(new Intent(getApplicationContext(), MyOrders.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_cart:
                        return true;

                    case R.id.nav_shop:
                        startActivity(new Intent(getApplicationContext(), Shop.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}
