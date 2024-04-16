package com.example.group3signup;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView fname = findViewById(R.id.fname);
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);
        TextView reenter_password = findViewById(R.id.reenterpassword);
        Button signUp = findViewById(R.id.bSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test_fname = fname.getText().toString();
                String test_email = email.getText().toString();
                String test_password = password.getText().toString();
                String test_reenter_password = reenter_password.getText().toString();
                //Validate TextView if blank or empty
                if (TextUtils.isEmpty(test_fname) || TextUtils.isEmpty(test_email) || TextUtils.isEmpty(test_password)
                        || TextUtils.isEmpty(test_reenter_password) ) {
                    Toast.makeText(MainActivity.this, "Please fill up all fields!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                //Validate Password and Re-enter Passowrd
                if (password.getText().toString().equals(reenter_password.getText().toString())) {
                    // Create a new user
                    Map<String, Object> user = new HashMap<>();
                    user.put("fname", fname.getText().toString());
                    user.put("email", email.getText().toString());
                    user.put("password", password.getText().toString());

                    // Add a new document with a generated ID
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                    Toast.makeText(MainActivity.this, "Successfully Sign Up!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(MainActivity.this, "Sign Up Failed!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this,"Password does not match!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}