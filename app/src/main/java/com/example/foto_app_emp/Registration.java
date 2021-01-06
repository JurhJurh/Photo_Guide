package com.example.foto_app_emp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Registration extends AppCompatActivity {

    EditText username, password, email, password1;
    TextView register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        getSupportActionBar().setTitle("Registracija");

        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        password1 = findViewById(R.id.reg_password1);
        email = findViewById(R.id.reg_email);

        register = findViewById(R.id.loginText);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String usernameS, passwordS, emailS, password1S;

                usernameS = String.valueOf(username.getText());
                passwordS = String.valueOf(password.getText());
                emailS = String.valueOf(email.getText());

                password1S = String.valueOf(password1.getText());

                if (!passwordS.equals(password1S)){
                    System.out.println(passwordS + " " + password1S);
                    Toast.makeText(Registration.this, "Gesli se neujemata!",Toast.LENGTH_LONG).show();
                    return;
                }

                if (!usernameS.equals("") && !passwordS.equals("") && !emailS.equals("")) {


                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[3];
                            field[0] = "username";
                            field[1] = "password";
                            field[2] = "email";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = usernameS;
                            data[1] = passwordS;
                            data[2] = emailS;
                            PutData putData = new PutData("http://192.168.1.12/FotoApp_RegisterLogin/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(Registration.this, result, Toast.LENGTH_LONG).show();
                                        backOnLogin();
                                        finish();
                                    } else {
                                        Toast.makeText(Registration.this, result, Toast.LENGTH_LONG).show();
                                    }
                                    Log.i("PutData", result);
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All files must be fullfield!",Toast.LENGTH_SHORT);
                }

            }
        });


    }

    public void backOnLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
