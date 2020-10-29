package com.etezaz.assessment_task_magma.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.etezaz.assessment_task_magma.R;
import com.etezaz.assessment_task_magma.model.modelHelper.db.table.BhAdsImageStatus;
import com.etezaz.assessment_task_magma.view.OnItemClickListener;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
class BhAdsAdapter extends RecyclerView.Adapter<BhAdsAdapter.MyViewHolder> {

    private List<BhAdsImageStatus> bhAdsImageList;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BhAdsAdapter(List<BhAdsImageStatus> bhAdsImageList) {
        this.bhAdsImageList = bhAdsImageList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BhAdsImageStatus c = bhAdsImageList.get(position);
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
            onItemClickListener.onItemClick(bhAdsImageList.get(position).getAdCode(),bhAdsImageList.get(position).getImageUrl(), v);
        }


    }

}