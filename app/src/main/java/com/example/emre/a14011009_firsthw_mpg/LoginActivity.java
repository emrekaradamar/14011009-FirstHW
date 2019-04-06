package com.example.emre.a14011009_firsthw_mpg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText username_ET, password_ET;
    Button login_Button;
    Intent takeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_ET = (EditText)findViewById(R.id.username_ET);
        password_ET = (EditText)findViewById(R.id.password_ET);
        login_Button = (Button)findViewById(R.id.login_Button);
        takeInfo = new Intent(LoginActivity.this, TakeInformationsActivity.class);

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username_ET.getText().toString().equals("admin") && password_ET.getText().toString().equals("password")){
                    startActivity(takeInfo);
                }
            }
        });
    }
}
