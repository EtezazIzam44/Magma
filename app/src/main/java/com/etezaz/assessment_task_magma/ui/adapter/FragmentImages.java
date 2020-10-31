package com.etezaz.assessment_task_magma.ui.adapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
       /* recyclerView.setLayoutFrozen(true);
        imagesPresenter = new BhAdsPresenter(this, getContext());

        imagesPresenter.getAllBhAdsImageStatus();*/

        //region Firebase

        //authImplicit();

//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[] {
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        },
//                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
//            }
//        }

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    initializeAppForStorage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        String bucketName="waseet-ads-images-bh";

        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

//            FileInputStream serviceAccount = {
//                    "type": "service_account",
//                    "project_id": "xxxxxx",
//                    "private_key_id": "xxxxxx",
//                    "private_key": "-----BEGIN PRIVATE KEY-----\jr5x+4AvctKLonBafg\nElTg3Cj7pAEbUfIO9I44zZ8=\n-----END PRIVATE KEY-----\n",
//                    "client_email": "xxxx@xxxx.iam.gserviceaccount.com",
//                    "client_id": "xxxxxxxx",
//                    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
//                    "token_uri": "https://oauth2.googleapis.com/token",
//                    "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
//                    "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-5rmdm%40xxxxx.iam.gserviceaccount.com"
//      };

          /*  FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("bucketName.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);

            Bucket bucket = StorageClient.getInstance().bucket();*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //mykey.json you get from FBconsole/Project Settings/service accounts/generte new private key

       /* File myFile = new File("2baOEUTpE2/M7El0gEKt+1GfSn4qD+c8SMBtFm6g");

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
*/

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

     void authImplicit() {
        // If you don't specify credentials when constructing the client, the client library will
        // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
        Storage storage = StorageOptions.getDefaultInstance().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }

    public void initializeAppForStorage() throws IOException {
        // [START init_admin_sdk_for_storage]

        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
     //   Uri uri = Uri.fromFile(new File("//assets/google-services.json"));
        InputStream is = getActivity().getAssets().open("google-services.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(is)
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }

        
        String bucketName="waseet-ads-images-bh";

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setStorageBucket(bucketName+".appspot.com")
                .build();
        FirebaseApp.initializeApp(options);

        Bucket bucket = StorageClient.getInstance().bucket();

        /* FileInputStream serviceAccount = new FileInputStream("app/google-services.json");
       // FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket(bucketName+".appspot.com")
                .build();*//*

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl(bucketName+".appspot.com")
                .build();

        FirebaseApp.initializeApp(options);

        Bucket bucket = StorageClient.getInstance().bucket();

        // 'bucket' is an object defined in the google-cloud-storage Java library.
        // See http://googlecloudplatform.github.io/google-cloud-java/latest/apidocs/com/google/cloud/storage/Bucket.html
        // for more details.
        // [END init_admin_sdk_for_storage]
        System.out.println("Retrieved bucket: " + bucket.getName());*/
    }

}
