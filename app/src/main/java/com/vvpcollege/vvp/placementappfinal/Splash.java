package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Splash extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseDatabase database;
    String flag;
    String u_id;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                          //  Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        *//*String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);*//*
                        Log.v("datais",token);
                        //Toast.makeText(Splash.this, token, Toast.LENGTH_SHORT).show();
                    }
                });*/

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar=(ProgressBar)findViewById(R.id.loader);

        progressBar.setVisibility(View.VISIBLE);

        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            u_id=mAuth.getCurrentUser().getUid();
            database=FirebaseDatabase.getInstance();
            mRef=database.getReference().child("User").child(u_id);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    flag = dataSnapshot.child("Flag").getValue().toString();

                    if(flag.equals("1")){
                        gopref();
                    }else if(flag.equals("7")){
                        gopass();
                    }else{
                        gohome();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }else{
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Current User", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Splash.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
    private void gopref(){
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(Splash.this, Prefrence.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("prefflag","1");
        startActivity(intent);
        finish();
    }
    private void gohome(){
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(Splash.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    private void gopass(){
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(Splash.this, Passcode.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
