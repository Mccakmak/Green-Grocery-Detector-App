package com.example.greendetect;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {
    DataBaseHelper db;
    private Button Delete,ViewAll;
    private EditText ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        db=new DataBaseHelper(this);

        Delete=(Button)findViewById(R.id.button_delete);
        ViewAll=(Button)findViewById(R.id.button_viewAll);
        ID=(EditText)findViewById(R.id.editText_deleteId);

        viewAll();
        delete();
    }

    private void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void delete(){
        Delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idstr =ID.getText().toString();
                        int deletedRows=db.deleteData(idstr);
                        if(deletedRows > 0){
                            Toast.makeText(Admin.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(Admin.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
                    }

                }
        );
    }

    private void viewAll(){
        ViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = db.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing found");
                        }
                        else {
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append("Id :" + res.getString(0) + "\n");
                                buffer.append("Email :" + res.getString(1) + "\n");
                                buffer.append("Password :" + res.getString(2) + "\n\n");
                            }

                            //Show all data
                            showMessage("User Data", buffer.toString());
                        }
                    }

                }
        );
    }

}
