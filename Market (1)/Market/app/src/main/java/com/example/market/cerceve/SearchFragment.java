package com.example.market.cerceve;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.market.Model.Product;
import com.example.market.R;
import com.example.market.ViewHolder.ProductViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductViewHolder productViewHolder;
    private List<Product> mProducts;

    EditText search_bar;



    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);



        recyclerView=view.findViewById(R.id.recycler_view_arama);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar=view.findViewById(R.id.edit_search_bar);

        mProducts=new ArrayList<>();
        productViewHolder = new ProductViewHolder(mProducts,getContext());

        recyclerView.setAdapter(productViewHolder);

        //productRead();

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productSearch(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void productSearch(String s){
        //Query sorgu= FirebaseDatabase.getInstance().getReference("Category").child("1").child("Product")
        Query sorgu= FirebaseDatabase.getInstance().getReference("Product")
                .orderByChild("productName");
                /*.startAt(s)
                .endAt(s+"\uf8ff");*/

        sorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProducts.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.child("productName").toString().toLowerCase().contains(s)){
                        Product product=snapshot.getValue(Product.class);
                        mProducts.add(product);
                    }
                }
                productViewHolder.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void productRead(){
        DatabaseReference productYol=FirebaseDatabase.getInstance().getReference("Category").child("/Product");
        productYol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(search_bar.getText().toString().equals("")){
                    mProducts.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Product product=snapshot.getValue(Product.class);
                        mProducts.add(product);
                    }
                    productViewHolder.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}