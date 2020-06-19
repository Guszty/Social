package com.example.socialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference databaseReference;
    ImageButton profileButton;
    Button newEventButton;
    int nrOfEvents = 0;
    boolean logedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = findViewById(R.id.layout);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Member");
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                    nrOfEvents = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

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
            }
            else
            {
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        for (int i = 1; i < nrOfEvents; i++)
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
            });
            ll.addView(button);
        }

    }

}
