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

public class SignInActivity extends AppCompatActivity
{

    EditText emailID, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editText);
        password = findViewById(R.id.editText3);
        btnSignUp = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);
        btnSignUp.setOnClickListener(v ->
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
                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!task.isSuccessful())
                            Toast.makeText(SignInActivity.this, "This user already exists, please try again", Toast.LENGTH_SHORT).show();
                        else
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }
                });
            }
        });
        tvSignIn.setOnClickListener(v ->
        {
            Intent i = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}
