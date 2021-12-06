package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Faculty_student_company extends AppCompatActivity {

    ListView lst_applied_company,lst_notapplied_company;
    private String enroll,flag,name;
    public ArrayList<String> apply_com = new ArrayList<String>();
    public ArrayList<String> notapply_com = new ArrayList<String>();
    public ArrayList<String> notapply_com_reson = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference mRef,mRef2;
    TextView fac_stu_com_name_enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_student_company);
        getSupportActionBar().hide();

        lst_applied_company=(ListView)findViewById(R.id.lst_applied_company);
        lst_notapplied_company=(ListView)findViewById(R.id.lst_notapplied_company);
        fac_stu_com_name_enroll=(TextView)findViewById(R.id.fac_stu_com_name_enroll);

        database=FirebaseDatabase.getInstance();
        getSupportActionBar().setTitle("Student Companies");

        /*enroll="150470116044";*/
        Intent intent=getIntent();
        enroll=intent.getStringExtra("Enrollment");
        name=intent.getStringExtra("Name");
        flag=intent.getStringExtra("flag");
        fac_stu_com_name_enroll.setText(enroll+" | "+name);


        mRef=database.getReference().child("VVP").child("IT").child("Student").child(enroll).child("Apply");
        mRef2=database.getReference().child("VVP").child("IT").child("Student").child(enroll).child("NotApply");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    apply_com.add(dsp.getKey());

                    //Toast.makeText(Faculty_student_company.this, "Apply"+dsp.getKey(), Toast.LENGTH_SHORT).show();
                    //compadpter(dsp.getKey());
                    //Log.v("COMapdatersizeof", listAddress.size()+"");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    notapply_com.add(dsp.getKey());
                    notapply_com_reson.add(dsp.getValue().toString());
                    //Toast.makeText(Faculty_student_company.this, "Not Apply"+dsp.getKey(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        StudentAdapter studentAdapter=new StudentAdapter(Faculty_student_company.this,apply_com);
        lst_applied_company.setAdapter(studentAdapter);

        StudentNotAdapter studentNotAdapter=new StudentNotAdapter(Faculty_student_company.this,notapply_com,notapply_com_reson,enroll);
        lst_notapplied_company.setAdapter(studentNotAdapter);
    }
}
