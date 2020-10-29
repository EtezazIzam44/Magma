package com.etezaz.assessment_task_magma.view;

import com.etezaz.assessment_task_magma.model.repository.imagesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public interface FirebaseAPI {

    @GET("/uploadprivate/event.json")
    Call<imagesResponse> getPrivateData();

}