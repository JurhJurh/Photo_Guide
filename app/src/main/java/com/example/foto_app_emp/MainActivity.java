package com.example.foto_app_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {

    public static String usernameS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView reg = (TextView) findViewById(R.id.registracijaText);
        final TextView login = (TextView) findViewById(R.id.loginText);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordS;

                usernameS = String.valueOf(username.getText());
                passwordS = String.valueOf(password.getText());

                if (!usernameS.equals("") && !passwordS.equals("")) {

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = usernameS;
                            data[1] = passwordS;
                            PutData putData = new PutData("http://192.168.1.12/FotoApp_RegisterLogin/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {
                                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                                        openMenu();
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                                    }
                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All files must be fullfield!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistration();
            }
        });

    }

    public void openRegistration(){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void openMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}