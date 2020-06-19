package com.example.socialme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class NewEventActivity extends AppCompatActivity
{

    EditText locationEditText, titleEditText, descriptionEditText;
    Button publish, backButton;
    TextView tvDate;
    DatabaseReference reff;
    Events member;

    int maxid = 0;
    private static final String TAG = "NewEventActivity";

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        //hooks to all elements in activity_main.xml
        locationEditText = findViewById(R.id.locationEditText);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        publish = (Button) findViewById(R.id.publish);
        tvDate = (TextView) findViewById(R.id.tvDate);
        member = new Events();

        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                    maxid = (int) dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        publish.setOnClickListener(view ->
        {
            member.setDate(tvDate.getText().toString().trim());
            member.setDescription(descriptionEditText.getText().toString().trim());
            member.setTitle(titleEditText.getText().toString().trim());
            member.setLocation(locationEditText.getText().toString().trim());

            reff.child(String.valueOf(maxid + 1)).setValue(member);

            Toast.makeText(NewEventActivity.this, "Data inserted succesfully", Toast.LENGTH_LONG).show();
        });

        // set the date picker
        tvDate.setOnClickListener(view ->
        {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    NewEventActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (view, year, month, dayOfMonth) ->
        {
            month = month + 1;
            Log.d(TAG, "onDataSet:dd/mm/yyy:" + dayOfMonth + "." + month + "." + year);
            String date = dayOfMonth + "." + month + "." + year;
            tvDate.setText(date);
        };

        //set the location picker
        Places.initialize(getApplicationContext(), "AIzaSyAyAiIOoSxPWHMU7O0kEWui4BewImJQZUo");

        locationEditText.setFocusable(false);
        locationEditText.setOnClickListener(view ->
        {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fieldList).build(NewEventActivity.this);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 100 && resultCode == RESULT_OK)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            locationEditText.setText(place.getAddress());

        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //set the Gmail SDK
    public void open(View view)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder()
                .append("<body><p>Content</p></body>")
                .toString()));
        sendIntent.setType("text/html");
        startActivity(sendIntent);

    }

}




