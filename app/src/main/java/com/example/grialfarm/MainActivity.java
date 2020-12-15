package com.example.grialfarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    EditText etNombre, etRfc, etCalle, etNumero, etColonia, etCiudad, etCp, etTelefono, etEstado;

    FloatingActionButton  fabListar;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String updateId,updateNombre, updateRfc, updateCalle, updateNumero, updateColonia, updateCiudad, updateCp, updateTelefono, updateEstado;
    Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAceptar = findViewById(R.id.btnAceptar);
        fabListar = findViewById(R.id.fabListar);
        etNombre = findViewById(R.id.etNombre);
        etRfc = findViewById(R.id.etRfc);
        etEstado = findViewById(R.id.etEstado);
        etCiudad = findViewById(R.id.etCiudad);
        etCalle = findViewById(R.id.etCalle);
        etColonia = findViewById(R.id.etColonia);
        etNumero = findViewById(R.id.etNumero);
        etTelefono = findViewById(R.id.etTelefono);
        etCp = findViewById(R.id.etcp);
        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar registro");

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Actualizar Datos");

            updateId = bundle.getString("updateId");
            updateNombre = bundle.getString("updateNombre");
            updateRfc = bundle.getString("updateRfc");
            updateCalle = bundle.getString("updateCalle");
            updateNumero = bundle.getString("updateNumero");
            updateColonia = bundle.getString("updateColonia");
            updateCiudad = bundle.getString("updateCiudad");
            updateCp = bundle.getString("updateCp");
            updateTelefono = bundle.getString("updateTelefono");
            updateEstado = bundle.getString("updateEstado");

            etNombre.setText(updateNombre);
            etRfc.setText(updateRfc);
            etCalle.setText(updateCalle);
            etNumero.setText(updateNumero);
            etColonia.setText(updateColonia);
            etCiudad.setText(updateCiudad);
            etCp.setText(updateCp);
            etTelefono.setText(updateTelefono);
            etEstado.setText(updateEstado);

        } else {
            actionBar.setTitle("Agregar");
        }

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = updateId;
                    String nombre = etNombre.getText().toString().trim();
                    String rfc = etRfc.getText().toString().trim();
                    String calle= etCalle .getText().toString().trim();
                    String numero = etNumero.getText().toString().trim();
                    String colonia = etColonia.getText().toString().trim();
                    String ciudad = etCiudad.getText().toString().trim();
                    String cp = etCp.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();
                    String estado = etEstado.getText().toString().trim();

                    //actualizarDatos(id, nombre, rfc, calle, numero, colonia, ciudad, cp, telefono, estado);
                    actualizarDatos( nombre, rfc, calle, numero, colonia, ciudad, cp, telefono, estado);

                } else {
                    String nombre = etNombre.getText().toString().trim();
                    String rfc = etRfc.getText().toString().trim();
                    String calle= etCalle .getText().toString().trim();
                    String numero = etNumero.getText().toString().trim();
                    String colonia = etColonia.getText().toString().trim();
                    String ciudad = etCiudad.getText().toString().trim();
                    String cp = etCp.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();
                    String estado = etEstado.getText().toString().trim();

                    cargarDatos(nombre, rfc, calle, numero, colonia, ciudad, cp, telefono, estado);
                }
            }
        });


        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivityGrial.class));
                finish();
            }
        });

    }


    private void cargarDatos(String nombre, String rfc, String calle, String numero, String colonia, String ciudad, String cp, String telefono, String estado) {
        progressDialog.setTitle("Agregar datos");
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map <String, Object> doc = new HashMap <>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("rfc", etRfc);
        doc.put("calle", etCalle);
        doc.put("numero", etNumero);
        doc.put("colonia", etColonia);
        doc.put("ciudad", etCiudad);
        doc.put("cp", etCp);
        doc.put("telefono", etTelefono);
        doc.put("estado", etEstado);


        db.collection("Documents").document(id).set(doc).addOnCompleteListener(new OnCompleteListener <Void>() {
            @Override
            public void onComplete(@NonNull Task <Void> task) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Datos almacenados con éxito...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void actualizarDatos(String nombre, String rfc, String calle, String numero, String colonia, String ciudad, String cp, String telefono, String estado) {
        progressDialog.setTitle("Actualizando datos a Firebase");
        progressDialog.show();


        String id = UUID.randomUUID().toString();

         Map <String, Object> doc = new HashMap <>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("rfc", etRfc);
        doc.put("calle", etCalle);
        doc.put("numero", etNumero);
        doc.put("colonia", etColonia);
        doc.put("ciudad", etCiudad);
        doc.put("cp", etCp);
        doc.put("telefono", etTelefono);
        doc.put("estado", etEstado);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivityGrial.class);
                startActivity(intent);
            }
        });

        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}