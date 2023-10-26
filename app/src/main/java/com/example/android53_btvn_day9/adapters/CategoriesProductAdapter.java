package com.example.android53_btvn_day9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android53_btvn_day9.R;
import com.example.android53_btvn_day9.models.objects.CategoriesProduct;
import com.example.android53_btvn_day9.uis.IClickListener;
import com.example.android53_btvn_day9.uis.fragments.HomeFragment;

import java.util.List;

public class CategoriesProductAdapter extends RecyclerView.Adapter<CategoriesProductAdapter.CategoriesProductViewHolder> {

    private Context mContext;
    private List<CategoriesProduct> mCategoriesProductList;
    private IClickListener iClickListener;

//    private IClickSave iClickSave;

    public CategoriesProductAdapter(IClickListener iClickListener, HomeFragment homeFragment) {
        this.iClickListener = iClickListener;
    }
    public void setData(List<CategoriesProduct> list){
        this.mCategoriesProductList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesProductAdapter.CategoriesProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_caegories,parent,false);
        return new CategoriesProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesProductAdapter.CategoriesProductViewHolder holder, int position) {
        CategoriesProduct categoriesProduct=mCategoriesProductList.get(position);
        if(categoriesProduct==null){
            return;
        }
        holder.tvName.setText(categoriesProduct.getNameTitle());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);

        ProductAdapter productAdapter =new ProductAdapter(categoriesProduct.getListProduct(),iClickListener);
        holder.rcvProduct.setLayoutManager(linearLayoutManager);
        holder.rcvProduct.setAdapter(productAdapter);
    }

    @Override
    public int getItemCount() {
        return mCategoriesProductList != null? mCategoriesProductList.size() : 0;

    }

    public class CategoriesProductViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rcvProduct;
        TextView tvName;
        public CategoriesProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            rcvProduct=itemView.findViewById(R.id.rcvProduct);
        }
    }
}
