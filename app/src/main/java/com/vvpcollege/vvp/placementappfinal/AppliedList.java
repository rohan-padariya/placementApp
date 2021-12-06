package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AppliedList extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mRef,company_ref;
    private String enroll,flag;
    public ArrayList<String> listAddress = new ArrayList<String>();

    ListView list_com_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_list);
        database=FirebaseDatabase.getInstance();
        getSupportActionBar().hide();


        list_com_app=(ListView)findViewById(R.id.list_com_app);

        Intent intent=getIntent();
        enroll=intent.getStringExtra("Enrollment");

        Log.v("appenroll",enroll);
        mRef=database.getReference().child("VVP").child("IT").child("Student").child(enroll);

        company_ref=database.getReference().child("VVP").child("IT").child("Company");


            //listAddress.add("ROHAN");


        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    if (dsp.getValue().equals("Yes")) {
                        listAddress.add(dsp.getKey());
                        //Toast.makeText(AppliedList.this, "in loop"+dsp.getKey(), Toast.LENGTH_SHORT).show();
                        //compadpter(dsp.getKey());
                        //Log.v("COMapdatersizeof", listAddress.size()+"");
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        CompanyAdapter companyAdapter=new CompanyAdapter(this,listAddress);
        list_com_app.setAdapter(companyAdapter);

    }
}
