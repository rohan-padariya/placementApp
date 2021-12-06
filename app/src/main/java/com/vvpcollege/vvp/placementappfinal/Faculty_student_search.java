package com.vvpcollege.vvp.placementappfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Faculty_student_search extends AppCompatActivity {
    EditText edt_fac_stu_enroll;
    Button btn_fac_stu_search;
    String enroll;


    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference mRef,mRef2,company_ref;
    ProgressDialog progressDialog;

    public ArrayList<String> enrollstu = new ArrayList<>();

    ListView list_com_app_stu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_student_search);
        getSupportActionBar().hide();
        // edt_fac_stu_enroll=(EditText)findViewById(R.id.edt_fac_stu_enroll);
        // btn_fac_stu_search=(Button)findViewById(R.id.btn_fac_stu_search);
        //list_com_app_stu=(ListView)findViewById(R.id.list_com_app_stu);
        database=FirebaseDatabase.getInstance();

        company_ref=database.getReference().child("VVP").child("IT").child("Student");
        progressDialog=new ProgressDialog(Faculty_student_search.this);

        recyclerView = (RecyclerView)findViewById(R.id.all_lists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }



    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Modal,StudentViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Modal, StudentViewHolder>(
                Modal.class,R.layout.faculty_stu_list_item, StudentViewHolder.class,company_ref
        ) {


            @Override
            protected void populateViewHolder(StudentViewHolder viewHolder, final Modal model, int position) {


                viewHolder.setEnroll(model.getEnroll());
                viewHolder.setSname(model.getSname());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Faculty_student_search.this,Faculty_student_company.class);
                        intent.putExtra("Enrollment",model.getEnroll());
                        intent.putExtra("Name",model.getSname());
                        intent.putExtra("flag","1");
                        startActivity(intent);
                    }
                });
                Log.v("Count#######","#######");
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class StudentViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public StudentViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setEnroll(String enroll) {
            //txt_stu_fac_enroll
            TextView txt_stu_fac_enroll=(TextView)mView.findViewById(R.id.txt_stu_fac_enroll);
            txt_stu_fac_enroll.setText(enroll);
        }

        public void setSname(String sname) {
            //txt_stu_fac_enroll
            TextView txt_stu_fac_name=(TextView)mView.findViewById(R.id.txt_stu_fac_name);
            txt_stu_fac_name.setText(sname);
        }
    }
}
