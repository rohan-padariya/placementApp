package com.vvpcollege.vvp.placementappfinal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyAdapter extends BaseAdapter {

    ArrayList<String> apply;
    Context context;
    DatabaseReference mRef;
    FirebaseDatabase database;
    String d1,d2,d3,d4;

    public ArrayList<String> getApply() {
        return apply;
    }

    public void setApply(ArrayList<String> apply) {
        this.apply = apply;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CompanyAdapter(Context context, ArrayList<String> apply) {
        this.context = context;
        this.apply=apply;
    }

    @Override
    public int getCount() {
        return apply.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        database= FirebaseDatabase.getInstance();
        mRef=database.getReference().child("VVP").child("IT").child("Company");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item, viewGroup, false);

        /*TextView apply_custom_list_com_name=(TextView)row.findViewById(R.id.apply_custom_list_com_name);
        apply_custom_list_com_name.setText(apply.get(i));

        View view_remove=(View)row.findViewById(R.id.view_remove);
        view_remove.setVisibility(View.GONE);

        TextView apply_custom_list_com_reason=(TextView)row.findViewById(R.id.apply_custom_list_com_reason);
        apply_custom_list_com_reason.setVisibility(View.GONE);
*/

        TextView txt_company = (TextView) row.findViewById(R.id.txt_company);
        final TextView txt_domain = (TextView) row.findViewById(R.id.txt_domain);
        final TextView txt_location = (TextView) row.findViewById(R.id.txt_location);
        final Button date=(Button)row.findViewById(R.id.date);
        txt_company.setText(apply.get(i));
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("IAS","hola");
                //Log.v("IAS",+"");
                d1=dataSnapshot.child(apply.get(i)).child("domain").getValue()+"";
                d2=dataSnapshot.child(apply.get(i)).child("domain1").getValue()+"";
                d3=dataSnapshot.child(apply.get(i)).child("location").getValue()+"";
                d4=dataSnapshot.child(apply.get(i)).child("location1").getValue()+"";
                txt_domain.setText(d1+", "+d2);
                txt_location.setText(d3+", "+d4);
                date.setText(dataSnapshot.child(apply.get(i)).child("date").getValue()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return row;

    }
}
