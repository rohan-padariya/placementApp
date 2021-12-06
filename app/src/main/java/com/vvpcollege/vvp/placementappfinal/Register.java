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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private static final String TAG = "Main";

    EditText enroll,email,pass1,pass2;
    Button signupButt,Verify;
    private FirebaseAuth uAuth;
    private DatabaseReference dAuth,mRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    LinearLayout sloginButt;
    FirebaseDatabase database;
    String i="1";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        enroll = (EditText) findViewById(R.id.senroll);
        email = (EditText) findViewById(R.id.semail);
        pass1 = (EditText) findViewById(R.id.spass1);
        pass2 = (EditText) findViewById(R.id.spass2);
        signupButt = (Button) findViewById(R.id.signupButt);
        sloginButt=(LinearLayout) findViewById(R.id.sloginButt);
        //Verify=(Button)findViewById(R.id.Verify);
        progressDialog=new ProgressDialog(this);

        database=FirebaseDatabase.getInstance();
        uAuth = FirebaseAuth.getInstance();
        dAuth = FirebaseDatabase.getInstance().getReference().child("User");

        mRef=database.getReference().child("VVP").child("IT").child("Student");


        signupButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        sloginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_to_Login=new Intent(Register.this,Login.class);
                back_to_Login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back_to_Login);
            }
        });


    }

    private void startRegister() {

        progressDialog.setMessage("Validating");
        progressDialog.show();

        final String emal = email.getText().toString().trim();
        final String enrl = enroll.getText().toString().trim();
        final String pwd1 = pass1.getText().toString().trim();
        final String pwd2 = pass2.getText().toString().trim();


        if (!TextUtils.isEmpty(emal) && !TextUtils.isEmpty(enrl) && !TextUtils.isEmpty(pwd1)) {

            if(pwd1.equals(pwd2) && pwd1.length()>5 && pwd2.length()>5){


            uAuth.createUserWithEmailAndPassword(emal, pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                        String u_id = uAuth.getCurrentUser().getUid();
                        Log.v("REgister",""+u_id);
                       /*Aya change karje error ave to
                        DatabaseReference currentUserDB = dAuth.child(u_id);
                        currentUserDB.child("Enrollment").setValue(enrl);
                        currentUserDB.child("Flag").setValue(i);
                        comment remove karine dAuth vadi line delete(109-110)
                        */
                        dAuth.child(u_id).child("Enrollment").setValue(enrl);
                        dAuth.child(u_id).child("Flag").setValue(i);
                        mRef.child(enrl).child("email").setValue(emal);
                        //Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                        /*Intent loginIntent = new Intent(signupActivity.this, loginActivity.class);
                        startActivity(loginIntent);*/
                    }else{
                        Toast.makeText(Register.this, "Error (email might be taken or wrong)", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
            }else{
                Toast.makeText(this, "Password not matched (>6)", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }

    private void sendEmailVerification(){
        final FirebaseUser firebaseUser=uAuth.getCurrentUser();
        if (firebaseUser != null) {

            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.setMessage("Email Verification Sent...");
                        Toast.makeText(Register.this, "Verification Send to your Email", Toast.LENGTH_SHORT).show();
                        uAuth.signOut();
                        finish();
                        Intent back_to_Login=new Intent(Register.this,Login.class);
                        back_to_Login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(back_to_Login);
                        finish();
                        progressDialog.dismiss();

                    }else{
                        Toast.makeText(Register.this, "Error in Verification", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }


}