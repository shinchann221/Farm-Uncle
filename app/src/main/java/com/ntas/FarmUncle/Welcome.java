package com.ntas.FarmUncle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Welcome extends AppCompatActivity {

    private ViewPager viewPager;
    private welcome_adapter adapter;

    Button next, back;
    ImageButton skip;
    private LinearLayout dots_layout;

    private TextView[] dots;

    int currentpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedPreferences = getSharedPreferences("welcome", MODE_PRIVATE);
        if (sharedPreferences.contains("applied")) {
            Intent intent = new Intent(Welcome.this, Login.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.welcome);

        getSupportActionBar().hide();

        viewPager = findViewById(R.id.welcome_viewpager);
        adapter = new welcome_adapter(this);
        viewPager.setAdapter(adapter);

        next = findViewById(R.id.welcome_nextbtn);
        back = findViewById(R.id.welcome_backbtn);
        skip = findViewById(R.id.welcome_skipbtn);

        dots_layout = findViewById(R.id.dots_layout);

        adddotsindicator(0);

        viewPager.addOnPageChangeListener(viewlistener);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentpage - 1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentpage + 1);

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("applied", 1).commit();
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void adddotsindicator(int position) {
        dots = new TextView[4];
        dots_layout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.TransparentWhite));

            dots_layout.addView(dots[i]);

        }

        if (dots.length > 0) {

            dots[position].setTextColor(getResources().getColor(android.R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            adddotsindicator(position);
            currentpage = position;

            if (position == 0) {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);

            } else if (position == dots.length - 1) {
                next.setEnabled(false);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);

            } else {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
