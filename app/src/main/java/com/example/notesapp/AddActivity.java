package com.example.notesapp;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    DBHelper helper;
    EditText Title, Detail;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        Title = (EditText)findViewById(R.id.title_Add);
        Detail = (EditText)findViewById(R.id.detail_Add);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add:
                String title = Title.getText().toString().trim();
                String detail = Detail.getText().toString().trim();

                //Get Date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDate = new SimpleDateFormat("MMM dd, yyyy");
                String created = simpleDate.format(calendar.getTime());

                ContentValues values = new ContentValues();
                values.put(DBHelper.title, title);
                values.put(DBHelper.note, detail);
                values.put(DBHelper.created, created);

                //Create Condition if Title and Detail is empty
                if (title.equals("") && detail.equals("")){
                    Toast.makeText(AddActivity.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertData(values);
                    Toast.makeText(AddActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}