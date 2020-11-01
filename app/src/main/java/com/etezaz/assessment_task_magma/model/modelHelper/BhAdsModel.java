package com.etezaz.assessment_task_magma.model.modelHelper;

import android.database.sqlite.SQLiteDatabase;

import com.etezaz.assessment_task_magma.db.dao.BhAdsImageStatusDao;
import com.etezaz.assessment_task_magma.db.dao.DaoMaster;
import com.etezaz.assessment_task_magma.db.dao.DaoSession;
import com.etezaz.assessment_task_magma.model.db.table.BhAdsImageStatus;

import java.util.List;

/**
 * Created by Etezaz Abo Al-Izam .
 * Automata4Group
 * etezazizam44@gmail.com
 */
public class BhAdsModel {
    private BhAdsImageStatusDao bhAdsImageStatusDao;


    public BhAdsModel(DaoMaster.DevOpenHelper openHelper) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        DaoMaster  master = new DaoMaster(db);//create masterDao
        DaoSession daoSession = master.newSession(); //Creates Session session
        this.bhAdsImageStatusDao = daoSession.getBhAdsImageStatusDao();
    }

    public int getAllBhAdsImageStatus(int status){
        return bhAdsImageStatusDao.queryBuilder().
                where(BhAdsImageStatusDao.Properties.ImageStatus.eq(status)).build().list().size();
    }


    public void insertBhAdsImageStatusToDB(BhAdsImageStatus bhAdsImageStatus){
        bhAdsImageStatusDao.insertOrReplace(bhAdsImageStatus);
    }

}
