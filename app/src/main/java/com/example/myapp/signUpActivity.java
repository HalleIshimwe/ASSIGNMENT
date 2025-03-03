package com.example.myapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.ComponentActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;





public class signUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         class SignUp extends ComponentActivity {
            private EditText nameEditText;
            private EditText emailEditText;
            private EditText usernameEditText;
            private EditText passwordEditText;
            private Button signUpButton;
            private EditText positionEditText;
            private EditText qualificationEditText;
            public DatabaseHelper dbHelper;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_sign_up);

                // Initialize UI components
                nameEditText = findViewById(R.id.fullName);
                emailEditText = findViewById(R.id.signupEmail);
                usernameEditText = findViewById(R.id.signupUsername);
                passwordEditText = findViewById(R.id.signupPassword);
                positionEditText = findViewById(R.id.Position);
                qualificationEditText = findViewById(R.id.Qualification);
                signUpButton = findViewById(R.id.signupButton);

                // Initialize database helper
                dbHelper = new DatabaseHelper(this);

                // Set click listener for Sign Up button
                signUpButton.setOnClickListener(view -> {
                    String name = nameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String position = positionEditText.getText().toString();
                    String qualification = qualificationEditText.getText().toString();

                    if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("name", name);
                        values.put("email", email);
                        values.put("username", username);
                        values.put("password", password);
                        values.put("position", position);
                        values.put("qualification", qualification);

                        long newRowId = db.insert("users", null, values);

                        if (newRowId == -1) {
                            Toast.makeText(SignUp.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        }
                        db.close(); }
                });
            }
        }
    }
}