package com.etezaz.assessment_task_magma.model.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */

@Entity
public
class BhAdsImageStatus {

    @Id
    private String adCode;
    private int imageIndex;
    private String imageUrl;
    private boolean imageStatus;
    private String annotatedBy;
    @Generated(hash = 1553627817)
    public BhAdsImageStatus(String adCode, int imageIndex, String imageUrl,
            boolean imageStatus, String annotatedBy) {
        this.adCode = adCode;
        this.imageIndex = imageIndex;
        this.imageUrl = imageUrl;
        this.imageStatus = imageStatus;
        this.annotatedBy = annotatedBy;
    }
    @Generated(hash = 2040463493)
    public BhAdsImageStatus() {
    }
    public String getAdCode() {
        return this.adCode;
    }
    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
    public int getImageIndex() {
        return this.imageIndex;
    }
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean getImageStatus() {
        return this.imageStatus;
    }
    public void setImageStatus(boolean imageStatus) {
        this.imageStatus = imageStatus;
    }
    public String getAnnotatedBy() {
        return this.annotatedBy;
    }
    public void setAnnotatedBy(String annotatedBy) {
        this.annotatedBy = annotatedBy;
    }
    

}


