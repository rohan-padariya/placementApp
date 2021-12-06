package com.vvpcollege.vvp.placementappfinal;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.FileProvider.getUriForFile;

public class Company extends AppCompatActivity {

    TextView txt_company_name,txt_company_location,txt_company_campus_date,txt_company_time,txt_company_position,txt_company_stipend,txt_company_jobtype;
    TextView txt_company_package,txt_company_bond,txt_company_lastdate,txt_company_venue,txt_company_qualification,txt_company_eligiblity,txt_company_domain;
    String c_name;
    Button btn_apply,btn_notapply,btn_intrestedstudents;
    ImageButton file_download;
    FirebaseDatabase database;
    DatabaseReference vRef,v2Ref,mRef,v3Ref;
    String stu_enroll;
    String reason,pdf_url;
    String d1,d2,d3,d4;
    FirebaseStorage storage;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        getSupportActionBar().hide();
       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        txt_company_stipend=(TextView)findViewById(R.id.txt_company_stipend);
        txt_company_time=(TextView)findViewById(R.id.txt_company_time);
        txt_company_jobtype=(TextView)findViewById(R.id.txt_company_jobtype);
        txt_company_name=(TextView)findViewById(R.id.txt_company_name);
        txt_company_location=(TextView)findViewById(R.id.txt_company_location);
        txt_company_campus_date=(TextView)findViewById(R.id.txt_company_campus_date);
        txt_company_position=(TextView)findViewById(R.id.txt_company_position);
        txt_company_bond=(TextView)findViewById(R.id.txt_company_bond);
        txt_company_lastdate=(TextView)findViewById(R.id.txt_company_lastdate);
        txt_company_venue=(TextView)findViewById(R.id.txt_company_venue);
        txt_company_package=(TextView)findViewById(R.id.txt_company_package);
        txt_company_qualification=(TextView)findViewById(R.id.txt_company_qualification);
        txt_company_eligiblity=(TextView)findViewById(R.id.txt_company_eligiblity);
        txt_company_domain=(TextView)findViewById(R.id.txt_company_domain);
        btn_apply=(Button)findViewById(R.id.btn_apply);
        btn_notapply=(Button)findViewById(R.id.btn_notapply);
        btn_intrestedstudents=(Button)findViewById(R.id.btn_intrestedstudents);
        file_download=(ImageButton)findViewById(R.id.file_download);
        context=this;

        database=FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        mRef=database.getReference().child("VVP").child("IT").child("Company");

        Intent intent=getIntent();
        c_name=intent.getStringExtra("Name");
        stu_enroll=intent.getStringExtra("Enrollment");
        txt_company_name.setText(c_name);

