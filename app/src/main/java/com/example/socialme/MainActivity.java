package com.example.socialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference databaseReference;
    private List<Events> eventsList = new ArrayList<>();
    Button eventButton;
    Button newEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = findViewById(R.id.layout);

        eventButton = findViewById(R.id.bEvent);
        eventButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, PageActivity.class);
            intent.putExtra("name", 1);
            startActivity(intent);
        });

        newEventButton = findViewById(R.id.bNewEvent);
        newEventButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, NewEventActivity.class);
            startActivity(intent);
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                eventsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Events event = postSnapshot.getValue(Events.class);
                    eventsList.add(event);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        for (int i = 1; i < eventsList.size(); i++)
        {
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            button.setId(i);
            button.setText("Button" + i);
            ll.addView(button);
        }

    }

}
