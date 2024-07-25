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
    EditText editTextName, editTextDatOfManufacture, editTextBeforeDate, editTextStorage, editTextDescription;
    Button btnAdd;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);

        editTextName = findViewById(R.id.name);
        editTextDatOfManufacture = findViewById(R.id.date_of_manufacture);
        editTextBeforeDate= findViewById(R.id.before_date);
        editTextStorage = findViewById(R.id.storage);
        editTextDescription = findViewById(R.id.description);

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

                String name = editTextName.getText().toString().trim();
                String dateOfManufacture = editTextDatOfManufacture.getText().toString().trim();
                String beforeDate = editTextBeforeDate.getText().toString().trim();
                String storage = editTextStorage.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (name.isEmpty()) {
                    editTextName.setError("Поле 'Название лекарства' не должно быть пустым");
                    isValid = false;
                }
                if (storage.isEmpty()) {
                    storage = "-";
                }
                if (description.isEmpty()) {
                    description = "-";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(dateOfManufacture);
                } catch (ParseException e) {
                    editTextDatOfManufacture.setError("Неверный формат даты (дд/мм/гггг)");
                    isValid = false;
                }

                try {
                    dateFormat.parse(beforeDate);
                } catch (ParseException e) {
                    editTextBeforeDate.setError("Неверный формат даты (дд/мм/гггг)");
                    isValid = false;
                }

                if (isValid) {
                    dbManager.insert(name, dateOfManufacture, beforeDate, storage, description);
                    Toast.makeText(AddMedicinesActivity.this, "Успешно добавлено", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ViewMedicinesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}