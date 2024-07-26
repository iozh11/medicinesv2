package com.example.medicinesv2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class ViewMedicinesActivity extends AppCompatActivity {

    Button btnAddMedicines;
    DatabaseManager dbManager;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicines);

        tableLayout = findViewById(R.id.tableLayout);
        dbManager = new DatabaseManager(this);

        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        btnAddMedicines = findViewById(R.id.btn_view_add_medicines);
        btnAddMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMedicinesActivity.this, AddMedicinesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loadTableData();
    }

    private void loadTableData() {
        tableLayout.removeAllViews();

        ViewTable viewTable = new ViewTable(this);
        TableRow header = viewTable.createHeader();
        tableLayout.addView(header);

        Cursor cursor = dbManager.fetch();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int medID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_NAME));
                    String dateBefore = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_BEFORE_DATE));

                    TableRow row = viewTable.addRow(name, dateBefore, medID);
                    tableLayout.addView(row);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTableData();
    }
}