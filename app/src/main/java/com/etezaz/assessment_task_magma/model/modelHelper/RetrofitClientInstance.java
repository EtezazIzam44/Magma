package com.etezaz.assessment_task_magma.model.modelHelper;

import android.util.Log;

import com.etezaz.assessment_task_magma.model.repository.imagesResponse;
import com.etezaz.assessment_task_magma.view.FirebaseAPI;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * Automata4Group
 * etezazizam44@gmail.com
 */
public class RetrofitClientInstance {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization",  "Bearer "+"https://oauth2.googleapis.com/token"); // <-- this is the important line
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    });

    httpClient.addInterceptor(logging);
    OkHttpClient client = httpClient.build();



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://yourproject.firebaseio.com")//url of firebase database app
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())//use for convert JSON file into object
            .build();

    // prepare call in Retrofit 2.0
    FirebaseAPI firebaseAPI = retrofit.create(FirebaseAPI.class);

    Call<imagesResponse> call2=firebaseAPI.getPrivateData();

    call2.enqueue(new Callback<imagesResponse>() {
        @Override
        public void onResponse(Call<imagesResponse> call, Response<imagesResponse> response) {

            Log.d("Response ", "onResponse");
            //t1.setText("success name "+response.body().getName());


        }

        @Override
        public void onFailure(Call<imagesResponse> call, Throwable t) {
            Log.d("Response ", "onFailure");
            //t1.setText("Notification failure");
        }
    });
}