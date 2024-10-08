//package com.example.newtryout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "MainActivity";
//    private EditText emailEditText;
//    private EditText passwordEditText;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Initialize the EditText and Button
//        emailEditText = findViewById(R.id.emailEditText);
//        passwordEditText = findViewById(R.id.passwordEditText);
//        Button signInButton = findViewById(R.id.sendLinkButton);
//        TextView createAccountTextView = findViewById(R.id.createAccountTextView);
//
//        // Set an OnClickListener on the Sign-In button
//        signInButton.setOnClickListener(v -> {
//            String email = emailEditText.getText().toString().trim();
//            String password = passwordEditText.getText().toString().trim();
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
//            } else {
//                signIn(email, password);
//            }
//        });
//
//        // Set an OnClickListener on the Create Account text to navigate to the registration page
//        createAccountTextView.setOnClickListener(v -> {
//            Toast.makeText(MainActivity.this, "Navigating to Register Page", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
//        });
//    }
//
//    private void signIn(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        Toast.makeText(MainActivity.this, "Authentication successful.",
//                                Toast.LENGTH_SHORT).show();
//                        // Proceed to the next activity or update the UI
//                        startActivity(new Intent(MainActivity.this, ChatActivity.class));
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.getException());
//                        Toast.makeText(MainActivity.this, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}
package com.example.newtryout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button EmailButton = findViewById(R.id.EmailButton);
        Button MobileButton = findViewById(R.id.MobileButton);
        TextView createAccountTextView = findViewById(R.id.createAccountTextView);

        // Set an OnClickListener on the Sign-In button
        EmailButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Navigating to Email Page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, EmailActivity.class));

        });
        MobileButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Navigating to Mobile Page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MobileActivity.class));
        });

        // Set an OnClickListener on the Create Account text to navigate to the registration page
        createAccountTextView.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Navigating to Register Page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });
    }
}

