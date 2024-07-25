package com.example.medicinesv2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

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
        btnDetails.setBackgroundColor(Color.TRANSPARENT);
        btnDetails.setBackgroundResource(R.drawable.border_background);
        btnDetails.setPadding(0, 50, 0, 30);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("MED_ID", medId);
                context.startActivity(intent);
            }
        });

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        textViewName.setLayoutParams(params);
        textViewDateBefore.setLayoutParams(params);

        textViewName.setBackgroundResource(R.drawable.border_background);
        textViewDateBefore.setBackgroundResource(R.drawable.border_background);

        textViewName.setTextSize(25);
        textViewDateBefore.setTextSize(25);

        textViewName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewDateBefore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        textViewName.setPadding(0, 30, 0, 27);
        textViewDateBefore.setPadding(0, 30, 0, 27);

        if (isDateExpired(medBeforeDate)) {
            textViewDateBefore.setTextColor(Color.RED);
            textViewDateBefore.setText("Срок годности истек");
        }

        row.addView(textViewName);
        row.addView(textViewDateBefore);
        row.addView(btnDetails);

        return row;
    }
}