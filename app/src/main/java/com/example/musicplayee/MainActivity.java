package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText userName,email,password;
    Button btnSignUp;
    TextView textView;
    LinearLayout userLine;
    int signUP=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLine=findViewById(R.id.usersLay);
        userName=findViewById(R.id.userName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnSignUp=findViewById(R.id.btnSignUp);
        textView=findViewById(R.id.textView);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,SongActivity.class));
            finish();
        }
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(signUP==1){
                    if(userName.getText().toString().trim().isEmpty()||email.getText().toString().trim().isEmpty()||
                    password.getText().toString().trim().isEmpty()){
                        Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString().trim(),
                                password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(new User(userName.getText().toString().trim(),
                                            email.getText().toString().trim(),password.getText().toString().trim(),"unchecked"));
                                    
                                    startActivity(new Intent(MainActivity.this,SongActivity.class));
                                    Toast.makeText(MainActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else if(signUP==0){
                    if(email.getText().toString().trim().isEmpty()||
                            password.getText().toString().trim().isEmpty()){
                        Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString().trim(),
                                password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(MainActivity.this,SongActivity.class));
                                    Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Something wrong ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUP==1){
                    signUP=0;
                    userLine.setVisibility(View.GONE);
                    btnSignUp.setText("Log In");
                    textView.setText("Don't have an account? Sign Up");

                }
                else if(signUP==0){
                    signUP=1;
                    userLine.setVisibility(View.VISIBLE);
                    btnSignUp.setText("Sign Up");
                    textView.setText("Already have account? Log In");

                }
            }
        });
    }
}