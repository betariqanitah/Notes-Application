package com.example.notesapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_notes, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.row_ID = (TextView)v.findViewById(R.id.row_ID);
        holder.row_Title = (TextView)v.findViewById(R.id.row_Title);
        holder.row_Detail = (TextView)v.findViewById(R.id.row_Detail);
        holder.row_Created = (TextView)v.findViewById(R.id.row_Created);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.row_ID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_id)));
        holder.row_Title.setText(cursor.getString(cursor.getColumnIndex(DBHelper.title)));
        holder.row_Detail.setText(cursor.getString(cursor.getColumnIndex(DBHelper.note)));
        holder.row_Created.setText(cursor.getString(cursor.getColumnIndex(DBHelper.created)));
    }

    class MyHolder{
        TextView row_ID;
        TextView row_Title;
        TextView row_Detail;
        TextView row_Created;
    }
}
