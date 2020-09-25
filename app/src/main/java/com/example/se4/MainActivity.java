package com.example.se4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView tv1,tv2;
    EditText et1,et2;
    Button bt1,bt2;
    FirebaseDatabase database;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tvname);
        tv2 = (TextView) findViewById(R.id.tvpass);

        et1 = (EditText) findViewById(R.id.etname);
        et2 = (EditText) findViewById(R.id.etpass);

        bt1 = (Button) findViewById(R.id.btnlogin);
        bt2 = (Button)findViewById(R.id.btnregister);

        database = FirebaseDatabase.getInstance();


    }


    public void LoginMethod(View view) {
        final String name = et1.getText().toString();
        final String pass = et2.getText().toString();
        myref = database.getReference().child("Accounts");
        if(!name.isEmpty() && !pass.isEmpty())
        {
            myref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(name).exists())
                    {
                        String datapass = dataSnapshot.child(name).child("Password").getValue().toString();
                        if(datapass.equals(pass))
                        {
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(MainActivity.this,HomeActivity.class);

                            startActivity(i);
                            et1.setText("");
                            et2.setText("");


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Invalid Details", Toast.LENGTH_LONG).show();
                            et1.setText("");
                            et2.setText("");

                        }

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Enter Complete Details", Toast.LENGTH_SHORT).show();


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

    public void RegisterMethod(View view) {
        Intent i = new Intent(MainActivity.this,registerActivity.class);
        startActivity(i);


    }
}