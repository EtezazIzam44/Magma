package com.etezaz.assessment_task_magma.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.etezaz.assessment_task_magma.R;
import com.etezaz.assessment_task_magma.model.db.table.BhAdsImageStatus;
import com.etezaz.assessment_task_magma.view.OnItemClickListener;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
class BhAdsAdapter extends RecyclerView.Adapter<BhAdsAdapter.MyViewHolder> {

    private ArrayList<List<String>> bhAdsImageList;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BhAdsAdapter(ArrayList<List<String>> bhAdsImageList) {
        this.bhAdsImageList = bhAdsImageList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String c = bhAdsImageList.get(position).get(0);
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_launcher_background);

        Glide
                .with(holder.imgv.getContext())
                .load(c)
                .apply(options)
                .into(holder.imgv);
    }

    @Override
    public int getItemCount() {
        return bhAdsImageList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images,parent, false);
        return new MyViewHolder(v);
    }


    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgv;

        public MyViewHolder(View view) {
            super(view);
            imgv =  view.findViewById(R.id.img);
            imgv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            onItemClickListener.onItemClick(bhAdsImageList.get(position).get(0),bhAdsImageList.get(position).get(1), v);
        }


    }

}