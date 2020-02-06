package com.ntas.FarmUncle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class welcome_adapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public int[] list_images = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4

    };

    public String[] list_titles = {
            "Hello",
            "go on",
            "almost there",
            "yeah you got it"

    };

    public int[] colors = {
            Color.rgb(55, 55, 55),
            Color.rgb(239, 85, 85),
            Color.rgb(110, 49, 89),
            Color.rgb(1, 188, 212)


    };

    public welcome_adapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return list_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider, container, false);
        LinearLayout layoutside = view.findViewById(R.id.slider_layout);
        ImageView imageView = view.findViewById(R.id.welcome_image);
        TextView textView = view.findViewById(R.id.welcome_text);
        layoutside.setBackgroundColor(colors[position]);
        imageView.setImageResource(list_images[position]);
        textView.setText(list_titles[position]);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
