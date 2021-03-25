package com.example.greendetect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Informations extends AppCompatActivity {

    DataBaseHelper db;
    MainActivity main;
    private Button btnEmail,btnPassword;
    private EditText newEmail,newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);
        db=new DataBaseHelper(this);
        main=new MainActivity();
        btnEmail=(Button) findViewById(R.id.ChangeEmail);
        btnPassword=(Button)findViewById(R.id.ChangePassword);

        newEmail=(EditText)findViewById(R.id.newEmail);
        newPassword=(EditText)findViewById(R.id.newPassword);


        changeEmail();
        changePassword();
    }
    public void changeEmail () {
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.changeEmail(main.getCurrentEmail(),newEmail.getText().toString(),main.getCurrentPassword());
            }
        });
    }

    public void changePassword () {
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.changePassword(main.getCurrentEmail(),main.getCurrentPassword(),newPassword.getText().toString());
            }
        });
    }

}
