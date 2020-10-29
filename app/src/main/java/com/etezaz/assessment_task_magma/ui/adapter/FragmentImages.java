package com.etezaz.assessment_task_magma.ui.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.etezaz.assessment_task_magma.R;
import com.etezaz.assessment_task_magma.model.db.table.BhAdsImageStatus;
import com.etezaz.assessment_task_magma.presenter.BhAdsPresenter;
import com.etezaz.assessment_task_magma.view.BhAdsView;
import com.etezaz.assessment_task_magma.view.OnItemClickListener;
import com.etezaz.assessment_task_magma.zoom.FragmentZoom;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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

        //region Firebase
        //mykey.json you get from FBconsole/Project Settings/service accounts/generte new private key

        File myFile = new File("2baOEUTpE2/M7El0gEKt+1GfSn4qD+c8SMBtFm6g");

        GoogleCredential googleCred = null;
        try {
            googleCred = GoogleCredential.fromStream(new FileInputStream(myFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleCredential scoped = googleCred.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/firebase.database",
                        "https://www.googleapis.com/auth/userinfo.email"
                )
        );
        try {
            scoped.refreshToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String token = scoped.getAccessToken();
        Log.d("token ", token);


        //endregion

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
