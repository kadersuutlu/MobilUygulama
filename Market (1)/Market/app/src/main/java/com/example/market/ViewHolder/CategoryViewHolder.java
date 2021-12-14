package com.example.market.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.market.Interface.ItemClickListener;
import com.example.market.Model.Category;
import com.example.market.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CategoryViewHolder extends RecyclerView.Adapter<CategoryViewHolder.ViewHolder> {
    private List<Category> liste;
    Context mContext;
    private CategoryViewHolder.OnItemClickListener mListener;

    public CategoryViewHolder(List<Category> liste, Context mContext) {
        this.liste = liste;
        this.mContext = mContext;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CategoryViewHolder.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_satiri, parent, false);
        return new ViewHolder(itemView, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mContext).load(liste.get(position).getImage()).into(holder.kategoriResmi);
        holder.kategoriAdi.setText(liste.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return liste.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView kategoriResmi;
        public TextView kategoriAdi;

        public ViewHolder(@NonNull View itemView, OnItemClickListener mListener) {
            super(itemView);
            this.kategoriResmi = itemView.findViewById(R.id.kategori_resmi);
            this.kategoriAdi = itemView.findViewById(R.id.kategori_adi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
