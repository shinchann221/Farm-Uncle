package com.ntas.FarmUncle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tablayout = findViewById(R.id.login_tablayout);
        viewPager = findViewById(R.id.login_viewpager);
        Loginadapter adapter = new Loginadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.AddFragments(new SignIn(), "Sign In");
        adapter.AddFragments(new SignUp(), "Sign Up");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
