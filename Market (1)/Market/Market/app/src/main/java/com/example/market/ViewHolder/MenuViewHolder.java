package com.example.market.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.market.Interface.ItemClickListener;
import com.example.market.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView categoryName;
    public ImageView categoryImage;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName=(TextView) itemView.findViewById(R.id.categoryName);
        categoryImage=(ImageView) itemView.findViewById(R.id.categoryImage);


    }

    @Override
    public void onClick(View v) {

    }
}
