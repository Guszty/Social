package com.example.socialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference reff;
    Button profileButton;
    Button newEventButton;
    int maxid = 0;
    boolean logedIn = false;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseAuth mFirebaseAuth;
    DataSnapshot snapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = findViewById(R.id.layout);
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        mAuthStateListener = firebaseAuth ->
        {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null)
            {
                logedIn = true;
            }
            else
                logedIn = false;
        };

        if(logedIn)
            profileButton.setText("Sign up");
        else
            profileButton.setText("Log out");

        newEventButton = findViewById(R.id.bNewEvent);
        newEventButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, NewEventActivity.class);
            startActivity(intent);
        });

        profileButton = findViewById(R.id.bProfile);
        profileButton.setOnClickListener(v ->
        {
            if (logedIn)
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        for (int i = 1; i < maxid; i++)
        {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            button.setId(i);
            button.setText("Button" + i);
            int finalI = i;
            button.setOnClickListener(v ->
            {
                Intent intent = new Intent(this, PageActivity.class);
                intent.putExtra("name", finalI);
                startActivity(intent);
                finish();
            });
            ll.addView(button);
        }

    }

    public void count(DataSnapshot snapshot)
    {

    }

}
