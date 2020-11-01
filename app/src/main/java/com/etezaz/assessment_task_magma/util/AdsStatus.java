package com.etezaz.assessment_task_magma.util;

/**
 * Created by Etezaz Abo Al-Izam on 28/07/2019.
 * Automata4Group
 * etezazizam44@gmail.com
 */



import java.lang.annotation.Retention;

import androidx.annotation.StringDef;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({AdsStatus.ACCEPT, AdsStatus.REJECT})
public @interface AdsStatus {
    String ACCEPT = "ACCEPT";
    String REJECT = "REJECT";
}