        ActivityCompat.requestPermissions(Company.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        if(stu_enroll.equals("123")){
            btn_notapply.setVisibility(View.GONE);
            btn_apply.setVisibility(View.GONE);
            file_download.setImageResource(R.mipmap.ic_circle_edit_outline_white_24dp);

            file_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(Company.this, "IT fucking worked !!!!!", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(Company.this,AddCompany.class);
                    intent1.putExtra("CHANGE","Edit");
                    intent1.putExtra("cname",c_name);
                    startActivity(intent1);
                }
            });
        }else{

            vRef=database.getReference().child("VVP").child("IT").child("Student").child(stu_enroll).child("Apply");
            v2Ref=database.getReference().child("VVP").child("IT").child("Student").child(stu_enroll).child("NotApply");
            v3Ref=database.getReference().child("VVP").child("IT").child("Company").child(c_name);
            v3Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    pdf_url=dataSnapshot.child(c_name+"PDF").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            file_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if(pdf_url.equals("No")){
                        Toast.makeText(Company.this, "No PDF was Uploaded", Toast.LENGTH_SHORT).show();
                    }else{
                        /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                        startActivity(browserIntent);*/
                        try{File file = new File(Environment.getExternalStoragePublicDirectory(Environment.getDataDirectory()+"/PlacementApp/files")+"/"+c_name+"PDF"+".pdf");
                            if(!file.exists()) {
                                Dowloadpdf(pdf_url,c_name+"PDF");
                            }else{
                                Openpdf(c_name+"PDF");
                            }
                        }catch(Exception e){

                            Toast.makeText(context, "Please grant storage permission", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(Company.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                        }

                    }

                }
            });
        }
        btn_intrestedstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Company.this,CompanyAppliedList.class);
                intent1.putExtra("cname",c_name);
                intent1.putExtra("value",stu_enroll);
                startActivity(intent1);
            }
        });


        //v3Ref.child(c_name).setValue("Seen");*/

        final Map<String, String> userData = new HashMap<String, String>();



        //vRef.child(stu_enroll).child(c_name).setValue("Null");
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // userData.put(c_name, "Yes");

                vRef.child(c_name).setValue("Yes");
                try{
                    v2Ref.child(c_name).removeValue();
                }catch (Exception e){
                    Toast.makeText(Company.this, "Child Not found", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(Company.this, "Response Saved", Toast.LENGTH_SHORT).show();
            }
        });

        btn_notapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Company.this);
                builder.setTitle("Your Reason");
                builder.setCancelable(false);
                View viewInflated = LayoutInflater.from(Company.this).inflate(R.layout.text_input_diag,(ViewGroup) findViewById(android.R.id.content), false);

                final EditText input = (EditText) viewInflated.findViewById(R.id.input);

                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        reason = input.getText().toString();
                        v2Ref.child(c_name).setValue(reason);
                        vRef.child(c_name).removeValue();
                        Log.v("reason",reason);
                        Toast.makeText(Company.this, "Response Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

               /* vRef.child(c_name).child("Reason").setValue(rad_but.getText());
                vRef.child(c_name).child("Apply").setValue("No");*/


            }
        });



    }

    public void Openpdf(String filename){

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.getDataDirectory()+"/PlacementApp/files")+"/"+c_name+"PDF"+".pdf");
        /*Toast.makeText(this, Environment.getExternalStoragePublicDirectory(Environment.getDataDirectory()+"/Plassy/files")+"/"+c_name+"PDF"+".pdf", Toast.LENGTH_SHORT).show();
        */
        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri contentUri = getUriForFile(getApplicationContext(), "com.vvpcollege.vvp.placementappfinal.fileprovider", file);

        target.setDataAndType(contentUri, "application/pdf");
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        Log.v("DATAIS",contentUri+"");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Company.this, "Download Pdf Viewer", Toast.LENGTH_SHORT).show();
        }
    }

    public void Dowloadpdf(String url,String filename){
        Toast.makeText(context, "Downloading.. File will open shorlty", Toast.LENGTH_SHORT).show();
        DownloadManager downloadmanager;
        Environment.getExternalStoragePublicDirectory(Environment.getDataDirectory()+"/PlacementApp/files/").mkdirs();
        downloadmanager = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle(filename)
                .setDestinationInExternalPublicDir(Environment.getDataDirectory()+"/PlacementApp/files/", filename+".pdf" )
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Log.v("Download1", String.valueOf(request));
        assert downloadmanager != null;
        downloadmanager.enqueue(request);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Openpdf(c_name+"PDF");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("IAS","hola");
                //Log.v("IAS",+"");
                d1=dataSnapshot.child(c_name).child("domain").getValue()+"";
                d2=dataSnapshot.child(c_name).child("domain1").getValue()+"";
                d3=dataSnapshot.child(c_name).child("location").getValue()+"";
                d4=dataSnapshot.child(c_name).child("location1").getValue()+"";
                txt_company_domain.setText(d1+","+d2);
                txt_company_location.setText(d3+", "+d4);
                txt_company_campus_date.setText(dataSnapshot.child(c_name).child("date").getValue()+"");
                txt_company_position.setText("Position : "+dataSnapshot.child(c_name).child("position").getValue().toString());
                txt_company_package.setText(dataSnapshot.child(c_name).child("package").getValue().toString());
                txt_company_bond.setText(dataSnapshot.child(c_name).child("bond").getValue().toString());
                txt_company_qualification.setText(dataSnapshot.child(c_name).child("qualification").getValue().toString());
                txt_company_eligiblity.setText(dataSnapshot.child(c_name).child("eligiblity").getValue().toString());
                txt_company_jobtype.setText(dataSnapshot.child(c_name).child("jobtype").getValue().toString());
                txt_company_stipend.setText(dataSnapshot.child(c_name).child("stipend").getValue().toString());
                txt_company_time.setText(dataSnapshot.child(c_name).child("reporttime").getValue().toString());
                txt_company_lastdate.setText("Last day to apply : "+dataSnapshot.child(c_name).child("lastdate").getValue().toString());
                txt_company_venue.setText(dataSnapshot.child(c_name).child("venue").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
