package com.vvpcollege.vvp.placementappfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText edt_email;
    Button btn_forgotpass;
    String email;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/


        edt_email=(EditText)findViewById(R.id.edt_email);
        btn_forgotpass=(Button)findViewById(R.id.btn_forgotpass);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=edt_email.getText().toString().trim();

                if (email.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter email address", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Email sent for reset password request", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this,Login.class));
                            }else{
                                Toast.makeText(ForgotPassword.this, "Error sending email might be wrong email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
