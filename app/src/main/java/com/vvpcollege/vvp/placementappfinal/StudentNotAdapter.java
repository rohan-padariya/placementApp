package com.vvpcollege.vvp.placementappfinal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentNotAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> notapply;
    ArrayList<String> notapplyreson;
    String enroll;

    public ArrayList<String> getNotapplyreson() {
        return notapplyreson;
    }

    public void setNotapplyreson(ArrayList<String> notapplyreson) {
        this.notapplyreson = notapplyreson;
    }

    public StudentNotAdapter() {

        super();
    }

    public Context getContext() {
        return context;
    }


    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String> getNotapply() {
        return notapply;
    }

    public void setNotapply(ArrayList<String> notapply) {
        this.notapply = notapply;
    }

    public StudentNotAdapter(Context context, ArrayList<String> notapply,ArrayList<String> notapplyreson,String enroll) {

        this.context = context;
        this.notapply = notapply;
        this.notapplyreson=notapplyreson;
        this.enroll=enroll;

    }

    @Override

    public int getCount() {
        return notapply.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.faculty_stu_detail_list_item, viewGroup, false);

        TextView apply_custom_list_com_name=(TextView)row.findViewById(R.id.apply_custom_list_com_name);
        apply_custom_list_com_name.setText(notapply.get(i));
        Log.v("Studentnot",""+notapply.get(i));

        TextView apply_custom_list_com_reason=(TextView)row.findViewById(R.id.apply_custom_list_com_reason);
        apply_custom_list_com_reason.setText(notapplyreson.get(i));

        return row;
    }
}
