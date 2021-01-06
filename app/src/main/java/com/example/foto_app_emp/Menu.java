package com.example.foto_app_emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    TextView razlaga, vaja, welcome;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        razlaga = findViewById(R.id.razlaga);
        vaja = findViewById(R.id.vaja);
        welcome = findViewById(R.id.welcome);

        welcome.setText("Dobrodosli, " + MainActivity.usernameS +"!");

        vaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVaja();
            }
        });

        razlaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRazlaga();
            }
        });
    }

    public void openVaja(){
        Intent intent = new Intent(this, Vaja.class);
        startActivity(intent);
    }

    public void openRazlaga(){
        Intent intent = new Intent(this, Razlaga.class);
        startActivity(intent);
    }
}
