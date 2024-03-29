package com.example.market.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.market.Model.Product;
import com.example.market.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.ViewHolder>{

    private List<Product> listeProduct;
    private Context pContext;
    private ProductViewHolder.OnItemClickListener pListener;

    private FirebaseUser firebaseProduct;

    public ProductViewHolder(List<Product> listeP, Context pContext) {
        this.listeProduct = listeP;
        this.pContext = pContext;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onBasketClick(int position, String productID);
    }

    public void setOnItemClickListener(ProductViewHolder.OnItemClickListener listener) {
        this.pListener = listener;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_satiri, parent, false);
        return new ViewHolder(itemView, pListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(pContext).load(listeProduct.get(position).getProductImage()).into(holder.urunResim);
        holder.urunAd.setText(listeProduct.get(position).getProductName());
        holder.urunFiyat.setText(" " + listeProduct.get(position).getProductPrice()+" TL");
    }


    @Override
    public int getItemCount() {
        return listeProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView urunResim;
        public ImageView add2Basket;
        public TextView urunAd;
        public TextView urunFiyat;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            this.urunResim = itemView.findViewById(R.id.product_resmi);
            this.urunAd = itemView.findViewById(R.id.product_adi);
            this.urunFiyat = itemView.findViewById(R.id.product_fiyatı);
            this.add2Basket = itemView.findViewById(R.id.add2Basket);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            pListener.onItemClick(position);
                        }
                    }
                }
            });

            add2Basket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            pListener.onBasketClick(position, listeProduct.get(position).getProductID());
                        }
                    }
                }
            });
        }
    }
}
