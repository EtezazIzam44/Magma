package com.etezaz.assessment_task_magma;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.etezaz.assessment_task_magma.ui.adapter.FragmentImages;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.linear_layout, new FragmentImages())
                .commit();

     /*   try {
            //MongoClient mongoClient = new MongoClient("10.1.1.1", 27017);//new MongoClient("mongodb://10.1.1.1:27017");
            MongoClient mongoClient = new MongoClient("mongodb://35.222.197.123", 47692);
            //String dbURI = "mongodb://35.222.197.123:27017";
           // MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));

            DB db = mongoClient.getDB("waseet_data");
            Log.d("LOG_TAG", "DBName = " + db.getName());

            Set<String> collections = db.getCollectionNames();
            for (String colName : collections) {
                Log.d("LOG_TAG", "colName = " + colName);
                System.out.println("\t + Collection: " + colName);
            }

            mongoClient.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("LOG_TAG", "exceptionMongo = " + e);
        }
    }*/
    }

  /*  public void listAllPaginated(@Nullable String pageToken) {
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
    }
*/
}