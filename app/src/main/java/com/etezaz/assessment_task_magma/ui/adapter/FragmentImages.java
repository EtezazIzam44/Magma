package com.etezaz.assessment_task_magma.ui.adapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.etezaz.assessment_task_magma.R;
import com.etezaz.assessment_task_magma.model.modelHelper.db.table.BhAdsImageStatus;
import com.etezaz.assessment_task_magma.presenter.BhAdsPresenter;
import com.etezaz.assessment_task_magma.view.BhAdsView;
import com.etezaz.assessment_task_magma.view.OnItemClickListener;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public class FragmentImages extends Fragment implements BhAdsView {

    private View view;
    private RecyclerView recyclerView;
    private BhAdsAdapter imagesAdapter;
    private  BhAdsPresenter imagesPresenter;
    private Button accept,reject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        accept=view.findViewById(R.id.btn_accepted);
        reject=view.findViewById(R.id.btn_rejected);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setLayoutFrozen(true);
        imagesPresenter = new BhAdsPresenter(this, getContext());

        imagesPresenter.getAllBhAdsImageStatus();

        return view;
    }

    
    @Override
    public void displayImages(List<BhAdsImageStatus> imagesList) {
        imagesAdapter = new BhAdsAdapter(imagesList);
        recyclerView.setAdapter(imagesAdapter);

        imagesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String id,String url, View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                FragmentZoom fragmentZoom = new FragmentZoom();
                fragmentZoom.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.linear_layout, fragmentZoom)
                        .addToBackStack("images")
                        .commit();
            }

        });
    }
}
