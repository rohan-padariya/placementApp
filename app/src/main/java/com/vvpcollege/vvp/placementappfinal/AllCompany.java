package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllCompany extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference company_ref;
    String stu_enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_company);
        getSupportActionBar().hide();
        /*getSupportActionBar().setTitle("All Companies");*/
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)*/;

        recyclerView = (RecyclerView)findViewById(R.id.all_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database=FirebaseDatabase.getInstance();
        company_ref=database.getReference().child("VVP").child("IT").child("Company");

        Intent intent=getIntent();

        stu_enroll=intent.getStringExtra("Enrollment");


    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Modal,ModalViewHolders>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Modal, ModalViewHolders>(
                Modal.class,R.layout.list_item, ModalViewHolders.class,company_ref
        ) {
            @Override
            protected void populateViewHolder(ModalViewHolders viewHolder, final Modal model, int position) {

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
                        Intent intent=new Intent(AllCompany.this,Company.class);
                        intent.putExtra("Name",model.getName());
                        intent.putExtra("Enrollment",stu_enroll);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class ModalViewHolders extends RecyclerView.ViewHolder{
        View mView;
        public ModalViewHolders(View itemView) {
            super(itemView);
            mView=itemView;
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
    }
}
