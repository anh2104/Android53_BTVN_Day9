package com.example.android53_btvn_day9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android53_btvn_day9.R;
import com.example.android53_btvn_day9.models.objects.Product;
import com.example.android53_btvn_day9.uis.IClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private List<Product> list;
    private Context mContext;

    private IClickListener iClickListener;

    public ProductAdapter(List<Product> list) {
        this.list = list;
    }

    public ProductAdapter(List<Product> list ,IClickListener iClickListener) {
        this.list = list;
        this.iClickListener=iClickListener;
    }
    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext= parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_items_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product =list.get(position);
        holder.tvTittle.setText(product.getTitle());
        holder.tvPrice.setText("$ "+product.getPrice().toString());
        holder.tvRating.setText(product.getRating().toString());
        Glide.with(mContext).load(product.getThumbnail()).into(holder.imgAnh);
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        BottomNavigationView bnvMain;
        LinearLayout llProduct;
        ImageView imgAnh,imgSave;
        TextView tvTittle,tvPrice,tvRating;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            bnvMain=itemView.findViewById(R.id.bottom_nav);
            imgAnh=itemView.findViewById(R.id.imgAnh);
            tvTittle=itemView.findViewById(R.id.tvTittle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvRating=itemView.findViewById(R.id.tvRating);
            llProduct=itemView.findViewById(R.id.llProduct);
            imgSave=itemView.findViewById(R.id.imgSave);
        }
    }
}
