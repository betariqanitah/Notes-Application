package com.example.notesapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    DBHelper helper;
    EditText Title, Detail;
    long id;
    String titles, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        Title = (EditText)findViewById(R.id.title_Edit);
        Detail = (EditText)findViewById(R.id.detail_Edit);

        getData();

        titles = Title.getText().toString().trim();
        details = Detail.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        String detail = Detail.getText().toString().trim();
        String title = Title.getText().toString().trim();

        if (detail.equals(details) && title.equals(titles)){
            EditActivity.super.onBackPressed();
        }else if (!detail.equals(details) || !title.equals(titles)) {
            alertDialog();
        }
    }

    private void alertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setMessage("Do you want to save changes?");
        builder.setCancelable(true);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update();
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditActivity.super.onBackPressed();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getData() {
        Cursor cursor = helper.oneData(id);
        if(cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.title));
            String detail = cursor.getString(cursor.getColumnIndex(DBHelper.note));

            Title.setText(title);
            Detail.setText(detail);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    public void update(){
        String title = Title.getText().toString().trim();
        String detail = Detail.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DBHelper.title, title);
        values.put(DBHelper.note, detail);

        if (title.equals("") && detail.equals("")){
            Toast.makeText(EditActivity.this, "Nothing to save", Toast.LENGTH_SHORT).show();
        }else {
            helper.updateData(values, id);
            Toast.makeText(EditActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_edit:
        update();
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setMessage("This note will be deleted.");
                builder.setCancelable(true);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteData(id);
                        Toast.makeText(EditActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}