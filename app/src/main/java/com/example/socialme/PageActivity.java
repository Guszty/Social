package com.example.socialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PageActivity extends AppCompatActivity
{
    TextView title, date, description;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent i = getIntent();
        int id = i.getIntExtra("name", 5);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        ImageButton back = findViewById(R.id.bBack);
        back.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        title = findViewById(R.id.tTitle);
        date = findViewById(R.id.tDate);
        description = findViewById(R.id.tDescription);
        ref = FirebaseDatabase.getInstance().getReference().child("Member").child(String.valueOf(id));
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String sTitle = dataSnapshot.child("title").getValue().toString();
                String sDate = dataSnapshot.child("date").getValue().toString();
                String sDescription = dataSnapshot.child("description").getValue().toString();
                title.setText(sTitle);
                date.setText(sDate);
                description.setText(sDescription);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void open (View view) {
        Intent sendintent = new Intent ( Intent.ACTION_SEND);
        sendintent.setType("text/plain");
        sendintent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        sendintent.putExtra(Intent.EXTRA_TEXT, "Body of Email");
        startActivity(sendintent);

    }
}
