package com.ntas.FarmUncle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Welcome extends AppCompatActivity {
    private ViewPager viewPager;
    private welcome_adapter adapter;

    Button next, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        getSupportActionBar().hide();

        viewPager = findViewById(R.id.welcome_viewpager);
        adapter = new welcome_adapter(this);
        viewPager.setAdapter(adapter);

        next = findViewById(R.id.welcome_nextbtn);
        back = findViewById(R.id.welcome_backbtn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount())
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });


    }
}
