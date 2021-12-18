package com.example.market.cerceve;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.market.AdminPanelActivity;
import com.example.market.Model.Category;
import com.example.market.ProductActivity;
import com.example.market.ProfileActivity;
import com.example.market.R;
import com.example.market.ViewHolder.CategoryViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseDatabase database;
    private DatabaseReference categoryYukle;
    private ValueEventListener categoryYukleListener;

    //Category RecyclerView
    private RecyclerView recyclerView;
    CategoryViewHolder recyclerAdapter;
    List<Category> liste = new ArrayList<>();
    int categoryCount = 0;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Firebase tanımlamaları
        database = FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        kategoriYukle();
        return view;
    }

    private void kategoriYukle() {
        categoryYukle = database.getReference("Category/");
        categoryYukleListener = categoryYukle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        liste.clear();
                        for (int listeSize = 0; listeSize < snapshot.getChildrenCount(); listeSize++) {
                            if (snapshot.child(String.valueOf(listeSize + 1)).exists()) {
                                snapshot.child(String.valueOf(listeSize + 1));
                                liste.add(new Category(snapshot.child(String.valueOf(listeSize + 1)).getKey(),
                                        snapshot.child(String.valueOf(listeSize + 1)).child("name").getValue(String.class),
                                        snapshot.child(String.valueOf(listeSize + 1)).child("image").getValue(String.class)));
                            }
                        }
                        recyclerViewRun(liste);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recyclerViewRun(List<Category> mliste) {
        recyclerAdapter = new CategoryViewHolder(mliste, getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter.setOnItemClickListener(new CategoryViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String id) {
                //Toast.makeText(getApplicationContext(), "Position ~> " + position, Toast.LENGTH_SHORT).show();
                Intent gecis = new Intent(getActivity(), ProductActivity.class);
                gecis.putExtra("category", id);
                gecis.putExtra("status", "1");
                //Status="0" admin
                //Status="1" user
                startActivity(gecis);
            }
        });
    }

}