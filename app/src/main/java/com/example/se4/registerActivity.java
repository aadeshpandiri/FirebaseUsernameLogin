package com.example.se4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registerActivity extends AppCompatActivity {

    EditText e1,e2,e3;
    Button bt1,bt2;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1= (EditText)findViewById(R.id.etregname);
        e2 = (EditText)findViewById(R.id.etregpass);
        e3 = (EditText)findViewById(R.id.etregpass2);

        database = FirebaseDatabase.getInstance();


    }

    public void gotosignin(View view) {
        Intent i1 = new Intent(registerActivity.this,MainActivity.class);
        startActivity(i1);
    }

    public void RegisterNow(View view) {
        final String username = e1.getText().toString();
        final String password = e2.getText().toString();
        final String confirmpassword = e3.getText().toString();

        ref=database.getReference().child("Accounts");
        if(!username.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty())
        {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child(username).exists())
                    {
                        Toast.makeText(registerActivity.this, "Username already exists create new account", Toast.LENGTH_SHORT).show();
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                    }
                    else
                    {
                        if(password.equals(confirmpassword))
                        {
                            ref.child(username).child("Username").setValue(username);
                            ref.child(username).child("Password").setValue(password);
                            ref.child(username).child("confirm Password").setValue(confirmpassword);

                            Toast.makeText(registerActivity.this, "Account Registered Successfully", Toast.LENGTH_SHORT).show();
                            e1.setText("");
                            e2.setText("");
                            e3.setText("");
                        }
                        else
                        {
                            Toast.makeText(registerActivity.this, "Passwords not matched", Toast.LENGTH_SHORT).show();

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(this, "some fields are empty", Toast.LENGTH_SHORT).show();
        }




    }
}