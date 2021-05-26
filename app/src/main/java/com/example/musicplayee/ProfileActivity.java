package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView userTaken,emailTaken;
    ProgressBar progBar;
    LinearLayout uLine,eLine;
    ImageView imageHome1,imageSearch1,imageProfile1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userTaken=findViewById(R.id.userTaken);
        emailTaken=findViewById(R.id.emailTaken);
        progBar=findViewById(R.id.prog);

        imageHome1=findViewById(R.id.imageHome1);
        imageSearch1=findViewById(R.id.imageSearch1);
        imageProfile1=findViewById(R.id.imageProfile1);
        eLine=findViewById(R.id.eLine);
        uLine=findViewById(R.id.uLine);
        progBar.setVisibility(View.VISIBLE);
        uLine.setVisibility(View.GONE);
        eLine.setVisibility(View.GONE);

        imageProfile1.setAlpha((float)1);
        imageSearch1.setAlpha((float).5);
        imageHome1.setAlpha((float).5);

        FirebaseDatabase.getInstance()
                .getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/userName")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progBar.setVisibility(View.GONE);
                userTaken.setText(snapshot.getValue().toString());
                uLine.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance()
                .getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/email")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progBar.setVisibility(View.GONE);
                        emailTaken.setText(snapshot.getValue().toString());
                        eLine.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        imageHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHome1.setAlpha((float)1);
                imageProfile1.setAlpha((float).5);
                imageHome1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageHome1.setAlpha((float) .5);
                    }
                },200);
                imageProfile1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProfile1.setAlpha((float) 1);
                    }
                },200);

                Intent intent=new Intent(ProfileActivity.this, SongActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSearch1.setAlpha((float)1);
                imageProfile1.setAlpha((float).5);
                imageSearch1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageSearch1.setAlpha((float) .5);
                    }
                },200);
                imageProfile1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProfile1.setAlpha((float) 1);
                    }
                },200);
                Intent intent=new Intent(ProfileActivity.this,SearchActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}