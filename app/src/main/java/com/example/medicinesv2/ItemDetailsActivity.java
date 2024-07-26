package com.example.medicinesv2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailsActivity extends AppCompatActivity {

    DatabaseManager dbManager;
    TextView textName, textDateOfMan, textBeforeDate, textStorage, textDescription;
    Button btnDelete, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        textName = findViewById(R.id.textViewName);
        textDateOfMan = findViewById(R.id.textViewDateOfManufacture);
        textBeforeDate = findViewById(R.id.textViewBeforeDate);
        textStorage = findViewById(R.id.textViewStorage);
        textDescription = findViewById(R.id.textViewDescription);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);


        Intent intent = getIntent();
        int id = intent.getIntExtra("MED_ID", -1);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Ошибка в открытии базы данных");
            return;
        }

        loadItemDetails(id);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(id);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, UpdateItemActivity.class);
                intent.putExtra("med_ID", id);
                intent.putExtra("med_NAME", textName.getText());
                intent.putExtra("med_DATE_OF_MAN", textDateOfMan.getText());
                intent.putExtra("med_BEFORE_DATE", textBeforeDate.getText());
                intent.putExtra("med_STORAGE", textStorage.getText());
                intent.putExtra("med_DESCRIPTION", textDescription.getText());
                startActivity(intent);
                finish();
            }
        });


    }

    private void loadItemDetails(int id) {
        Cursor cursor = dbManager.fetchById(id);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String medName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_NAME));
                String medDateOfManufacture = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_DATE_OF_MANUFACTURE));
                String medBeforeDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_BEFORE_DATE));
                String medStorage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_STORAGE));
                String medDescription = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MED_DESCRIPTION));

                textName.setText(medName);
                textDateOfMan.setText(medDateOfManufacture);
                textBeforeDate.setText(medBeforeDate);
                textStorage.setText(medStorage);
                textDescription.setText(medDescription);
            }
            cursor.close();
        } else {
            showToast("Невозможно получить информацию об этом лекарственном средстве");
        }
    }

    private void deleteItem(int id) {
        dbManager.delete(id);
        showToast("Лекарственное средство успешно удалено");
        Intent intent = new Intent(this, ViewMedicinesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}