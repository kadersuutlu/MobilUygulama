package com.example.market.cerceve;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.market.CartActivity;
import com.example.market.HomePageActivity;
import com.example.market.LoginActivity;
import com.example.market.MainActivity;
import com.example.market.PersonalActivity;
import com.example.market.ProfileActivity;
import com.example.market.R;

import java.util.Objects;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void Tikla(View v) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }

    public void Address(View v) {
        Intent intent = new Intent(getActivity(), PersonalActivity.class);
        startActivity(intent);
    }

    public void CreditCard(View v) {
        Intent intent = new Intent(getActivity(), CartActivity.class);
        startActivity(intent);
    }

    public void LogOut(View v) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }


}