package com.example.myapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText usernameEditText;
        EditText passwordEditText;
        Button loginButton;
        TextView signUpTextView;
        DatabaseHelper dbHelper;

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signupLink);

        //initialize database helper
        dbHelper = new DatabaseHelper(this);

        //set onClick listner for login button
        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Pease enter passwprd and username", Toast.LENGTH_SHORT).show();
            }
            else {
                //sql integration code starts here
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(
                        "users",
                        new String[]{"id"},
                        "username=? AND password=?",
                        new String[]{username,password},
                null,null,null
                );
                if (cursor != null && cursor.moveToFirst()) {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show(); }
                else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show(); }
                if (cursor != null) { cursor.close(); } db.close();
            }
        });

        //onClickListener for signUp textView
        signUpTextView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, signUpActivity.class);
            startActivity(intent);
        });


    }
}