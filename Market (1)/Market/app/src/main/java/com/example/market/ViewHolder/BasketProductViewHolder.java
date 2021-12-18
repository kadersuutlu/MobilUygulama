package com.example.market.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.market.Basket;
import com.example.market.Model.Product;
import com.example.market.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class BasketProductViewHolder extends RecyclerView.Adapter<BasketProductViewHolder.ViewHolder>{

    private List<Product> listeProduct;
    private List<Integer> listeCount;
    private Context pContext;
    private BasketProductViewHolder.OnItemClickListener pListener;

    private FirebaseUser firebaseProduct;

    public BasketProductViewHolder(List<Product> listeP, List<Integer> listeCount, Context pContext) {
        this.listeProduct = listeP;
        this.listeCount = listeCount;
        this.pContext = pContext;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(BasketProductViewHolder.OnItemClickListener listener) {
        this.pListener = listener;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_satiri, parent, false);
        return new ViewHolder(itemView, pListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(pContext).load(listeProduct.get(position).getProductImage()).into(holder.urunResim);
        holder.urunAd.setText("Ad : "+listeProduct.get(position).getProductName());
        holder.urunFiyat.setText("Fiyat : " + listeProduct.get(position).getProductPrice()+" TL");
        holder.urun_sayisi.setText("Adet : " + String.valueOf(listeCount.get(position)));
    }


    @Override
    public int getItemCount() {
        return listeProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView urunResim;
        public TextView urun_sayisi;
        public TextView urunAd;
        public TextView urunFiyat;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            this.urunResim = itemView.findViewById(R.id.urun_image);
            this.urunAd = itemView.findViewById(R.id.urun_ad);
            this.urunFiyat = itemView.findViewById(R.id.urun_fiyat);
            this.urun_sayisi = itemView.findViewById(R.id.urun_sayisi);

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
        }
    }
}
