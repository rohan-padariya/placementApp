package com.vvpcollege.vvp.placementappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AppliedCompanyAdater extends BaseAdapter {
    ArrayList<String> apply;
    Context context;

    public AppliedCompanyAdater(ArrayList<String> apply, Context context) {
        this.apply = apply;
        this.context = context;
    }

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

    public AppliedCompanyAdater() {
        super();

    }

    @Override
    public int getCount() {
        return apply.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.faculty_stu_detail_list_item, parent, false);

        if (!apply.get(position).equals("CHANGE")){
            TextView apply_custom_list_com_name=(TextView)row.findViewById(R.id.apply_custom_list_com_name);
            apply_custom_list_com_name.setText(apply.get(position));

            View view_remove=(View)row.findViewById(R.id.view_remove);
            view_remove.setVisibility(View.GONE);

            TextView apply_custom_list_com_reason=(TextView)row.findViewById(R.id.apply_custom_list_com_reason);
            apply_custom_list_com_reason.setVisibility(View.GONE);
        }else{
            apply.remove(position);
            notifyDataSetChanged();
        }


        return row;
    }
}
