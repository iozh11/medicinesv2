package com.example.medicinesv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewTable {

    private Context context;

    public ViewTable(Context context) {
        this.context = context;
    }

    private boolean isDateExpired(String dateBefore) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date expirationDate = dateFormat.parse(dateBefore);
            Date currentDate = new Date();
            return currentDate.after(expirationDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TableRow addRow(String medName, String medBeforeDate, int medId) {
        TableRow row = new TableRow(context);

        TextView textViewName = new TextView(context);
        TextView textViewDateBefore = new TextView(context);
        Button btnDetails = new Button(context);

        textViewName.setText(medName);
        textViewDateBefore.setText(medBeforeDate);
        btnDetails.setText("Подробнее");

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        textViewName.setLayoutParams(params);
        textViewDateBefore.setLayoutParams(params);

        textViewName.setBackgroundResource(R.drawable.border_background);
        textViewDateBefore.setBackgroundResource(R.drawable.border_background);

        int color = ContextCompat.getColor(context, R.color.sprites);
        textViewName.setTextColor(color);
        textViewDateBefore.setTextColor(color);

        textViewName.setTextSize(25);
        textViewDateBefore.setTextSize(25);

        textViewName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewDateBefore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        textViewName.setPadding(0, 30, 0, 27);
        textViewDateBefore.setPadding(0, 30, 0, 27);

        btnDetails.setBackgroundColor(Color.TRANSPARENT);
        btnDetails.setTextColor(color);
        btnDetails.setBackgroundResource(R.drawable.border_background);
        btnDetails.setPadding(0, 50, 0, 30);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("MED_ID", medId);
                context.startActivity(intent);
                if (context instanceof Activity) {
                    ((Activity) context).finish(); // Закрываем текущую Activity
                }
            }
        });

        if (isDateExpired(medBeforeDate)) {
            textViewDateBefore.setTextColor(Color.RED);
            textViewDateBefore.setText("Срок годности истек");
        }

        row.addView(textViewName);
        row.addView(textViewDateBefore);
        row.addView(btnDetails);

        return row;
    }

    public TableRow createHeader() {
        TableRow header = new TableRow(context);
        header.setBackgroundColor(ContextCompat.getColor(context, R.color.sprites));

        TextView textViewName = new TextView(context);
        TextView textViewDateBefore = new TextView(context);

        textViewName.setText("Название");
        textViewDateBefore.setText("Годен до");

        textViewName.setTextColor(Color.WHITE);
        textViewDateBefore.setTextColor(Color.WHITE);

        textViewName.setTextSize(30);
        textViewDateBefore.setTextSize(30);

        textViewName.setTypeface(null, Typeface.BOLD);
        textViewDateBefore.setTypeface(null, Typeface.BOLD);

        textViewName.setPadding(20,15,20,15);
        textViewDateBefore.setPadding(20,15,20,15);


        textViewName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textViewDateBefore.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        textViewName.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textViewDateBefore.setGravity(View.TEXT_ALIGNMENT_CENTER);

        header.addView(textViewName);
        header.addView(textViewDateBefore);

        return header;
    }
}