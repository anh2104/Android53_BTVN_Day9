package com.example.android53_btvn_day9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android53_btvn_day9.R;
import com.example.android53_btvn_day9.models.objects.Photo;

import java.util.List;

public class PhotoViewPager2Adapter extends RecyclerView.Adapter<PhotoViewPager2Adapter.PhotoViewHodel> {
    private Context mContext;
    private List<Photo> mListPhoto;

    public PhotoViewPager2Adapter(List<Photo> mListPhoto) {
        this.mListPhoto = mListPhoto;
    }

    @NonNull
    @Override
    public PhotoViewPager2Adapter.PhotoViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo,parent,false);
        return new PhotoViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewPager2Adapter.PhotoViewHodel holder, int position) {
        Photo photo = mListPhoto.get(position);
        if (photo == null) {
            return;
        }
        holder.imgPhoto.setImageResource(photo.getImageId());
    }

    @Override
    public int getItemCount() {
        return mListPhoto != null? mListPhoto.size() : 0;
    }

    public class PhotoViewHodel extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        public PhotoViewHodel(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }
}
