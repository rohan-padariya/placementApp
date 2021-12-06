package com.vvpcollege.vvp.placementappfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class FacultyHome extends AppCompatActivity {

    FirebaseAuth uAuth;
    //LinearLayout send_notification;
    ImageButton send_notification;
    //Button btn_stu;
    ImageButton btn_fac_logout,btn_stu;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference company_ref,stuREf,mRef;
    FloatingActionButton btn_fac_add_company,btn_about_us;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        getSupportActionBar().hide();
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "true";
                        if (!task.isSuccessful()) {
                            msg = "false";
                        }
                        Log.d("hello", msg);
                        // Toast.makeText(Splash.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        btn_fac_logout=(ImageButton)findViewById(R.id.btn_fac_logout);
        btn_fac_add_company=(FloatingActionButton)findViewById(R.id.btn_fac_add_company);
        btn_stu=(ImageButton) findViewById(R.id.btn_stu);
        btn_about_us = (FloatingActionButton)findViewById(R.id.aboutUsButton);
        //send_notification=(LinearLayout)findViewById(R.id.send_notification);
        send_notification=(ImageButton)findViewById(R.id.send_notification);

        recyclerView = (RecyclerView)findViewById(R.id.task_list_fac);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database=FirebaseDatabase.getInstance();
        company_ref=database.getReference().child("VVP").child("IT").child("Company");
        stuREf=database.getReference().child("VVP").child("IT").child("Student");
        mRef=database.getReference().child("VVP").child("IT").child("Student");


        uAuth = FirebaseAuth.getInstance();

        btn_fac_add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(FacultyHome.this,AddCompany.class);
                intent.putExtra("CHANGE","Add");
                intent.putExtra("cname","nothing");
                startActivity(intent);
            }
        });

        btn_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyHome.this ,about_us.class);
                startActivity(intent);
            }
        });

        btn_fac_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uAuth.signOut();
                Intent intent =new Intent(FacultyHome.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        btn_stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(FacultyHome.this,Faculty_student_search.class);
                startActivity(intent);
            }
        });

        send_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(FacultyHome.this,Notty.class);
                    startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Modal,AllCompany.ModalViewHolders> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Modal, AllCompany.ModalViewHolders>(
                Modal.class,R.layout.list_item, AllCompany.ModalViewHolders.class,company_ref
        ) {
            @Override
            protected void populateViewHolder(AllCompany.ModalViewHolders viewHolder, final Modal model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setDomain(model.getDomain());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setDomain1(model.getDomain1());
                viewHolder.setLocation1(model.getLocation1());
                viewHolder.setDate(model.getDate());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Home.this, "You clicked "+model.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(FacultyHome.this,Company.class);
                        intent.putExtra("Name",model.getName());
                        intent.putExtra("Enrollment","123");
                        startActivity(intent);
                    }
                });
                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder al=new AlertDialog.Builder(FacultyHome.this);
                        al.setMessage("You want delete this Company ??");
                        al.setTitle("Confirm Delete ....");

                        al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                company_ref.child(model.getName()).removeValue();
                               // mRef.child("150470116044").child("Apply").child(model.getName()).removeValue();
                                stuREf.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                            String key = ds.getKey();
                                            mRef.child(key).child("Apply").child(model.getName()).removeValue();
                                            mRef.child(key).child("NotApply").child(model.getName()).removeValue();
                                            //enrollstu.add(key+"");
                                            //Toast.makeText(Faculty_student_search.this, ""+enrollstu, Toast.LENGTH_SHORT).show();
                                            Log.d("TAGFaculty", key);
                                            //check(key,model.getName());
                                           // mRef.child(key).child(model.getName()).removeValue();
                                            /*String val=ds.child(key).child("Apply").getValue().toString();
                                            Log.d("TAGFaculty", val);*/
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                Toast.makeText(FacultyHome.this, "You deleted this file", Toast.LENGTH_SHORT).show();
                            }
                        });
                        al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        al.show();
                        return false;
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    //Adapter no chale to copy from allcompany activity ok ???????
}
