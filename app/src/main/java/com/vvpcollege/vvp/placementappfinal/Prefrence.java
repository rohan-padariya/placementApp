package com.vvpcollege.vvp.placementappfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Prefrence extends AppCompatActivity {

    private FirebaseAuth uAuth;
    Button btn_next;
    Spinner spn_city1,spn_city2,spn_domain1,spn_domain2,spn_domain3;
    FirebaseDatabase database;
    DatabaseReference mRef,stu_EnrollRef,stu_ref;
    String city1_sel,city2_sel,domain1_sel,domain2_sel,domain3_sel;
    String d1,d2,d3,l1,l2;
    String student_enroll,enroll;
    String prefflag;
    ProgressDialog progressDialog;

    ArrayList<String> city=new ArrayList<String>();
    ArrayList<String> domain=new ArrayList<String>();
    ArrayList<String> domain1=new ArrayList<String>();

    int sflag=0,f1,f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefrence);

        getSupportActionBar().hide();
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        progressDialog=new ProgressDialog(this);

        Intent intent=getIntent();
        prefflag=intent.getStringExtra("prefflag");
        enroll=intent.getStringExtra("Enrollment");

        btn_next=(Button)findViewById(R.id.btn_next);
        spn_city1=(Spinner)findViewById(R.id.spn_city1);
        spn_city2=(Spinner)findViewById(R.id.spn_city2);
        spn_domain1=(Spinner)findViewById(R.id.spn_domain1);
        spn_domain2=(Spinner)findViewById(R.id.spn_domain2);
        spn_domain3=(Spinner)findViewById(R.id.spn_domain3);

        String u_id=FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Toast.makeText(this, ""+u_id, Toast.LENGTH_SHORT).show();

        database=FirebaseDatabase.getInstance();

        stu_EnrollRef=database.getReference().child("User").child(u_id);

        stu_ref=database.getReference().child("VVP").child("IT").child("Student");

        mRef=database.getReference().child("VVP").child("IT").child("Company");



        stu_EnrollRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student_enroll=dataSnapshot.child("Enrollment").getValue().toString();

                //Toast.makeText(Prefrence.this, "Enroll is"+student_enroll, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(prefflag.equals("2")){
            stu_ref.child(enroll).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    d1=dataSnapshot.child("Domain1").getValue().toString();
                    d2=dataSnapshot.child("Domain2").getValue().toString();
                    d3=dataSnapshot.child("Domain3").getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        city.add("None");
        city.add("Rajkot");
        city.add("Out of Gujarat");
        city.add("Out of Rajkot");
        domain.add("iOS");
        domain.add("Networking");
        domain.add("Testing");
        domain.add("Designing");
        domain.add("PHP");
        domain.add(".NET");
        domain.add("Java");
        domain.add("Android");
        domain.add("SEO");
        domain.add("BDE");

        final ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_city1.setAdapter(adapter1);
        spn_city2.setAdapter(adapter1);

        final ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,domain);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_domain1.setAdapter(adapter2);
        spn_domain2.setAdapter(adapter2);
        spn_domain3.setAdapter(adapter2);


        spn_city1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city1_sel=adapterView.getItemAtPosition(i).toString();
                if(city1_sel.equals("None")){
                    f1=0;
                }else if(city1_sel.equals("Rajkot")){
                    f1=1;
                }else if(city1_sel.equals("Out of Gujarat")){
                    f1=5;
                }else{
                    f1=3;
                }
                //Toast.makeText(Prefrence.this, "sflag "+sflag, Toast.LENGTH_SHORT).show();
                // Toast.makeText(Prefrence.this, "City 1"+city1_sel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city2_sel=adapterView.getItemAtPosition(i).toString();


                if(city1_sel.equals(city2_sel) || city2_sel.equals("None")){
                    f2=0;
                }else if(city2_sel.equals("Rajkot")){
                    f2=1;
                }else if(city2_sel.equals("Out of Gujarat")) {
                    f2=5;
                }else{
                    f2=3;
                }
                sflag=f1+f2;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_domain1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                domain1_sel=adapterView.getItemAtPosition(i).toString();
              //  Toast.makeText(Prefrence.this, "domain 1"+domain1_sel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_domain2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                domain2_sel=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(Prefrence.this, "domain 2"+domain2_sel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_domain3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                domain3_sel=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Saving your data");
                progressDialog.setCancelable(false);
                progressDialog.show();

                stu_ref.child(student_enroll).child("Domain1").setValue(domain1_sel);
                stu_ref.child(student_enroll).child("Domain2").setValue(domain2_sel);
                stu_ref.child(student_enroll).child("Domain3").setValue(domain3_sel);
                stu_ref.child(student_enroll).child("sflag").setValue(sflag+"");
               // Toast.makeText(Prefrence.this, "S flag is ---"+sflag, Toast.LENGTH_SHORT).show();
                stu_EnrollRef.child("Flag").setValue(0+"");





                        Intent intent = new Intent(Prefrence.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();

               // }

            }
        });


        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot itemsnapshot:dataSnapshot.getChildren()){
                    String c1,c2,d1,d2;

                    if(itemsnapshot.getKey().equals("domain")){
                        String val=itemsnapshot.getValue(String.class);
                        if (!domain.contains(val)) {
                            domain.add(val);
                        }
                        //domain.add(val);
                        adapter2.notifyDataSetChanged();
                    }
                    if(itemsnapshot.getKey().equals("domain1")){
                        String val=itemsnapshot.getValue(String.class);
                        if (!domain.contains(val)) {
                            domain.add(val);
                        }
                        adapter2.notifyDataSetChanged();
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
}
