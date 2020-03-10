package com.example.lec08;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    StringBuffer output = new StringBuffer();
    SQLiteDatabase db;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sps = PreferenceManager.getDefaultSharedPreferences(this);
        String texts = sps.getString("key1","Hello");
        Toast.makeText(MainActivity.this, texts, Toast.LENGTH_SHORT).show();

        createDB();
        final EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        ListView lv = findViewById(R.id.lv);
        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.activity_main);
        lv.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                text = editText.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("key1", text);
                editor.commit();
            }
        });
    }

    private void  createDB(){
        try{
            db = openOrCreateDatabase("test_db.db", MODE_PRIVATE, null);
            output.append("Demo DB");
        }
        catch (Exception e){
            output.append(e.getMessage());
        }
    }

    private void createTables(){
        try {
            String dropStudentsTable = "DROP TABLE IF EXISTS students";
            db.execSQL(dropStudentsTable);
            output.append("Students table dropped");
            String dropGrasde = "DROP TABLE IF EXISTS grades";
            db.execSQL(dropGrasde);
        }
        catch (Exception e){
            output.append(e.getMessage());
        }
    }
}
