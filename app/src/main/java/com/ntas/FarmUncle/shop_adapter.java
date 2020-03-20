package com.ntas.FarmUncle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class shop_adapter extends FirestoreRecyclerAdapter<shopitem_model, shop_adapter.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public shop_adapter(@NonNull FirestoreRecyclerOptions<shopitem_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull shopitem_model model) {

        holder.title.setText(model.getName());
        holder.price.setText(new StringBuilder().append("₹ ").append(model.getPrice()).toString());
        Glide
                .with(holder.itemView.getContext())
                .load(model.getImage())
                .centerCrop()
                .into(holder.img);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card, parent, false);

        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.shopcard_title);
            price = itemView.findViewById(R.id.shopcard_price);
            img = itemView.findViewById(R.id.shopcard_img);

        }
    }
}
