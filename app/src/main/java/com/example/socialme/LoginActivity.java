package com.example.socialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    EditText emailID, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editText);
        password = findViewById(R.id.editText3);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        mAuthStateListener = firebaseAuth ->
        {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null)
            {
                Toast.makeText(LoginActivity.this, "You are logged in",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(LoginActivity.this, "Please Login",Toast.LENGTH_SHORT).show();
        };

        btnSignIn.setOnClickListener(v ->
        {
            String email = emailID.getText().toString();
            String pwd = password.getText().toString();
            if (email.isEmpty())
            {
                emailID.setError("Please enter your email");
                emailID.requestFocus();
            }
            else if (pwd.isEmpty())
            {
                password.setError("Please enter your password");
                password.requestFocus();
            }
            else
                {
                mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, task ->
                {
                    if(!task.isSuccessful())
                        Toast.makeText(LoginActivity.this,"Email or password are incorrect, please try again", Toast.LENGTH_SHORT).show();
                    else
                        {
                        Intent intToHome = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intToHome);
                    }
                });
            }
        });

        tvSignUp.setOnClickListener(v ->
        {
            Intent intSignUp = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intSignUp);
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
