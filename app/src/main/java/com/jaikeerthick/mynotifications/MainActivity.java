package com.jaikeerthick.mynotifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaikeerthick.mynotifications.adapter.NotificationAdapter;
import com.jaikeerthick.mynotifications.model.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MyListener {

    Handler handler = new Handler();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;

    NotificationAdapter adapter;
    private List<Notification> mNotifications;

    String package_noti, title_noti, text_noti, ID_noti;

    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_main_collapse);
        collapsingToolbarLayout.setTitle("My Notifications");

        recyclerView = findViewById(R.id.recyclerView_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("notifications");

        mNotifications= new ArrayList<>();
        adapter = new NotificationAdapter(MainActivity.this, mNotifications);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        new MyNotificationListener().setListener(this);
        WorkerThread1 workerThread1 = new WorkerThread1();
        new Thread(workerThread1).start();


    }


    @Override
    public void setValue(String packageName, String tittle, String text,String ID) {

        package_noti = packageName;
        title_noti = tittle;
        text_noti = text;
        ID_noti = ID;
        WorkerThread workerThread = new WorkerThread();
        new Thread(workerThread).start();
    }

    public class WorkerThread implements Runnable{

        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {


                    Notification notification = new Notification(package_noti, title_noti, text_noti, ID_noti);

                    String uploadId = databaseReference.push().getKey();

                    databaseReference.child(uploadId).setValue(notification).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error occurred -firebase", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });

        }
    }

    public class WorkerThread1 implements Runnable{

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                    Query query = databaseReference.orderByChild("packageNameNoti");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            mNotifications.clear();


                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Notification notification = postSnapshot.getValue(Notification.class);
                                notification.setKey(postSnapshot.getKey());
                                mNotifications.add(notification);

                            }
                            Collections.reverse(mNotifications);
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();


                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}