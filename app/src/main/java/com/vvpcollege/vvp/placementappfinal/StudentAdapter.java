package com.vvpcollege.vvp.placementappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    ArrayList<String> apply;
    ArrayList<String> notapply;

    public StudentAdapter(Context context,ArrayList<String> apply) {
        this.context = context;
        this.apply=apply;

    }

    Context context;
    String enroll;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.faculty_stu_detail_list_item, viewGroup, false);

        TextView apply_custom_list_com_name=(TextView)row.findViewById(R.id.apply_custom_list_com_name);
        apply_custom_list_com_name.setText(apply.get(i));

        View view_remove=(View)row.findViewById(R.id.view_remove);
        view_remove.setVisibility(View.GONE);

        TextView apply_custom_list_com_reason=(TextView)row.findViewById(R.id.apply_custom_list_com_reason);
        apply_custom_list_com_reason.setVisibility(View.GONE);

        return row;
    }
}
