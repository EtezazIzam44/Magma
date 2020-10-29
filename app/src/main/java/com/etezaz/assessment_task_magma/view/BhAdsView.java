package com.etezaz.assessment_task_magma.view;


import com.etezaz.assessment_task_magma.model.db.table.BhAdsImageStatus;

import java.util.List;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public interface BhAdsView {

    void displayImages(List<BhAdsImageStatus> imagesList);

}
