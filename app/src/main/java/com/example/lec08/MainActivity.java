package com.example.lec08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    StringBuffer output = new StringBuffer();
    SQLiteDatabase db;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        createTables();
        addStudet("12345", "Sam", "CSIS");
        addStudet("12346", "kate", "CSIS");
        browseStudents();
        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        final SharedPreferences sps = PreferenceManager.getDefaultSharedPreferences(this);
        String texts = sps.getString("key1","Hello");
        //Toast.makeText(MainActivity.this, texts, Toast.LENGTH_SHORT).show();
        final EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        ListView lv = findViewById(R.id.lv);
        List<String[]> getdata = ReadCSV();
        CustomAdapter customAdapter = new CustomAdapter(ReadCSV());
        lv.setHeaderDividersEnabled(true);
        lv.setAdapter(customAdapter);
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
            db = openOrCreateDatabase("Students.db", Context.MODE_PRIVATE, null);
            Toast.makeText(this, "DB Created", Toast.LENGTH_SHORT).show();
            output.append("Demo DB");
        }
        catch (Exception e){
            output.append("Error creating DB");
        }
    }

    private void createTables(){
        try {
            String dropStudentsTable = "DROP TABLE IF EXISTS students";
            db.execSQL(dropStudentsTable);
            output.append("Students table dropped \n");
            String dropGrasde = "DROP TABLE IF EXISTS grades";
            db.execSQL(dropGrasde);
            String createStudents = "CREATE TABLE students " +  "(id text primary key, name text, major text);";
            db.execSQL(createStudents);
            output.append("Created table students \n");
            String createGrades = "CREATE TABLE grades " + "(id text primary key, grade real);";
            db.execSQL(createGrades);
            output.append("Create table grades \n");
        }
        catch (Exception e){
            output.append(e.getMessage());
        }
    }

    private void addStudet(String id, String name, String major){
        ContentValues studentCV = new ContentValues();
        studentCV.put("id", id);
        studentCV.put("name", name);
        studentCV.put("major", major);
    }

    private List<String[]> ReadCSV(){
        List<String[]> resultList = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.students);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvLine;
            while ((csvLine = bufferedReader.readLine())!= null){
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        }
        catch (IOException e){
            throw new RuntimeException("Error reading csv file" + e);
        }
        finally {
            try{
                inputStream.close();
            }
            catch (IOException e){
                throw new RuntimeException("Error closing stream" + e);
            }
        }
        return resultList;
    }

    private void browseStudents(){
        String browesStudents = "SELECT * FROM students;";
        try {
            Cursor cursor = db.rawQuery(browesStudents,null);
            output.append("Browsing students..\n");
            if(cursor !=null){
                cursor.moveToFirst();
                do{
                    String eachStudentRec = cursor.getString(0);
                    output.append(eachStudentRec);
                    Log.w("TAG", "HERE");
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception e){
            output.append("Error browsing.");
        }
    }
}
