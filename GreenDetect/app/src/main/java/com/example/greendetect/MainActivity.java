package com.example.greendetect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    DataBaseHelper db;
    public EditText Email,Password;
    public Button Login,Register,Info;
    private static String currentEmail,currentPassword;
    private String groceryName="Apple";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DataBaseHelper(this);

        Email=(EditText)findViewById(R.id.editText_id);
        Password=(EditText)findViewById(R.id.editText_password);
        Register=(Button)findViewById(R.id.button_register);
        Login=(Button)findViewById(R.id.button_login);
      //  Info=(Button)findViewById(R.id.button_Info);
        Register();
        Login();
        //viewGroceryInfo();



    }

    public String getGroceryName(){
        return groceryName;
    }

    public String getCurrentEmail(){
        return currentEmail;
    }

    public String getCurrentPassword(){
        return currentPassword;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void Login(){
        Login.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        currentEmail=Email.getText().toString();
                        currentPassword=Password.getText().toString();
                        if(currentEmail.equals("")||currentPassword.equals("")){
                            Toast.makeText(MainActivity.this,"Field Empty!",Toast.LENGTH_LONG).show();
                        }
                        else if(validate(currentEmail,currentPassword)){
                            Toast.makeText(MainActivity.this, "ADMIN ACCESS", Toast.LENGTH_LONG).show();
                        }
                        else {

                            boolean logincheck = db.checkemailpass(currentEmail,currentPassword);

                            if(logincheck){
                                Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                Intent intent1 =new Intent(MainActivity.this, ImageActivity.class);
                                startActivity(intent1);
                            }
                            else
                                Toast.makeText(MainActivity.this, "Wrong email or password!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public boolean validate(String admin,String pass) {
        String emailstr=Email.getText().toString();
        String passwordstr=Password.getText().toString();
        if(( emailstr.equals("Admin")) && (passwordstr.equals("12345"))) {
            Intent intent =new Intent(MainActivity.this, Admin.class);
            startActivity(intent);
            return true;
        }
        else
            return false;
    }
    public void viewGroceryInfo(){
        Info.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res=db.getGroceryData("Lychee");
                        if(res.getCount()==0){
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("GroceryProduct :" +res.getString(1)+"\n");
                            buffer.append("Info :" +res.getString(2)+"\n\n");
                        }

                        //Show all data
                        showMessage("GroceryInfo",buffer.toString());
                    }

                }
        );
    }
    public void Register(){
        Register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String emailstr=Email.getText().toString();
                        String passwordstr=Password.getText().toString();
                        if(emailstr.equals("")||passwordstr.equals("")){
                            Toast.makeText(MainActivity.this,"Field Empty!",Toast.LENGTH_LONG).show();
                        }
                        else if (!isValidEmail(emailstr)){
                            Toast.makeText(MainActivity.this,"Not an Email!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (db.checkemail(emailstr)){
                                boolean isInserted = db.insert(emailstr,passwordstr);
                                if (isInserted) {
                                    Toast.makeText(MainActivity.this, "Register Successful!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Register not Successful!", Toast.LENGTH_LONG).show();
                                }
                            }

                            else{
                                Toast.makeText(MainActivity.this, "The email already exists!", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
        );
    }


}