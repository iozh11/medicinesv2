package com.example.medicinesv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddMedicinesActivity extends AppCompatActivity {
    EditText editTextNameAdd, editTextDatOfManufactureAdd, editTextBeforeDateAdd, editTextStorageAdd, editTextDescriptionAdd;
    Button btnAdd;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);

        editTextNameAdd = findViewById(R.id.nameAdd);
        editTextDatOfManufactureAdd = findViewById(R.id.date_of_manufactureAdd);
        editTextBeforeDateAdd= findViewById(R.id.before_dateAdd);
        editTextStorageAdd = findViewById(R.id.storageAdd);
        editTextDescriptionAdd = findViewById(R.id.descriptionAdd);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                String nameAdd = editTextNameAdd.getText().toString().trim();
                String dateOfManufactureAdd = editTextDatOfManufactureAdd.getText().toString().trim();
                String beforeDateAdd = editTextBeforeDateAdd.getText().toString().trim();
                String storageAdd = editTextStorageAdd.getText().toString().trim();
                String descriptionAdd = editTextDescriptionAdd.getText().toString().trim();

                if (nameAdd.isEmpty()) {
                    editTextNameAdd.setError("Заполните название");
                    isValid = false;
                }
                if (storageAdd.isEmpty()) {
                    storageAdd = "-";
                }
                if (descriptionAdd.isEmpty()) {
                    descriptionAdd = "-";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(dateOfManufactureAdd);
                } catch (ParseException e) {
                    editTextDatOfManufactureAdd.setError("Неверный формат даты дд/мм/гггг");
                    isValid = false;
                }

                try {
                    dateFormat.parse(beforeDateAdd);
                } catch (ParseException e) {
                    editTextBeforeDateAdd.setError("Неверный формат даты дд/мм/гггг");
                    isValid = false;
                }

                if (isValid) {
                    dbManager.insert(nameAdd, dateOfManufactureAdd, beforeDateAdd, storageAdd, descriptionAdd);
                    Toast.makeText(AddMedicinesActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ViewMedicinesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}