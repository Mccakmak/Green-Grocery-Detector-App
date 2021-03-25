package com.example.greendetect;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Info extends AppCompatActivity {

    DataBaseHelper db;
    MainActivity main;
    private TextView Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        db=new DataBaseHelper(this);
        main = new MainActivity();

        Info=(TextView)findViewById(R.id.textView_info);

        viewInfo();
    }

    public void viewInfo() {
        String groceryName=main.getGroceryName();
        Cursor res = db.getGroceryData(groceryName);
        if (res.getCount() == 0) {
            Toast.makeText(Info.this, "Error nothing found", Toast.LENGTH_LONG).show();
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("GroceryProduct :" + res.getString(1) + "\n");
            buffer.append("Info :" + res.getString(2) + "\n\n");
        }
        Info.setText(buffer.toString());
    }


}