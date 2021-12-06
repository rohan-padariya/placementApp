package com.vvpcollege.vvp.placementappfinal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CompanyAppliedList extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mRef;
    ListView studentwhoapplied;
    String company,tempcompany,check;
    AppliedCompanyAdater appliedCompanyAdater;
    ImageButton downloadfilestudent;
    LinearLayout removebutton;


    public ArrayList<String> apply_com = new ArrayList<String>();
    public ArrayList<String> student_enroll = new ArrayList<String>();
    public ArrayList<String> CPI = new ArrayList<String>();
    public ArrayList<String> CGPA = new ArrayList<String>();
    public ArrayList<String> BACK = new ArrayList<String>();
    public ArrayList<String> MOB = new ArrayList<String>();
    public ArrayList<String> D2D = new ArrayList<String>();
    public ArrayList<String> th10 = new ArrayList<String>();
    public ArrayList<String> th12 = new ArrayList<String>();
    public ArrayList<String> email = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_applied_list);
        getSupportActionBar().hide();



        downloadfilestudent=(ImageButton)findViewById(R.id.downloadfilestudent);
        removebutton=(LinearLayout)findViewById(R.id.removebutton);
        database=FirebaseDatabase.getInstance();

        apply_com.add("CHANGE");




        Intent intent=getIntent();
        company =intent.getStringExtra("cname");
        check =intent.getStringExtra("value");


        studentwhoapplied=(ListView)findViewById(R.id.studentwhoapplied);

        mRef=database.getReference().child("VVP").child("IT").child("Student");

        appliedCompanyAdater=new AppliedCompanyAdater(apply_com,CompanyAppliedList.this);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                for (DataSnapshot itemsnapshot:dataSnapshot.getChildren()){

                    //Toast.makeText(CompanyAppliedList.this, ""+itemsnapshot.getValue(), Toast.LENGTH_SHORT).show();
                    if (itemsnapshot.getKey().equals("Apply")){
                        String name=dataSnapshot.child("sname").getValue().toString();
                        String enroll=dataSnapshot.child("enroll").getValue().toString();
                        String CPIs=dataSnapshot.child("CPI").getValue().toString();
                        String CGPAs=dataSnapshot.child("CGPA").getValue().toString();
                        String BACKs=dataSnapshot.child("Backlogs").getValue().toString();
                        String MOBs=dataSnapshot.child("mobile").getValue().toString();
                        String D2Dd=dataSnapshot.child("D2D").getValue().toString();
                        String t10=dataSnapshot.child("10th").getValue().toString();
                        String t12=dataSnapshot.child("12th").getValue().toString();
                        String emails=dataSnapshot.child("email").getValue().toString();
                        //Toast.makeText(CompanyAppliedList.this, "Name is "+dataSnapshot.child("sname").getValue(), Toast.LENGTH_SHORT).show();
                        for (DataSnapshot itemsnapshots:itemsnapshot.getChildren()){
                            tempcompany=itemsnapshots.getKey();
                            if(company.equals(tempcompany)){
                               // Toast.makeText(CompanyAppliedList.this, ""+itemsnapshots.getKey()+"Apply by "+name, Toast.LENGTH_SHORT).show();
                                apply_com.add(name);
                                student_enroll.add(enroll);
                                CPI.add(CPIs);
                                CGPA.add(CGPAs);
                                BACK.add(BACKs);
                                MOB.add(MOBs);
                                D2D.add(D2Dd);
                                th10.add(t10);
                                th12.add(t12);
                                email.add(emails);
                                studentwhoapplied.setAdapter(appliedCompanyAdater);
                            }
                        }
                        //Toast.makeText(CompanyAppliedList.this, ""+itemsnapshot.child("Apply").getValue(), Toast.LENGTH_SHORT).show();
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

        if(check.equals("123")){
            downloadfilestudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ActivityCompat.requestPermissions(CompanyAppliedList.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    try {

                        Toast.makeText(CompanyAppliedList.this, "File Created to PlacementAppStudentData..", Toast.LENGTH_SHORT).show();
                        exportEmailInCSV();
                    } catch (IOException e) {
                        Toast.makeText(CompanyAppliedList.this, "ERROR", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }else{
            removebutton.setVisibility(View.GONE);
        }



    }

    public void exportEmailInCSV() throws IOException {
        {

            File folders = new File(Environment.getExternalStorageDirectory()
                    + "/PlacementAppStudentData");


            /*previous data
            File folders = new File(Environment.getExternalStorageDirectory()
                    + "/PlassyStudentData");
*/


            boolean var = false;
            if (!folders.exists()){
                var = folders.mkdir();
            }

            Log.v("datais:",var+"");



            final String filename = folders.toString() + "/" + company+".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    CompanyAppliedList.this, contentTitle, "Creating file",
                    true);//please wait


            new Thread() {
                public void run() {
                    try {

                        FileWriter fw = new FileWriter(filename);



                        fw.append("Name");
                        fw.append(',');

                        fw.append("Enrollment");
                        fw.append(',');

                        fw.append("Mobile");
                        fw.append(',');

                        fw.append("Email");
                        fw.append(',');

                        fw.append("CPI");
                        fw.append(',');

                        fw.append("CGPA");
                        fw.append(',');

                        fw.append("10th");
                        fw.append(',');

                        fw.append("12th");
                        fw.append(',');

                        fw.append("Backlogs");
                        fw.append(',');

                        fw.append("D2D");
                        fw.append(',');

                        fw.append('\n');

                        for (int i=0;i<student_enroll.size();i++){
                            /*mRef.child(student_enroll.get(i)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });*/
                            fw.append(apply_com.get(i));
                            fw.append(',');
                            fw.append(student_enroll.get(i));
                            fw.append(',');
                            fw.append(MOB.get(i));
                            fw.append(',');
                            fw.append(email.get(i));
                            fw.append(',');
                            fw.append(CPI.get(i));
                            fw.append(',');
                            fw.append(CGPA.get(i));
                            fw.append(',');
                            fw.append(th10.get(i));
                            fw.append(',');
                            fw.append(th12.get(i));
                            fw.append(',');
                            fw.append(BACK.get(i));
                            fw.append(',');
                            fw.append(D2D.get(i));
                            fw.append('\n');

                        }



                        fw.close();
                        Toast.makeText(CompanyAppliedList.this, company+".csv is saved successfully", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                    }
                   // handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                }
            }.start();

        }

    }




    @Override
    protected void onStart() {
        appliedCompanyAdater=new AppliedCompanyAdater(apply_com,CompanyAppliedList.this);
        studentwhoapplied.setAdapter(appliedCompanyAdater);
        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
