package com.vvpcollege.vvp.placementappfinal;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText eml;
    EditText pass;
    Button loginButt;
    private FirebaseAuth uAuth;
    LinearLayout go_signup,txt_forgotpass;
    DatabaseReference mRef,stu_ref;
    FirebaseDatabase database;
    String flag;
    ProgressDialog progressDialog;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(this);
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        eml = (EditText) findViewById(R.id.eml);
        pass = (EditText) findViewById(R.id.pass);
        loginButt = (Button) findViewById(R.id.loginButt);
        go_signup=(LinearLayout) findViewById(R.id.lsignupButt);
        txt_forgotpass=(LinearLayout)findViewById(R.id.txt_forgotpass);

        uAuth = FirebaseAuth.getInstance();


            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        FirebaseUser firebaseUser=uAuth.getCurrentUser();
                        Boolean emailflag=firebaseUser.isEmailVerified();
                        if(emailflag){
                            //Toast.makeText(Login.this, "Logged in ", Toast.LENGTH_SHORT).show();
                            checkPref();

                            /*Intent intent = new Intent(Login.this, Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();*/
                        }else{
                            //Toast.makeText(Login.this, "Not logged in", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //Toast.makeText(Login.this, "Not logged in", Toast.LENGTH_SHORT).show();
                    }
                }
            };


        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Validating");
                progressDialog.setCancelable(false);
                progressDialog.show();
                checkLogin();
            }
        });

        go_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //startActivity(new Intent(Login.this,Register.class));
                Intent intent = new Intent(Login.this, Register.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();

                //startActivity(new Intent(Login.this,Register.class));
                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
            }
        });

        txt_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });
    }

    @Override
    protected void onStart() {
        uAuth.addAuthStateListener(mAuthListener);

        super.onStart();

    }


    private void checkLogin() {

        final String email = eml.getText().toString().trim();
        String password = pass.getText().toString().trim();

               if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                   uAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkfacultystudent();
                        Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(Login.this,Home.class));
                    } else {
                        Toast.makeText(Login.this, "Error Login", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void checkfacultystudent(){
        final String u_id=uAuth.getCurrentUser().getUid();

        database=FirebaseDatabase.getInstance();

        mRef=database.getReference().child("User").child(u_id);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flag = dataSnapshot.child("Flag").getValue().toString();

                if(flag.equals("7")){
                    Log.v("Facutly",u_id);
                    Intent intent = new Intent(Login.this, Passcode.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }else{
                    checkEmailVerification();

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void checkEmailVerification(){

        FirebaseUser firebaseUser=uAuth.getCurrentUser();


        Boolean emailflag=firebaseUser.isEmailVerified();

        /*String u_id=uAuth.getCurrentUser().getUid();

        database=FirebaseDatabase.getInstance();

        mRef=database.getReference().child("User").child(u_id);

        Toast.makeText(this, "Login"+u_id, Toast.LENGTH_SHORT).show();*/


        if(emailflag){
            checkPref();
            /*Intent intent = new Intent(Login.this, Home.class);

            startActivity(intent);*/

        }else{
            Toast.makeText(this, "Verify your Email", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            uAuth.signOut();
        }
    }

    private void checkPref(){

        String u_id=uAuth.getCurrentUser().getUid();

        database=FirebaseDatabase.getInstance();

        mRef=database.getReference().child("User").child(u_id);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               flag = dataSnapshot.child("Flag").getValue().toString();

               if(flag.equals("1")){
                   gopref();

               }else{
                   gohome();

               }


                /*if(flag.equals("1")){
                    Intent intent = new Intent(Login.this, Prefrence.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void gopref(){
        Intent intent = new Intent(Login.this, Prefrence.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("prefflag","1");
        startActivity(intent);
        finish();
        progressDialog.dismiss();
    }
    private void gohome(){
        Intent intent = new Intent(Login.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        progressDialog.dismiss();
    }


}
