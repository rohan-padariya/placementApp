package com.vvpcollege.vvp.placementappfinal;

import android.Manifest;
import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class AddCompany extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference comRef, reference;
    Calendar c;

    int restoreStipend = 0, restoreBond = 0, restoreJobtype = 0;
    String spbond, spstipend, spjob;
    String date, time, format, flag, sd1, sd2, sloc1, sloc2,lastdate;
    String tag = "Date picker";
    EditText edt_fac_com_name, edt_fac_com_package, edt_fac_com_position, edt_fac_com_eligiblity, edt_fac_com_loc1, edt_fac_com_loc2;
    EditText edt_fac_com_qualification,edt_fac_com_lastdate, edt_fac_com_date, edt_fac_com_time, edt_fac_com_venue, edt_fac_com_domain1, edt_fac_com_domain2;
    LinearLayout btn_fac_save;
    String fac_name, fac_pack, fac_bond, fac_position, fac_elig, fac_qual, fac_stipend, fac_venue;
    AppCompatCheckBox edt_fac_com_raj, edt_fac_com_outraj, edt_fac_com_gujout,check_pdf;
    public ArrayList<String> response = new ArrayList<String>();
    public ArrayList<String> tech = new ArrayList<String>();
    public ArrayList<String> Stipend = new ArrayList<String>();
    Spinner edt_fac_com_bond, edt_fac_com_stipend, edt_fac_com_jobtype;
    String flagvalue;
    String edit_time, edit_date;
    Button pdf_uploadButt;
    Uri pdfUri;
    FirebaseStorage storage;
    StorageReference store;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        getSupportActionBar().hide();
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        edt_fac_com_name = (EditText) findViewById(R.id.edt_fac_com_name);
        edt_fac_com_package = (EditText) findViewById(R.id.edt_fac_com_package);
        edt_fac_com_bond = (Spinner) findViewById(R.id.edt_fac_com_bond);
        edt_fac_com_position = (EditText) findViewById(R.id.edt_fac_com_position);
        edt_fac_com_eligiblity = (EditText) findViewById(R.id.edt_fac_com_eligiblity);
        edt_fac_com_lastdate = (EditText) findViewById(R.id.edt_fac_com_lastdate);
        edt_fac_com_qualification = (EditText) findViewById(R.id.edt_fac_com_qualification);
        edt_fac_com_date = (EditText) findViewById(R.id.edt_fac_com_date);
        edt_fac_com_time = (EditText) findViewById(R.id.edt_fac_com_time);
        edt_fac_com_stipend = (Spinner) findViewById(R.id.edt_fac_com_stipend);
        edt_fac_com_venue = (EditText) findViewById(R.id.edt_fac_com_venue);
        btn_fac_save = (LinearLayout) findViewById(R.id.btn_fac_save);
        edt_fac_com_gujout = (AppCompatCheckBox) findViewById(R.id.edt_fac_com_gujout);
        edt_fac_com_outraj = (AppCompatCheckBox) findViewById(R.id.edt_fac_com_outraj);
        edt_fac_com_raj = (AppCompatCheckBox) findViewById(R.id.edt_fac_com_raj);
        check_pdf = (AppCompatCheckBox) findViewById(R.id.check_pdf);
        edt_fac_com_jobtype = (Spinner) findViewById(R.id.edt_fac_com_jobtype);
        edt_fac_com_domain1 = (EditText) findViewById(R.id.edt_fac_com_domain1);
        edt_fac_com_domain2 = (EditText) findViewById(R.id.edt_fac_com_domain2);
        edt_fac_com_loc1 = (EditText) findViewById(R.id.edt_fac_com_loc1);
        edt_fac_com_loc2 = (EditText) findViewById(R.id.edt_fac_com_loc2);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        store = storage.getReference();
        pdf_uploadButt = (Button) findViewById(R.id.pdf_uploadButt);

        database = FirebaseDatabase.getInstance();
        comRef = database.getReference().child("VVP").child("IT").child("Company");

        Intent intent = getIntent();
        String edit = intent.getStringExtra("CHANGE");
        String cname = intent.getStringExtra("cname");

        if (edit.equals("Edit")) {

            comRef.child(cname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    edt_fac_com_name.setText(dataSnapshot.child("name").getValue().toString());
                    edt_fac_com_date.setText(dataSnapshot.child("date").getValue().toString());
                    edt_fac_com_time.setText(dataSnapshot.child("reporttime").getValue().toString());
                    flagvalue = dataSnapshot.child("lflag").getValue().toString();
                    edt_fac_com_package.setText(dataSnapshot.child("package").getValue().toString());
                    edt_fac_com_eligiblity.setText(dataSnapshot.child("eligiblity").getValue().toString());
                    edt_fac_com_domain1.setText(dataSnapshot.child("domain1").getValue().toString());
                    edt_fac_com_domain2.setText(dataSnapshot.child("domain").getValue().toString());
                    edt_fac_com_loc1.setText(dataSnapshot.child("location").getValue().toString());
                    edt_fac_com_loc2.setText(dataSnapshot.child("location1").getValue().toString());
                    edt_fac_com_qualification.setText(dataSnapshot.child("qualification").getValue().toString());
                    edt_fac_com_venue.setText(dataSnapshot.child("venue").getValue().toString());
                    edt_fac_com_position.setText(dataSnapshot.child("position").getValue().toString());
                    edt_fac_com_lastdate.setText(dataSnapshot.child("lastdate").getValue().toString());
                    lastdate=dataSnapshot.child("lastdate").getValue().toString();
                    date=dataSnapshot.child("date").getValue().toString();
                    time=dataSnapshot.child("reporttime").getValue().toString();
                    edit_date = dataSnapshot.child("date").getValue().toString();
                    edit_time = dataSnapshot.child("reporttime").getValue().toString();
                    String bond = dataSnapshot.child("bond").getValue().toString();

                    String stipend = dataSnapshot.child("stipend").getValue().toString();
                    String jobtype = dataSnapshot.child("jobtype").getValue().toString();
                    if (bond.equals("No")) {
                        edt_fac_com_bond.setSelection(1);
                    } else if (bond.equals("Yes")) {
                        edt_fac_com_bond.setSelection(2);
                    } else {
                        edt_fac_com_bond.setSelection(0);
                    }
                    if (stipend.equals("No")) {
                        edt_fac_com_stipend.setSelection(1);
                    } else if (stipend.equals("Yes")) {
                        edt_fac_com_stipend.setSelection(2);
                    } else {
                        edt_fac_com_stipend.setSelection(0);
                    }
                    if (jobtype.equals("Technical")) {
                        edt_fac_com_jobtype.setSelection(1);
                    } else if (jobtype.equals("Non Technical")) {
                        edt_fac_com_jobtype.setSelection(2);
                    } else {
                        edt_fac_com_jobtype.setSelection(0);
                    }


                    if (flagvalue.equals("1")) {
                        edt_fac_com_raj.setChecked(true);
                    } else if (flagvalue.equals("3")) {
                        edt_fac_com_outraj.setChecked(true);
                    } else if (flagvalue.equals("5")) {
                        edt_fac_com_gujout.setChecked(true);
                    } else if (flagvalue.equals("4")) {
                        edt_fac_com_raj.setChecked(true);
                        edt_fac_com_outraj.setChecked(true);
                    } else if (flagvalue.equals("6")) {
                        edt_fac_com_gujout.setChecked(true);
                        edt_fac_com_raj.setChecked(true);
                    } else if (flagvalue.equals("8")) {
                        edt_fac_com_gujout.setChecked(true);
                        edt_fac_com_outraj.setChecked(true);
                    } else {
                        edt_fac_com_raj.setChecked(true);
                        edt_fac_com_outraj.setChecked(true);
                        edt_fac_com_gujout.setChecked(true);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
        }
        response.add("Bond");
        response.add("No");
        response.add("Yes");
        tech.add("Job Type");
        tech.add("Technical");
        tech.add("Non Technical");
        Stipend.add("Stipend");
        Stipend.add("No");
        Stipend.add("Yes");
        c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int min = c.get(Calendar.MINUTE);
        //setTimeFormat(hour);

        edt_fac_com_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddCompany.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        edt_fac_com_date.setText(i2 + "/" + i1 + "/" + i);
                        date = i2 + "/" + i1 + "/" + i;
                        Toast.makeText(AddCompany.this, "" + date, Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        edt_fac_com_lastdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddCompany.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        edt_fac_com_lastdate.setText(i2 + "/" + i1 + "/" + i);
                        lastdate = i2 + "/" + i1 + "/" + i;
                        Toast.makeText(AddCompany.this, "" + lastdate, Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });




        edt_fac_com_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddCompany.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        int i3 = setTimeFormat(i);
                        time = i3 + ":" + i1 + " " + format;
                        edt_fac_com_time.setText(time);
                        Toast.makeText(AddCompany.this, "" + time, Toast.LENGTH_SHORT).show();
                    }
                }, hour, min, true);
                timePickerDialog.show();
            }
        });

        edt_fac_com_jobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spjob = adapterView.getItemAtPosition(i).toString();
                restoreJobtype = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        edt_fac_com_bond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spbond = adapterView.getItemAtPosition(i).toString();
                restoreBond = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_fac_com_stipend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spstipend = adapterView.getItemAtPosition(i).toString();
                restoreStipend = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pdf_uploadButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddCompany.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPDF();
                } else
                    ActivityCompat.requestPermissions(AddCompany.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
        });
    }

    public int setTimeFormat(int hour) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {

            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        return hour;
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Stipend);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        edt_fac_com_stipend.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, response);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        edt_fac_com_bond.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tech);
        adapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        edt_fac_com_jobtype.setAdapter(adapter3);


        btn_fac_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = 0, j = 0, k = 0;
                fac_name = edt_fac_com_name.getText().toString().trim();
                fac_pack = edt_fac_com_package.getText().toString().trim();
                //fac_bond=edt_fac_com_bond.getText().toString();
                fac_position = edt_fac_com_position.getText().toString().trim();
                fac_elig = edt_fac_com_eligiblity.getText().toString().trim();
                fac_qual = edt_fac_com_qualification.getText().toString().trim();
                //fac_stipend=edt_fac_com_stipend.getText().toString();
                fac_venue = edt_fac_com_venue.getText().toString().trim();
                sd1 = edt_fac_com_domain1.getText().toString().trim();
                sd2 = edt_fac_com_domain2.getText().toString().trim();
                sloc1 = edt_fac_com_loc1.getText().toString().trim();
                sloc2 = edt_fac_com_loc2.getText().toString().trim();
                lastdate = edt_fac_com_lastdate.getText().toString().trim();

                if (edt_fac_com_raj.isChecked()) {
                    i = 1;
                }
                if (edt_fac_com_gujout.isChecked()) {
                    j = 5;
                }
                if (edt_fac_com_outraj.isChecked()) {
                    k = 3;
                }
                flag = i + j + k + "";


                //if (pdfUri != null) {
                    if (date != null && time != null && !fac_name.equals("") && !fac_qual.equals("") && !fac_pack.equals("") && !fac_elig.equals("") && !fac_position.equals("") && !fac_venue.equals("") && !sd1.equals("") && !sd2.equals("") && !sloc1.equals("") && !sloc2.equals("") ) {

                            comRef.child(fac_name).child("name").setValue(fac_name);
                            comRef.child(fac_name).child("package").setValue(fac_pack);
                            comRef.child(fac_name).child("position").setValue(fac_position);
                            comRef.child(fac_name).child("eligiblity").setValue(fac_elig);
                            comRef.child(fac_name).child("bond").setValue(spbond);
                            comRef.child(fac_name).child("qualification").setValue(fac_qual);
                            comRef.child(fac_name).child("date").setValue(date);
                            comRef.child(fac_name).child("reporttime").setValue(time);
                            comRef.child(fac_name).child("stipend").setValue(spstipend);
                            comRef.child(fac_name).child("jobtype").setValue(spjob);
                            comRef.child(fac_name).child("venue").setValue(fac_venue);
                            comRef.child(fac_name).child("lflag").setValue(flag);
                            comRef.child(fac_name).child("domain").setValue(sd1);
                            comRef.child(fac_name).child("domain1").setValue(sd2);
                            comRef.child(fac_name).child("location").setValue(sloc1);
                            comRef.child(fac_name).child("location1").setValue(sloc2);
                            comRef.child(fac_name).child("lastdate").setValue(lastdate);
                            if(check_pdf.isChecked()){
                                if (pdfUri != null){
                                    uploadFile(pdfUri);

                                }else{
                                    Toast.makeText(AddCompany.this, "Select a file", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                if(pdfUri!=null){
                                    Toast.makeText(AddCompany.this, "Please select checkbox to upload", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(AddCompany.this, "No pdf uploaded", Toast.LENGTH_SHORT).show();
                                    comRef.child(fac_name).child(fac_name+"PDF").setValue("No");
                                    finish();
                                }
                            }

                            // || fac_name!=null || fac_qual !=null || fac_pack!=null || fac_elig!=null || fac_position!=null || fac_venue!=null || sd1!=null || sd2!=null || sloc1!=null || sloc2!=null ) {
                        }


                     else {
                        Toast.makeText(AddCompany.this, "Some date is missing", Toast.LENGTH_SHORT).show();
                    }

                /*} else {
                    Toast.makeText(AddCompany.this, "Select a file", Toast.LENGTH_SHORT).show();
                }

*/

            }
        });


    }

    private void uploadFile(Uri pdfUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String fileName = fac_name + "PDF";///[storage ma fileName]+[RealtimeDatabase ma child name]--change this to company name
        final StorageReference pdfstorageReference = storage.getReference().child("CompanyDetailPdfs").child(fileName);  ///[storage ma folder/file name]
        pdfstorageReference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pdfstorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downUrl = uri.toString();

                                reference = database.getReference().child("VVP/IT/Company").child(fac_name);
                                reference.child(fileName).setValue(downUrl).addOnCompleteListener(new OnCompleteListener<Void>() {     //[RealtimeDatabase reference to append child of (file name + location)]-- put into company name child
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddCompany.this, "Data is successfully uploaded", Toast.LENGTH_SHORT).show();
                                            progressDialog.hide();
                                            finish();
                                        } else {
                                            Toast.makeText(AddCompany.this, "Data is not successfully uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCompany.this, "File is not successfully uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        } else {
            Toast.makeText(this, "Please Provide Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
        } else {
            Toast.makeText(this, "Please select file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        if (restoreBond != 0) {
            edt_fac_com_bond.setSelection(restoreBond);
        }
        if (restoreStipend != 0) {
            edt_fac_com_stipend.setSelection(restoreStipend);
        }
        if (restoreJobtype != 0) {
            edt_fac_com_jobtype.setSelection(restoreJobtype);
        }
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Stipend", edt_fac_com_stipend.getSelectedItemPosition());
        outState.putInt("Bond", edt_fac_com_bond.getSelectedItemPosition());
        outState.putInt("Jobtype", edt_fac_com_jobtype.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            try {
                edt_fac_com_stipend.setSelection(savedInstanceState.getInt("Stipend", 0));
                edt_fac_com_bond.setSelection(savedInstanceState.getInt("Stipend", 0));
                edt_fac_com_jobtype.setSelection(savedInstanceState.getInt("Stipend", 0));
            } catch (Exception e) {
                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
