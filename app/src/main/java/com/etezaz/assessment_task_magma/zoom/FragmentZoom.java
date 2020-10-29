package com.etezaz.assessment_task_magma.zoom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.etezaz.assessment_task_magma.R;
import com.etezaz.assessment_task_magma.presenter.BhAdsPresenter;

import androidx.fragment.app.Fragment;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public class FragmentZoom  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_zoom_image, container, false);

        ImageView image=view.findViewById(R.id.img);
       String url =getArguments().getString("url");

        Glide.with(image.getContext())
                .load(url)
                .into(image);

        return view;
    }


}
