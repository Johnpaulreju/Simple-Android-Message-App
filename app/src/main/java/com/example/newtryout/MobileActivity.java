package com.example.newtryout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileActivity extends AppCompatActivity {

    private EditText mobileNumberEditText;
    private EditText verificationCodeEditText;
    private Button sendVerificationButton;
    private Button verifyButton;

    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        sendVerificationButton = findViewById(R.id.sendVerificationButton);
        verifyButton = findViewById(R.id.verifyButton);

        // Send verification code button click
        sendVerificationButton.setOnClickListener(v -> {
            String mobileNumber = mobileNumberEditText.getText().toString().trim();
            if (TextUtils.isEmpty(mobileNumber)) {
                Toast.makeText(MobileActivity.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            } else {
                sendVerificationCode(mobileNumber);
            }
        });

        // Verify code button click
        verifyButton.setOnClickListener(v -> {
            String code = verificationCodeEditText.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                Toast.makeText(MobileActivity.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
            } else {
                verifyCode(code);
            }
        });
    }

    private void sendVerificationCode(String mobileNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + mobileNumber)       // Phone number to verify, assuming +91 for India, adjust accordingly
                        .setTimeout(60L, TimeUnit.SECONDS)         // Timeout and unit
                        .setActivity(this)                        // Activity (for callback binding)
                        .setCallbacks(mCallbacks)                 // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(MobileActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("MobileActivity", "Verification Failed", e);
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    MobileActivity.this.verificationId = verificationId;

                    // Show the verification code input field and the verify button
                    verificationCodeEditText.setVisibility(View.VISIBLE);
                    verifyButton.setVisibility(View.VISIBLE);

                    Toast.makeText(MobileActivity.this, "Verification code sent.", Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MobileActivity.this, "Mobile Verified Successfully", Toast.LENGTH_SHORT).show();
                        // Proceed to the next activity or update UI accordingly
                    } else {
                        Toast.makeText(MobileActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
