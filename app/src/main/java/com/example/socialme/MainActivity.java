package com.example.socialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private DatabaseReference databaseReference;
    Button profileButton;
    Button newEventButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = findViewById(R.id.layout);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Member");
        int eventnumber ;

        newEventButton = findViewById(R.id.bNewEvent);
        newEventButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, NewEventActivity.class);
            startActivity(intent);
        });

        profileButton = findViewById(R.id.bProfile);
        profileButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        for (int i = 1; i < 5; i++)
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
