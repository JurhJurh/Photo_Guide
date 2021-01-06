package com.example.foto_app_emp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Razlaga extends AppCompatActivity {

    Button buttonToVaja;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.razlaga);

        buttonToVaja = findViewById(R.id.buttonToVaja);

        buttonToVaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVaja();
            }
        });
    }

    public void openVaja(){
        Intent intent = new Intent(this, Vaja.class);
        startActivity(intent);
    }
}
