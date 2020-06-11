package com.ntas.FarmUncle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Shop extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("B2C").document("Products").collection("Vegetables");

    private shop_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        setupnotificationbarcolor();
        setupbottomnav();
        setuprecyclerview();
    }

    void setupnotificationbarcolor() {
        //Set Notification Bar color to transparent White :)
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.TransparentWhite));
    }

    void setupbottomnav() {
        //Setup Bottom Nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_shop);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_order:
                        startActivity(new Intent(getApplicationContext(), MyOrders.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_cart:
                        startActivity(new Intent(getApplicationContext(), Cart.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_shop:
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

    private void setuprecyclerview() {
        Query query = ref;

        FirestoreRecyclerOptions<shopitem_model> options = new FirestoreRecyclerOptions.Builder<shopitem_model>()
                .setQuery(query, shopitem_model.class)
                .build();

        adapter = new shop_adapter(options);

        RecyclerView recyclerView = findViewById(R.id.shopitems_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
