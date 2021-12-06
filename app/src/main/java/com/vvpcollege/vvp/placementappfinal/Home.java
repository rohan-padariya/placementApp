package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Home extends AppCompatActivity {

    ImageButton signoutButt;
    ImageButton btn_apply_company,btn_company;
    private FirebaseAuth uAuth;
    DatabaseReference mRef,stu_ref,loop_ref;
    FirebaseDatabase database;
    String student_enrollment;
    String student_data;
    RecyclerView recyclerView;
    TextView txt_stu_name,txt_stu_enroll;
    Query query;
    String name;
    String sflag;
    int eye=0,nose=0;
    TextView txt_stu_pref_domain,txt_stu_pref_city;

    FloatingActionButton pref,btn_about_us;
    String s_city1,s_city2,s_domain1,s_domain2,timepass,s_domain3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        uAuth = FirebaseAuth.getInstance();
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

        signoutButt=(ImageButton) findViewById(R.id.signoutButt);
        pref=(FloatingActionButton)findViewById(R.id.pref);
        btn_about_us = (FloatingActionButton)findViewById(R.id.aboutUsButton);
        btn_company=(ImageButton) findViewById(R.id.btn_allcompany);
        txt_stu_enroll=(TextView)findViewById(R.id.txt_stu_enroll);
        txt_stu_name=(TextView)findViewById(R.id.txt_stu_name);
        btn_apply_company=(ImageButton) findViewById(R.id.btn_apply_company);
        txt_stu_pref_domain=(TextView)findViewById(R.id.txt_stu_pref_domain);
        txt_stu_pref_city=(TextView)findViewById(R.id.txt_stu_pref_city);

        recyclerView=(RecyclerView)findViewById(R.id.task_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        signoutButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        String u_id=uAuth.getCurrentUser().getUid();

        //Toast.makeText(this, "Login user id :  "+u_id, Toast.LENGTH_SHORT).show();

        database=FirebaseDatabase.getInstance();

        mRef=database.getReference().child("User").child(u_id);

        stu_ref=database.getReference().child("VVP").child("IT").child("Student");

        loop_ref=database.getReference().child("VVP").child("IT").child("Company");

        mRef.child("Flag").setValue("0");
        getStudentEnrollment();

        getStudentData();

       // getCompany();

        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Prefrence.class);
                intent.putExtra("homeflag","ok");
                intent.putExtra("prefflag","2");
                intent.putExtra("Enrollment",student_enrollment);
                startActivity(intent);
            }
        });

        btn_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this ,about_us.class);
                startActivity(intent);
            }
        });

        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,AllCompany.class);
                intent.putExtra("Enrollment",student_enrollment);
                startActivity(intent);
            }
        });

        btn_apply_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,AppliedList.class);
                intent.putExtra("Enrollment",student_enrollment);
               // intent.putExtra("flag","2");
                startActivity(intent);
            }
        });

        //txt_stu_enroll.setText(student_enrollment);
       // txt_stu_name.setText(name);
    }

    private void signOut(){
        FirebaseUser firebaseUser=uAuth.getCurrentUser();
        uAuth.signOut();
        Intent intent =new Intent(Home.this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void getStudentEnrollment(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student_enrollment = dataSnapshot.child("Enrollment").getValue().toString();
                txt_stu_enroll.setText(student_enrollment);
                //name=dataSnapshot.child("Name").getValue().toString();
                //Toast.makeText(Home.this, "OLD"+ student_enrollment, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //query = loop_ref.orderByChild("domain").equalTo("Android");

        final FirebaseRecyclerAdapter<Modal,ModalViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Modal, ModalViewHolder>(
                Modal.class,R.layout.list_item,ModalViewHolder.class,loop_ref
        ) {
            @Override
            protected void populateViewHolder( ModalViewHolder viewHolder, final Modal model, int position) {


               // eye=Integer.parseInt(sflag);
                nose=Integer.parseInt(model.getLflag());
               //Toast.makeText(Home.this, "this is int"+eye+"this is 2nd int "+nose, Toast.LENGTH_SHORT).show();

                if(model.getDomain().equalsIgnoreCase(s_domain1) ||model.getDomain().equalsIgnoreCase(s_domain2) || model.getDomain1().equalsIgnoreCase(s_domain1) ||model.getDomain1().equalsIgnoreCase(s_domain2) || model.getDomain1().equalsIgnoreCase(s_domain3) ||model.getDomain().equalsIgnoreCase(s_domain3)){
                    if(sflag.equals("1")){
                        if(nose==1 || nose==4 || nose==6){
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
                                    Intent intent=new Intent(Home.this,Company.class);
                                    intent.putExtra("Name",model.getName());
                                    intent.putExtra("Enrollment",student_enrollment);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            viewHolder.frameLayout.removeAllViews();
                        }
                    }else if(sflag.equals("3")){
                        if(nose==3 || nose==4 || nose==8){
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
                                    Intent intent=new Intent(Home.this,Company.class);
                                    intent.putExtra("Name",model.getName());
                                    intent.putExtra("Enrollment",student_enrollment);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            viewHolder.frameLayout.removeAllViews();
                        }
                    }else if(sflag.equals("5")){
                        if(nose==5 || nose==6 || nose==8){
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
                                    Intent intent=new Intent(Home.this,Company.class);
                                    intent.putExtra("Name",model.getName());
                                    intent.putExtra("Enrollment",student_enrollment);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            viewHolder.frameLayout.removeAllViews();
                        }
                    }else if(sflag.equals("4")){
                        if(nose==4 || nose==1 || nose==3 ||nose==6 || nose==8){
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
                                    Intent intent=new Intent(Home.this,Company.class);
                                    intent.putExtra("Name",model.getName());
                                    intent.putExtra("Enrollment",student_enrollment);
                                    startActivity(intent);
                                }
                            });
                        }
                    }else if(sflag.equals("8")){
                        if(nose==8 || nose==5 || nose==3 || nose==6 || nose==4){
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
                                    Intent intent=new Intent(Home.this,Company.class);
                                    intent.putExtra("Name",model.getName());
                                    intent.putExtra("Enrollment",student_enrollment);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            viewHolder.frameLayout.removeAllViews();
                        }
                    }else if(sflag.equals("6")){
                        if(nose==6 || nose==1 || nose==5 || nose==4 ||nose==8){
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
                                    Intent intent=new Intent(Home.this,Company.class);
                                    intent.putExtra("Name",model.getName());
                                    intent.putExtra("Enrollment",student_enrollment);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            viewHolder.frameLayout.removeAllViews();
                        }
                    }else if(sflag.equals("9")){
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
                                Intent intent=new Intent(Home.this,Company.class);
                                intent.putExtra("Name",model.getName());
                                intent.putExtra("Enrollment",student_enrollment);
                                startActivity(intent);
                            }
                        });
                    }else{

                    }
                }else{
                    viewHolder.frameLayout.removeAllViews();
                }
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }



    public static class ModalViewHolder extends RecyclerView.ViewHolder{
        LinearLayout frameLayout;
        View mView;
        public ModalViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            frameLayout= (LinearLayout) mView.findViewById(R.id.list);
        }


        public void setName(String name) {

            TextView txt_company=(TextView)mView.findViewById(R.id.txt_company);

            txt_company.setText(name);
        }

        public void setLocation(String location) {
            TextView txt_location=(TextView)mView.findViewById(R.id.txt_location);

            txt_location.setText(location);
        }

        public void setDomain(String domain) {
            TextView txt_domain=(TextView)mView.findViewById(R.id.txt_domain);
            txt_domain.setText(domain);
        }
        public void setDomain1(String domain1){
            TextView txt_domain=(TextView)mView.findViewById(R.id.txt_domain);
            txt_domain.append(", "+domain1);
        }

        public void setLocation1(String location1){
            TextView txt_location=(TextView)mView.findViewById(R.id.txt_location);
            txt_location.append(", "+location1);
        }

        public void setDate(String date){
            Button btn_date=(Button)mView.findViewById(R.id.date);
            btn_date.setText(date);
        }
        public void setLflag(String lflag){

        }
    }

    private void getStudentData() {

        stu_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student_data = dataSnapshot.child("7").child(student_enrollment).getKey();


                s_domain1 = dataSnapshot.child(student_enrollment).child("Domain1").getValue().toString();
                s_domain2 = dataSnapshot.child(student_enrollment).child("Domain2").getValue().toString();
                s_domain3 = dataSnapshot.child(student_enrollment).child("Domain3").getValue().toString();
                name = dataSnapshot.child(student_enrollment).child("sname").getValue().toString();
                sflag = dataSnapshot.child(student_enrollment).child("sflag").getValue().toString();
                Log.v("Homeclass",""+sflag);
                txt_stu_name.setText(name);
                txt_stu_pref_domain.setText(s_domain1+", "+s_domain2+", "+s_domain3);

                if(sflag.equals("1")){
                    txt_stu_pref_city.setText("Rajkot");
                }else if(sflag.equals("3")){
                    txt_stu_pref_city.setText("Out of Rajkot");
                }else if(sflag.equals("5")){
                    txt_stu_pref_city.setText("Out of Gujarat");
                }else if(sflag.equals("4")){
                    txt_stu_pref_city.setText("Rajkot,\nOut of Rajkot");
                }else if(sflag.equals("6")){
                    txt_stu_pref_city.setText("Rajkot, \nOut of Gujarat");
                }else if(sflag.equals("8")){
                    txt_stu_pref_city.setText("Out of Rajkot, \nOut of Gujarat");
                }else{
                    txt_stu_pref_city.setText("None, \n None");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
