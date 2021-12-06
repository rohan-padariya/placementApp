package com.vvpcollege.vvp.placementappfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class SpinnerClass extends AppCompatActivity {

    Spinner spr_list;
    String[] countryNames={"India","China","Australia","Portugle","America","New Zealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_class);

        spr_list=(Spinner)findViewById(R.id.spr_list);

        CustomAdapter customAdapter=new CustomAdapter(countryNames,SpinnerClass.this);
        spr_list.setAdapter(customAdapter);
    }
}
