package com.example.market.cerceve;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.market.Basket;
import com.example.market.Model.Product;
import com.example.market.R;
import com.example.market.ViewHolder.BasketProductViewHolder;
import com.example.market.ViewHolder.ProductViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ShoppingCartFragment extends Fragment {

    private List<Product> listeProduct;
    private List<Integer> listeCount;
    private RecyclerView recyclerView;
    private BasketProductViewHolder productViewHolder;

    private Button sepet_onay;
    private Button sepet_sil;
    private CardView productCard;

    public ShoppingCartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        productCard=view.findViewById(R.id.productCard);
        sepet_onay=view.findViewById(R.id.sepet_onay);
        sepet_sil=view.findViewById(R.id.delete_cart);

        recyclerView=view.findViewById(R.id.recycler_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productCard.setVisibility(View.INVISIBLE);
        sepet_onay.setVisibility(View.INVISIBLE);



        //productViewHolder = new BasketProductViewHolder(mProducts, getContext());
        Basket.getLiveBasketList().observeForever(strings -> {
            //Toast.makeText(requireActivity(), strings.toString(), Toast.LENGTH_SHORT).show();

            if(strings.isEmpty()){
                productCard.setVisibility(View.INVISIBLE);
                sepet_onay.setVisibility(View.INVISIBLE);
            } else {
                productCard.setVisibility(View.VISIBLE);
                sepet_onay.setVisibility(View.VISIBLE);
            }

            ValueEventListener urunCountRefListener = FirebaseDatabase.getInstance().getReference("Product").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.hasChildren()) {

                            listeProduct = new ArrayList<>();
                            listeCount = new ArrayList<>();

                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                if(strings.contains(dataSnapshot.getKey())){

                                    Product product=dataSnapshot.getValue(Product.class);
                                    listeProduct.add(product);
                                    listeCount.add(Collections.frequency(strings, dataSnapshot.getKey()));
                                }
                            }

                            productViewHolder = new BasketProductViewHolder(listeProduct, listeCount, getContext());
                            recyclerView.setAdapter(productViewHolder);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }

            });
        });

        productViewHolder = new BasketProductViewHolder(new ArrayList<>(), new ArrayList<>(), getContext());
        recyclerView.setAdapter(productViewHolder);

        return view;
    }
}