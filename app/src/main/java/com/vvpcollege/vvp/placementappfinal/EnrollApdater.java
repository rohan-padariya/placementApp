package com.vvpcollege.vvp.placementappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EnrollApdater extends BaseAdapter {

    ArrayList<String> enad;
    Context context;
    TextView textView;

    public EnrollApdater(ArrayList<String> enad,Context context) {
        this.enad=enad;
        this.context=context;
    }

    @Override
    public int getCount() {
        return enad.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.spinner_layout,viewGroup,false);
        TextView textView=(TextView)view.findViewById(R.id.txt_spinner);
        textView.setText(enad.get(i));
        return view;
    }
}
