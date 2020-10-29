package com.etezaz.assessment_task_magma.presenter;

import android.content.Context;

import com.etezaz.assessment_task_magma.db.dao.DaoMaster;
import com.etezaz.assessment_task_magma.model.modelHelper.BhAdsModel;
import com.etezaz.assessment_task_magma.model.db.table.BhAdsImageStatus;
import com.etezaz.assessment_task_magma.view.BhAdsView;

import java.util.List;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public class BhAdsPresenter {
    private Context context;
    private BhAdsView bhAdsView;
    private DaoMaster.DevOpenHelper openHelper;



    public BhAdsPresenter(BhAdsView bhAdsView, Context context) {
        this.openHelper = new DaoMaster.DevOpenHelper(context, "bhAds_db", null);
        this.context=context;
        this.bhAdsView=bhAdsView;
    }

    public void getAllBhAdsImageStatus(){

        BhAdsModel bhAdsModel = new BhAdsModel(openHelper);
        List<BhAdsImageStatus> bhAdsList = bhAdsModel.getAllBhAdsImageStatus();
        bhAdsView.displayImages(bhAdsList);

    }

}
