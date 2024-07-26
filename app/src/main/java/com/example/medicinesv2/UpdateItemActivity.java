package com.example.medicinesv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UpdateItemActivity extends AppCompatActivity {

    EditText editTextNameUpdate, editTextDatOfManufactureUpdate, editTextBeforeDateUpdate, editTextStorageUpdate, editTextDescriptionUpdate;
    Button btnUpdate;
    DatabaseManager dbManager;
    String medName, medDateOfMan, medBeforeDate, medStorage, medDescript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        Intent intent = getIntent();
        int id = intent.getIntExtra("med_ID", -1);
        medName = getIntent().getStringExtra("med_NAME");
        medDateOfMan = getIntent().getStringExtra("med_DATE_OF_MAN");
        medBeforeDate = getIntent().getStringExtra("med_BEFORE_DATE");
        medStorage = getIntent().getStringExtra("med_STORAGE");
        medDescript = getIntent().getStringExtra("med_DESCRIPTION");

        editTextNameUpdate = findViewById(R.id.nameUpdate);
        editTextDatOfManufactureUpdate = findViewById(R.id.date_of_manufactureUpdate);
        editTextBeforeDateUpdate= findViewById(R.id.before_dateUpdate);
        editTextStorageUpdate = findViewById(R.id.storageUpdate);
        editTextDescriptionUpdate = findViewById(R.id.descriptionUpdate);

        editTextNameUpdate.setText(medName);
        editTextDatOfManufactureUpdate.setText(medDateOfMan);
        editTextBeforeDateUpdate.setText(medBeforeDate);
        editTextStorageUpdate.setText(medStorage);
        editTextDescriptionUpdate.setText(medDescript);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                String nameUpdate = editTextNameUpdate.getText().toString().trim();
                String dateOfManufactureUpdate = editTextDatOfManufactureUpdate.getText().toString().trim();
                String beforeDateUpdate = editTextBeforeDateUpdate.getText().toString().trim();
                String storageUpdate = editTextStorageUpdate.getText().toString().trim();
                String descriptionUpdate = editTextDescriptionUpdate.getText().toString().trim();

                if (nameUpdate.isEmpty()) {
                    editTextNameUpdate.setError("Заполните название");
                    isValid = false;
                }
                if (storageUpdate.isEmpty()) {
                    storageUpdate = "-";
                }
                if (descriptionUpdate.isEmpty()) {
                    descriptionUpdate = "-";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(dateOfManufactureUpdate);
                } catch (ParseException e) {
                    editTextDatOfManufactureUpdate.setError("Неверный формат даты дд/мм/гггг");
                    isValid = false;
                }

                try {
                    dateFormat.parse(beforeDateUpdate);
                } catch (ParseException e) {
                    editTextBeforeDateUpdate.setError("Неверный формат даты дд/мм/гггг");
                    isValid = false;
                }

                if (isValid) {
                    dbManager.update(id, nameUpdate, dateOfManufactureUpdate, beforeDateUpdate, storageUpdate, descriptionUpdate);
                    Toast.makeText(UpdateItemActivity.this, "Измененно", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ViewMedicinesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}