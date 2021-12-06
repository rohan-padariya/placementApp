package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Passcode extends AppCompatActivity {

    Button checkCode;
    EditText secCode;
    private String passcode;
    private FirebaseDatabase dbRef;
    private DatabaseReference codeRef;
    FirebaseAuth mAuth;
    String u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();

        u_id=mAuth.getCurrentUser().getUid();
        Log.v("pass",u_id);
        dbRef=FirebaseDatabase.getInstance();
        codeRef=dbRef.getReference().child("User").child(u_id);

        dbRef = FirebaseDatabase.getInstance();
        checkCode = (Button) findViewById(R.id.checkCode);
        secCode = (EditText) findViewById(R.id.secCode);

        codeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                passcode = dataSnapshot.child("passkey").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = secCode.getText().toString();
                if(code.equals(passcode)){
                    Toast.makeText(Passcode.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Passcode.this, FacultyHome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Passcode.this, "Wrong Passcode", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
