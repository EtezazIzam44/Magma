package com.etezaz.assessment_task_magma.ui.adapter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.etezaz.assessment_task_magma.R;
import com.etezaz.assessment_task_magma.model.db.table.BhAdsImageStatus;
import com.etezaz.assessment_task_magma.presenter.BhAdsPresenter;
import com.etezaz.assessment_task_magma.view.BhAdsView;
import com.etezaz.assessment_task_magma.view.OnItemClickListener;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.common.collect.Lists;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Etezaz Abo Al-Izam on 10/29/2020.
 * etezazizam44@gmail.com
 */
public class FragmentImages extends Fragment implements BhAdsView, View.OnClickListener {

    private View view;
    private RecyclerView recyclerView;
    private BhAdsAdapter imagesAdapter;
    private  BhAdsPresenter imagesPresenter;
    private Button accept,reject;
    private ArrayList<List<String>> arrayList=new ArrayList<List<String>>();
    private Page<Blob> blobPage;
    private boolean flag=false;
    private LinearLayoutManager layoutManager;
    private TextView txtvImages,txtvRecord,txtvAcc,txtvRej;
    private int page=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_images, container, false);

        accept=view.findViewById(R.id.btn_accepted);
        reject=view.findViewById(R.id.btn_rejected);
        txtvImages=view.findViewById(R.id.images);
        txtvRej=view.findViewById(R.id.rejected);
        txtvRecord=view.findViewById(R.id.record);
        txtvAcc=view.findViewById(R.id.accepted);
        recyclerView = view.findViewById(R.id.recycler);
        layoutManager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        imagesAdapter=new BhAdsAdapter(arrayList);
        recyclerView.setAdapter(imagesAdapter);


        imagesPresenter = new BhAdsPresenter(this, getContext());

        reject.setOnClickListener(this);
        accept.setOnClickListener(this);

        txtvAcc.setText(imagesPresenter.getAllBhAdsImageStatus(1)+"\n"+getResources().getString(R.string.accepted));
        txtvRej.setText(imagesPresenter.getAllBhAdsImageStatus(0)+"\n"+getResources().getString(R.string.rejected));

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //region Firebase

        DownloadFilesTask adapter1=new DownloadFilesTask();
        adapter1.execute();

        //endregion

        return view;
    }

    
    @Override
    public void displayImages(List<BhAdsImageStatus> imagesList) {
       /* imagesAdapter = new BhAdsAdapter(imagesList);
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

        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_accepted:
                BhAdsImageStatus bhAdsImageStatus=new BhAdsImageStatus();
                String[] img=arrayList.get(layoutManager.findLastVisibleItemPosition()).get(1).split("-");
                bhAdsImageStatus.setImageStatus(true);
                bhAdsImageStatus.setAnnotatedBy("Etezaz Abo AL-Izam");
                bhAdsImageStatus.setAdCode(img[0]);
                bhAdsImageStatus.setImageIndex(Integer.parseInt(img[1].substring(0,1)));
                imagesPresenter.insertBhAdsImageStatusToDB(bhAdsImageStatus);
                recyclerView.getLayoutManager().scrollToPosition(layoutManager.findLastVisibleItemPosition() + 1);

                txtvAcc.setText(imagesPresenter.getAllBhAdsImageStatus(1)+"\n"+getResources().getString(R.string.accepted));

                break;
            case R.id.btn_rejected:
                BhAdsImageStatus bhAdsImageStatus2=new BhAdsImageStatus();
                String[] img2=arrayList.get(layoutManager.findLastVisibleItemPosition()).get(1).split("-");
                bhAdsImageStatus2.setImageStatus(false);
                bhAdsImageStatus2.setAnnotatedBy("Etezaz Abo AL-Izam");
                bhAdsImageStatus2.setAdCode(img2[0]);
                bhAdsImageStatus2.setImageIndex(Integer.parseInt(img2[1].substring(0,1)));
                imagesPresenter.insertBhAdsImageStatusToDB(bhAdsImageStatus2);
                recyclerView.getLayoutManager().scrollToPosition(layoutManager.findLastVisibleItemPosition() + 1);

                txtvRej.setText(imagesPresenter.getAllBhAdsImageStatus(0)+"\n"+getResources().getString(R.string.rejected));

                break;
        }
    }

    private class PrepareAdapter1 extends AsyncTask<Void,Void,BhAdsAdapter > {
        ProgressDialog dialog= new ProgressDialog(getContext());

        public PrepareAdapter1(Page<Blob> blobPage1) {
            blobPage=blobPage1;
        }

        public PrepareAdapter1() {
        }

        @Override
        protected void onPreExecute() {
            if(blobPage==null)
            {
                dialog.setMessage("\tLoading...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            }

        }
        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected BhAdsAdapter doInBackground(Void... params) {
            String bucketName="waseet-ads-images-bh";
            InputStream is;
            GoogleCredentials credentials;

            if(blobPage==null)
            {
                try {
                    is = getActivity().getAssets().open("google-services.json");
                    credentials = GoogleCredentials.fromStream(is)
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));


                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(credentials)
                            .setStorageBucket(bucketName)
                            .build();
                    FirebaseApp.initializeApp(options);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bucket bucket = StorageClient.getInstance().bucket();

                blobPage = bucket.list();
            }


            for (Blob blob : blobPage.iterateAll()) {
                URL signedUrl = blob.signUrl(14, TimeUnit.DAYS);
                arrayList.add(Arrays.asList(signedUrl.toString(), blob.getName()));
                System.out.println("blob.getName()"+" "+signedUrl.toString());
                // publishProgress(signedUrl.toString());
                // SystemClock.sleep(6000);
                    if(arrayList.size()>3)
                       break;


            }

            imagesAdapter = new BhAdsAdapter (arrayList);

            /*if(blobPage.hasNextPage()) {
                flag=true;
                blobPage = blobPage.getNextPage();
            }*/

            return imagesAdapter;
        }

        protected void onPostExecute(BhAdsAdapter imagesAdapter) {
            //recyclerView.setAdapter(imagesAdapter);

            if (imagesAdapter!= null) {
                imagesAdapter.notifyDataSetChanged();

            }

            dialog.dismiss();
           /* PrepareAdapter1 adapter1=new PrepareAdapter1(blobPage);
            adapter1.execute();*/
        }
    }

   /* public void listAllPaginated(@Nullable String pageToken) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("files/uid");

        // Fetch the next page of results, using the pageToken if we have one.
        Task<ListResult> listPageTask = pageToken != null
                ? listRef.list(100, pageToken)
                : listRef.list(100);

        listPageTask
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        List<StorageReference> prefixes = listResult.getPrefixes();
                        List<StorageReference> items = listResult.getItems();

                        // Process page of results
                        // ...

                        // Recurse onto next page
                        if (listResult.getPageToken() != null) {
                            listAllPaginated(listResult.getPageToken());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Uh-oh, an error occurred.
            }
        });
    }*/

    private class DownloadFilesTask extends AsyncTask<Void, Integer, Void> {

        ProgressDialog pdLoading = new ProgressDialog(getContext());

        public DownloadFilesTask(Page<Blob> blobPage1) {
            blobPage=blobPage1;
        }

        public DownloadFilesTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(blobPage ==null)
            {
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            if(blobPage ==null)
            {
                String bucketName="waseet-ads-images-bh";
                InputStream is;
                GoogleCredentials credentials;
                try {
                    is = getActivity().getAssets().open("google-services.json");
                    credentials = GoogleCredentials.fromStream(is)
                            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(credentials)
                            .setStorageBucket(bucketName)
                            .build();
                    FirebaseApp.initializeApp(options);

                    Bucket bucket = StorageClient.getInstance().bucket();

                    blobPage = bucket.list();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (Blob blob : blobPage.iterateAll()) {
                URL signedUrl = blob.signUrl(14, TimeUnit.DAYS);
                arrayList.add(Arrays.asList(signedUrl.toString(), blob.getName()));
             //   System.out.println("blob.getName()"+" "+signedUrl.toString());
                publishProgress(null);
              }

            if(blobPage.hasNextPage()) {
                flag=true;
                blobPage = blobPage.getNextPage();
            }

            return null;
        }

        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            pdLoading.dismiss();
            txtvImages.setText(arrayList.size()+"\n"+getResources().getString(R.string.images));
            txtvRecord.setText(page+"\n"+getResources().getString(R.string.record));
           // System.out.println("array list: "+arrayList.size());
            if (imagesAdapter!= null) {
                imagesAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPostExecute(Void o) {

            pdLoading.dismiss();
            txtvImages.setText(arrayList.size()+"\n"+getResources().getString(R.string.images));
            txtvRecord.setText(page+"\n"+getResources().getString(R.string.record));
           // System.out.println("array list: "+arrayList.size());
            if (imagesAdapter!= null) {
                imagesAdapter.notifyDataSetChanged();
            }
            if(flag)
            {
                page++;
                txtvRecord.setText(page+"\n"+getResources().getString(R.string.record));
                DownloadFilesTask filesTask=new DownloadFilesTask(blobPage);
                filesTask.execute();
            }

            super.onPostExecute(o);
        }
    }

}
