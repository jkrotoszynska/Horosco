package com.example.horosco.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.horosco.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ConstraintLayout signupButton;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupButton = findViewById(R.id.signupButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        ConstraintLayout loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(emailEditText.getText().toString()) && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password);
                    signupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        @Override
        protected void onStart() {
            super.onStart();
            //mAuth.addAuthStateListener(mAuthListener);

    }
}
